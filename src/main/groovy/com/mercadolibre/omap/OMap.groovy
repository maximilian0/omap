package com.mercadolibre.omap

import groovy.transform.PackageScope

import java.text.SimpleDateFormat

/**
 * Created by mtaborda on 11-Nov-2016.
 *
 * This is the class with the responsibility to convert one object to its map representation. If the object
 * is what we consider a primitive, just add this object to the resultant map. If the object is a complex one
 * and has an adapter defined, this adapter is used to convert this complex object into map and the added to
 * the resultant map. If the object is a complex one and you do not provide an adapter to it, well, maybe you
 * need to check your decision.
 * @adapters is a map to where each entry is an adapter of one specific object.
 *  Each entry has the form:
 *      key: <one class>, value: <an adapter>.
 * @adaptNull is a flag to indicate if the field or method with null value needs to be added to the resultant map.
 *  By default null are avoided.
 */
class OMap {

    Map<Class, AdapterStrategy> adapters = [:]
    boolean adaptNulls = false
    boolean keysToCamelCase = true
    SimpleDateFormat dateFormatter

    protected OMap() {

        super()
    }

    Map<String, Object> toMap(Object object) {

        Map<String, Object> result = [:]
        this.intoMap(object, result)
        return result
    }

    List<Map<String, Object>> toMapList(List<Object> objects) {

        return objects.collect { it -> this.toMap(it) }
    }

    @PackageScope
    void intoMap(Object object, Map<String, Object> result) {

        AdapterStrategy adapter = this.findAdapter(object.class)
        adapter.adapt(object, result, this)
    }

    def <T> T fromMap(Map<String, Object> map, Class<T> type) {

        T instance = type.newInstance()
        this.initializeObject(instance, this.mapToConstructorArguments(map, type))
        return instance
    }

    def <T> List<T> fromMapList(List<Map<String, Object>> list, Class<T> type) {

        return list.collect { it -> this.fromMap(it, type) }
    }

    def <T> Map<String, Object> fromMapForUpdate(Map<String, Object> map, Class<T> type) {

        return this.mapToConstructorArguments(map, type)
    }

    private <T> LinkedHashMap<String, Object> mapToConstructorArguments(Map<String, Object> map, Class<T> type) {

        Map<String, Object> args = [:]
        map.inject(args) { Map<String, Object> m, Map.Entry<String, Object> entry ->
            String key = this.keyToCamelCase(entry.key as String)
            Class clazz = type.getDeclaredField(key).getType()
            Type t = Type.forObject(clazz)
            Object value = t.read(entry, clazz, type, this)
            m.put(this.keyToCamelCase(entry.key as String), value)
            m
        }
        return args
    }

    private void initializeObject(Object instance, LinkedHashMap<String, Object> args) {

        args.each { Map.Entry<String, Object> entry ->
            Class clazz = instance.getClass().getDeclaredField(entry.key).getType()
            Type t = Type.forObject(clazz)
            t.setValue(instance, entry.key, entry.value)
        }
    }

    private String keyToCamelCase(String key) {

        return keysToCamelCase ?
                key.replaceAll("(_)([A-Za-z0-9])", { Object[] it -> (it[2] as String).toUpperCase() }) :
                key.replaceAll(/([A-Z])/, /_$1/).toLowerCase().replaceAll(/^_/, '')
    }

    @PackageScope
    void intoMap(Object object, Map<String, Object> result, String key) {

        if (object == null && !adaptNulls) return
        Type type = Type.forObject(object)
        result[key] = type.value(object, this)
    }

    private AdapterStrategy findAdapter(Class clazz) {

        return adapters.findResult(
                new DefaultAdapterStrategy(),
                { it -> it.key == clazz ? it.value : null }) as AdapterStrategy
    }
}

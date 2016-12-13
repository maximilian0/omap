package com.mercadolibre.omap

/**
 * Created by mtaborda on 29-Nov-2016.
 */
class ObjectType extends Type {

    static boolean canHandle(Object object) {

        return !CollectionType.canHandle(object) &&
                !EnumType.canHandle(object) &&
                !DateType.canHandle(object) &&
                !PrimitiveType.canHandle(object)
    }

    Object value(Object object, OMap oMap) {

        return oMap.toMap(object)
    }

    Object read(Map.Entry<String, Object> entry, Class clazz, Class type, OMap omap) {

        return omap.fromMap(entry.value as Map, clazz)
    }

    void setValue(Object instance, String key, Object value) {

        this.defaultSetValue(instance, key, value)
    }
}

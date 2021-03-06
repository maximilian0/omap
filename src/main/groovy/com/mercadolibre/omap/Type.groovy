package com.mercadolibre.omap

/**
 * Created by mtaborda on 29-Nov-2016.
 */
abstract class Type {

    static List<Class> subclasses = [CollectionType, EnumType, DateType, PrimitiveType]

    static Type forObject(Object object) {

        Class handler = subclasses.find { it -> it.canHandle(object) }
        if (handler == null) handler = ObjectType
        return (Type) handler.newInstance()
    }

    abstract Object value(Object object, OMap omap)

    abstract Object read(Map.Entry<String, Object> entry, Class clazz, Class type, OMap omap)

    abstract void setValue(Object instance, String key, Object value)

    void defaultSetValue(Object instance, String key, Object value) {

        instance[key] = value
    }
}

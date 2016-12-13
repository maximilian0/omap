package com.mercadolibre.omap

/**
 * Created by mtaborda on 29-Nov-2016.
 */
class PrimitiveType extends Type {

    static boolean canHandle(Object object) {

        return object instanceof Number || object in Number ||
                object instanceof String || object in String ||
                object instanceof Character || object in Character ||
                object instanceof Boolean || object in Boolean ||
                object == null || (object.respondsTo("isPrimitive") && object.isPrimitive())
    }

    Object value(Object object, OMap oMap) {

        return object
    }

    Object read(Map.Entry<String, Object> entry, Class clazz, Class type, OMap omap) {

        return entry.value
    }

    void setValue(Object instance, String key, Object value) {

        this.defaultSetValue(instance, key, value)
    }
}

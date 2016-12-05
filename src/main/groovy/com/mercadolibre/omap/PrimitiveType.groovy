package com.mercadolibre.omap

/**
 * Created by mtaborda on 29-Nov-2016.
 */
class PrimitiveType extends Type {

    static boolean canHandle(Object object) {

        return object instanceof Number || object instanceof String ||
                object instanceof Character || object instanceof Boolean ||
                object instanceof Date || object == null
    }

    Object value(Object object, OMap oMap) {

        return object
    }

    Object read(Map.Entry<String, Object> entry, Class clazz, Class type, OMap omap) {

        return entry.value
    }
}

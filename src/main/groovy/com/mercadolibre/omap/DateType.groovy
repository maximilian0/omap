package com.mercadolibre.omap

/**
 * Created by mtaborda on 05-Dec-2016.
 */
class DateType extends Type {

    static boolean canHandle(Object object) {

        return object instanceof Date || object in Date
    }

    Object value(Object object, OMap omap) {

        return omap.dateFormatter.format(object as Date)
    }

    Object read(Map.Entry<String, Object> entry, Class clazz, Class type, OMap omap) {

        return omap.dateFormatter.parse(entry.value as String)
    }

    void setValue(Object instance, String key, Object value) {

        this.defaultSetValue(instance, key, value)
    }
}

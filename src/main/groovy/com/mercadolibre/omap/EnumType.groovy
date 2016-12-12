package com.mercadolibre.omap
/**
 * Created by mtaborda on 29-Nov-2016.
 */
class EnumType extends Type {

    static boolean canHandle(Object object) {

        return object instanceof Enum || object in Enum
    }

    Object value(Object object, OMap oMap) {

        return (object as Enum).name()
    }

    Object read(Map.Entry<String, Object> entry, Class clazz, Class type, OMap omap) {

        return clazz.valueOf(entry.value)
    }

    void setValue(Object instance, String key, Object value) {

        this.defaultSetValue(instance, key, value)
    }
}

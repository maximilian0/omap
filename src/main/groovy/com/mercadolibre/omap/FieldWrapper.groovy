package com.mercadolibre.omap

import java.lang.reflect.Field

/**
 * Created by mtaborda on 15-Nov-2016.
 *
 * A wrapper for class field.
 * @key is the key used in the map entry.
 * @field is the field instance.
 * @object is the object that own the @field.
 */
class FieldWrapper extends Wrapper {

    String key
    Field field
    Object object

    FieldWrapper(Field field, Object object, AdapterStrategy strategy) {

        this.key = strategy.keyFor(field)
        this.field = field
        this.object = object
    }

    void adaptInto(Map<String, Object> result, OMap omap) {

        omap.intoMap(object[field.name], result, key)
    }
}

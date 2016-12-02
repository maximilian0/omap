package com.mercadolibre.omap

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by mtaborda on 11-Nov-2016.
 *
 * This class is just a default object-to-map adapter. It's main responsibility is
 * evaluate each instance variable and add an entry to the map where the key is the
 * variable name and the value is the adapted variable value.
 */
class DefaultAdapterStrategy extends AdapterStrategy {

    @Override
    Collection<Wrapper> computeElementsToAdapt(Object object) {

        return object.class.declaredFields
                .grep { !it.synthetic && it.name != MAP_FIELDS && it.name != MAP_GETTERS }
                .collect { it -> new FieldWrapper(it, object, this) }
    }

    @Override
    String keyFor(Field field) {

        return field.name
    }

    @Override
    String keyFor(Method method) {

        throw new Exception("By default only fields could be converted to map. Check your strategy.")
    }
}

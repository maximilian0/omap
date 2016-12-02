package com.mercadolibre.omap

import java.lang.reflect.Method

/**
 * Created by mtaborda on 15-Nov-2016.
 *
 * A wrapper for class method.
 * @key is the key used in the map entry.
 * @method the method (getter) to evaluate.
 * @object is the object that own the @method.
 */
class MethodWrapper extends Wrapper {

    static String GET_PREFIX = 'get'

    String key
    Method method
    Object object

    MethodWrapper(String getterName, Object object) {

        this.method = object.class.getDeclaredMethod("$GET_PREFIX${getterName.capitalize()}")
        this.key = getterName
        this.object = object
    }

    MethodWrapper(Method method, Object object, AdapterStrategy strategy) {

        this.method = method
        this.key = strategy.keyFor(method)
        this.object = object
    }

    void adaptInto(Map<String, Object> result, OMap omap) {

        omap.intoMap(method.invoke(object), result, key)
    }
}

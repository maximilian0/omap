package com.mercadolibre.omap

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by mtaborda on 11-Nov-2016.
 *
 * Abstract class of the different adapter strategies.
 */
abstract class AdapterStrategy {

    static String MAP_FIELDS = 'mapFields'
    static String MAP_GETTERS = 'mapGetters'

    abstract Collection<Wrapper> computeElementsToAdapt(Object object)

    abstract String keyFor(Field field)

    abstract String keyFor(Method method)

    void adapt(Object object, Map<String, Object> map, OMap omap) {

        this.adapt(map, this.computeElementsToAdapt(object), omap)
    }

    void adapt(Map<String, Object> map, Collection<Wrapper> elementsToAdapt, OMap omap) {

        elementsToAdapt.inject(map) { Map<String, Object> result, Wrapper it ->
            it.adaptInto(result, omap)
            result
        }
    }
}

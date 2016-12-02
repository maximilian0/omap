package com.mercadolibre.omap

/**
 * Created by mtaborda on 15-Nov-2016.
 *
 * Abstract class to wrap what is needed to put in a map entry.
 */
abstract class Wrapper {

    abstract void adaptInto(Map<String, Object> result, OMap omap)
}

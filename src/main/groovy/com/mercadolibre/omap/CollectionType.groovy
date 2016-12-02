package com.mercadolibre.omap

/**
 * Created by mtaborda on 29-Nov-2016.
 */
class CollectionType extends Type {

    static boolean canHandle(Object object) {

        return object in Collection
    }

    Object value(Object object, OMap omap) {

        List objects = object as List
        List result = []
        objects.inject(result) { List list, Object it ->
            list.add(omap.toMap(it))
            list
        }
        return result
    }
}

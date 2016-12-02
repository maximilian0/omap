package com.mercadolibre.omap

/**
 * Created by mtaborda on 29-Nov-2016.
 */
class ObjectType extends Type {

    static boolean canHandle(Object object) {

        return !CollectionType.canHandle(object) &&
                !EnumType.canHandle(object) &&
                !PrimitiveType.canHandle(object)
    }

    Object value(Object object, OMap oMap) {

        return oMap.toMap(object)
    }
}

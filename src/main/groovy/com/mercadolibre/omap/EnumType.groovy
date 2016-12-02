package com.mercadolibre.omap
/**
 * Created by mtaborda on 29-Nov-2016.
 */
class EnumType extends Type {

    static boolean canHandle(Object object) {

        return object instanceof Enum
    }

    Object value(Object object, OMap oMap) {

        return (object as Enum).name()
    }
}

package com.mercadolibre.omap

/**
 * Created by mtaborda on 15-Nov-2016.
 */
class Document {

    @Include(keyName = "someDifferentToTypeKey")
    String type
    String number

    @Include(keyName = "someDifferentToNumberKey")
    String getNumber() {

        return number
    }
}

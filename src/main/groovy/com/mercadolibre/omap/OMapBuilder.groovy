package com.mercadolibre.omap

/**
 * Created by mtaborda on 11-Nov-2016.
 *
 * Builder used to configure the OMap.
 * @adapters is a map to where each entry is an adapter of one specific object.
 *  Each entry has the form:
 *      key: <one class>, value: <an adapter>.
 * @adaptNull is a flag to indicate if the field or method with null value needs to be added to the resultant map.
 *  By default null are avoided.
 */
class OMapBuilder {

    Map<Class, AdapterStrategy> adapters
    boolean adaptNulls
    boolean keysToCamelCase

    OMapBuilder() {

        this.adapters = [:]
        this.adaptNulls = false
        this.keysToCamelCase = true
    }

    OMapBuilder registerAdapter(Class aClass, AdapterStrategy adapter) {

        this.adapters.put(aClass, adapter)
        return this
    }

    OMapBuilder adaptNulls() {

        this.adaptNulls = true
        return this
    }

    OMapBuilder notAdaptNulls() {

        this.adaptNulls = false
        return this
    }

    OMapBuilder keysToCamelCase() {

        this.keysToCamelCase = true
        return this
    }

    OMapBuilder keysToSnakeCase() {

        this.keysToCamelCase = false
        return this
    }

    OMap build() {

        return new OMap(adapters: adapters, adaptNulls: adaptNulls, keysToCamelCase: keysToCamelCase)
    }
}

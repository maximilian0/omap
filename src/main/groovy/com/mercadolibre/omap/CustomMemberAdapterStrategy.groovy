package com.mercadolibre.omap

import groovy.transform.PackageScope

/**
 * Created by mtaborda on 30-Nov-2016.
 *
 * This class is the object-to-map adapter to use where the resultant map has the needed fields
 * and getters of the object. It's main responsibility is evaluate the fields and the methods declared
 * when the strategy is created and evaluate these instance variables and getter and add an entry to the
 * map where the key could be the variable name or the getter name or whatever the keyName attribute of the
 * annotation defines and the value is the adapted variable or getter value.
 */
class CustomMemberAdapterStrategy extends IncludeDeclaredMemberAdapterStrategy {

    List<String> fieldNames
    List<String> methodNames

    CustomMemberAdapterStrategy() {

        this.fieldNames = []
        this.methodNames = []
    }

    @Override
    @PackageScope
    void computeFieldsToAdapt(Object object, Collection<Wrapper> wrappers) {

        wrappers.addAll(fieldNames.collect { it -> new FieldWrapper(object.class.getDeclaredField(it), object, this) })
    }

    @Override
    @PackageScope
    void computeMethodsToAdapt(Object object, Collection<Wrapper> wrappers) {

        wrappers.addAll(methodNames.collect { it -> new MethodWrapper(it, object) })
    }
}

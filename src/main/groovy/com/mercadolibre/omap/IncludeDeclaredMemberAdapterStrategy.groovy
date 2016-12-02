package com.mercadolibre.omap

import groovy.transform.PackageScope

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by mtaborda on 30-Nov-2016.
 */
abstract class IncludeDeclaredMemberAdapterStrategy extends AdapterStrategy {

    @Override
    Collection computeElementsToAdapt(Object object) {

        List<Wrapper> wrappers = new ArrayList<>()
        this.computeFieldsToAdapt(object, wrappers)
        this.computeMethodsToAdapt(object, wrappers)
        return wrappers
    }

    @Override
    String keyFor(Field field) {

        return field.name
    }

    @Override
    String keyFor(Method method) {

        return method.name
    }

    @PackageScope
    abstract void computeFieldsToAdapt(Object object, Collection<Wrapper> wrappers)

    @PackageScope
    abstract void computeMethodsToAdapt(Object object, Collection<Wrapper> wrappers)

}

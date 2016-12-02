package com.mercadolibre.omap

/**
 * Created by mtaborda on 15-Nov-2016.
 *
 * This class is the object-to-map adapter to use where the resultant map has the annotated attributes
 * and getters of the object. It's main responsibility is evaluate each @Include annotated instance variable and
 * getter and add an entry to the map where the key could be the variable name or the getter name or whatever
 * the keyName attribute of the annotation defines and the value is the adapted variable or getter value.
 */
class IncludeAnnotatedAdapterStrategy extends AnnotatedAdapterStrategy {

    @Override
    Collection computeElementsToAdapt(Object object) {

        List<Wrapper> wrappers = new ArrayList<>()
        this.computeFieldsToAdapt(object, wrappers)
        this.computeMethodsToAdapt(object, wrappers)
        return wrappers
    }

    private void computeFieldsToAdapt(Object object, Collection<Wrapper> wrappers) {

        wrappers.addAll(object.class.declaredFields
                .grep { !it.synthetic && it.isAnnotationPresent(Include) }
                .collect { it -> new FieldWrapper(it, object, this) })
    }

    private void computeMethodsToAdapt(Object object, Collection<Wrapper> wrappers) {

        wrappers.addAll(object.class.declaredMethods
                .grep { !it.synthetic && it.isAnnotationPresent(Include) }
                .collect { it -> new MethodWrapper(it, object, this) })
    }
}

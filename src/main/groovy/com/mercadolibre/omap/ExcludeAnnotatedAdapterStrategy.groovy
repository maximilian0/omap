package com.mercadolibre.omap

/**
 * Created by mtaborda on 15-Nov-2016.
 *
 * This class is just other object-to-map adapter. It's main responsibility is
 * evaluate each instance variable and, if the field do not declare the annotation @Exclude,
 * add an entry to the map where the key is the variable name and the value is the adapted
 * variable value.
 */
class ExcludeAnnotatedAdapterStrategy extends AnnotatedAdapterStrategy {

    @Override
    Collection computeElementsToAdapt(Object object) {

        List<Wrapper> wrappers = new ArrayList<>()
        wrappers.addAll(object.class.declaredFields
                .grep {
            !it.synthetic && !it.isAnnotationPresent(Exclude) &&
                    it.name != MAP_FIELDS && it.name != MAP_GETTERS
        }
        .collect { it -> new FieldWrapper(it, object, this) })
        return wrappers
    }
}

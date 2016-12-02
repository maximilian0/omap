package com.mercadolibre.omap

import java.lang.annotation.Annotation
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by mtaborda on 18-Nov-2016.
 *
 * Abstract class of the different adapter strategies to use with annotation.
 */
abstract class AnnotatedAdapterStrategy extends AdapterStrategy {

    @Override
    String keyFor(Field field) {

        Annotation include = field.getAnnotation(Include)
        return (include && !include.keyName().isEmpty()) ? include.keyName() : field.name
    }

    @Override
    String keyFor(Method method) {

        Annotation include = method.getAnnotation(Include)
        return (include && !include.keyName().isEmpty()) ? include.keyName() : method.name
    }
}

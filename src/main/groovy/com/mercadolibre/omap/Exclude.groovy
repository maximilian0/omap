package com.mercadolibre.omap

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Created by mtaborda on 15-Nov-2016.
 *
 * Annotation used to exclude some object fields.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = [ElementType.FIELD, ElementType.METHOD])
@interface Exclude {}

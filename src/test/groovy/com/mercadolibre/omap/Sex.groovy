package com.mercadolibre.omap

/**
 * Created by mtaborda on 22-Nov-2016.
 */
enum Sex {

    MALE('male', 'Male'),
    FEMALE('female', 'Female')

    String id
    String description

    /**
     * Private constructor.
     *
     * @param id
     * @param description
     */
    private Sex(String id, String description) {

        this.id = id
        this.description = description
    }
}
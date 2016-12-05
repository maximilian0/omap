package com.mercadolibre.omap

/**
 * Created by mtaborda on 29-Nov-2016.
 */
class CollectionType extends Type {

    static boolean canHandle(Object object) {

        return object in Collection
    }

    Object value(Object object, OMap omap) {

        List objects = object as List
        List result = []
        objects.inject(result) { List list, Object it ->
            list.add(omap.toMap(it))
            list
        }
        return result
    }

    Object read(Map.Entry<String, Object> entry, Class clazz, Class type, OMap omap) {

        return this.collectionFromMap(entry, type, omap)
    }

    private List collectionFromMap(Map.Entry<String, Object> entry, Class type, OMap omap) {

        Class t = type.getDeclaredField(entry.key).getGenericType().getActualTypeArguments()[0] as Class
        List list = []
        List values = entry.value as List
        values.inject(list) { List l, Map<String, Object> i ->
            l.add(omap.fromMap(i, t))
            l
        }
        return list
    }
}

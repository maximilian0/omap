package com.mercadolibre.omap

/**
 * Created by mtaborda on 11-Nov-2016.
 */
class Person {

    @Include
    private String name
    @Exclude
    private int age
    private Document document
    @Exclude
    private Sex sex
    @Exclude
    private String maritalStatus
    @Exclude
    private Set<Address> addresses
    @Exclude
    private Date birth

    static mapFields = ['document', 'age']
    static mapGetters = ['name']

    @Exclude
    String getName() {
        return name
    }

    @Include(keyName = "age")
    int getAge() {
        return age
    }

    Document getDocument() {
        return document
    }

    Sex getSex() {
        return sex
    }

    String getMaritalStatus() {
        return maritalStatus
    }

    Set<Address> getAddresses() {
        return addresses
    }

    Date getBirth() {
        return birth
    }

    void addToAddresses(Address address) {

        if (this.addresses == null) this.addresses = new LinkedHashSet<>()
        this.addresses.add(address)
    }
}

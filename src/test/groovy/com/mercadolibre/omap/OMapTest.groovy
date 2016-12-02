package com.mercadolibre.omap

/**
 * Created by mtaborda on 11-Nov-2016.
 */
class OMapTest extends GroovyTestCase {

    void testToMapDefault() {

        Person person = new Person(name: 'charly', age: 65)
        OMap omap = new OMapBuilder().build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 2
        assert result["name"] == "charly"
        assert result["age"] == 65
    }

    void testToMapDefaultWithNulls() {

        Person person = new Person(name: 'charly', age: 65)
        OMap omap = new OMapBuilder()
                .adaptNulls()
                .build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 6
        assert result["name"] == "charly"
        assert result["age"] == 65
        assert result["document"] == null
        assert result["sex"] == null
        assert result["maritalStatus"] == null
        assert result["addresses"] == null
    }

    void testToMapDefaultWithEnum() {

        Person person = new Person(name: 'charly', age: 65, sex: Sex.MALE)
        OMap omap = new OMapBuilder()
                .build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 3
        assert result["name"] == "charly"
        assert result["age"] == 65
        assert result["sex"] == Sex.MALE.name()
    }

    void testToMapExcludingAnnotatedFields() {

        Person person = new Person(name: 'charly', age: 65)
        OMap omap = new OMapBuilder()
                .registerAdapter(Person, new ExcludeAnnotatedAdapterStrategy())
                .build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 1
        assert result["name"] == "charly"
    }

    void testToMapIncludingAnnotatedFields() {

        Person person = new Person(name: 'charly', age: 65)
        OMap omap = new OMapBuilder()
                .registerAdapter(Person, new IncludeAnnotatedAdapterStrategy())
                .build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 2
        assert result["name"] == "charly"
        assert result["age"] == 65
    }

    void testToMapIncludingAnnotatedFieldsWithCustomKey() {

        Document document = new Document(type: 'dni', number: '26018550')
        OMap omap = new OMapBuilder()
                .registerAdapter(Document, new IncludeAnnotatedAdapterStrategy())
                .build()

        Map<String, Object> result = omap.toMap(document)
        assert result.size() == 2
        assert result["someDifferentToTypeKey"] == "dni"
        assert result["someDifferentToNumberKey"] == '26018550'
    }

    void testToMapIncludingMapped() {

        Person person = new Person(name: 'charly', age: 65)
        OMap omap = new OMapBuilder()
                .registerAdapter(Person, new IncludeMappedAdapterStrategy())
                .build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 2
        assert result["name"] == "charly"
        assert result["age"] == 65
    }

    void testComplexObjectToMap() {

        Document document = new Document(type: 'dni', number: '26018550')
        Person person = new Person(name: 'charly', age: 65, document: document)
        OMap omap = new OMapBuilder().build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 3
        assert result["name"] == "charly"
        assert result["age"] == 65
        assert ((Map) result["document"]).size() == 2
        assert result["document"]["type"] == "dni"
        assert result["document"]["number"] == '26018550'
    }

    void testObjectWithCollectionAttributeToMap() {

        Address home = new Address(street: 'la esquina del infinito', number: 666)
        Address work = new Address(street: 'donde el viento pega la vuelta', number: 18)
        Person person = new Person(name: 'charly', age: 65, addresses: [home, work])
        OMap omap = new OMapBuilder().build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 3
        assert result["name"] == "charly"
        assert result["age"] == 65
        assert result["addresses"].size() == 2
        assert result["addresses"][0]["street"] == 'la esquina del infinito'
        assert result["addresses"][0]["number"] == 666
        assert result["addresses"][1]["street"] == 'donde el viento pega la vuelta'
        assert result["addresses"][1]["number"] == 18
    }

    void testCustomToMap() {

        Person person = new Person(name: 'charly', age: 65)
        AdapterStrategy strategy = new CustomMemberAdapterStrategy(
                fieldNames: ['name', 'document'],
                methodNames: ['addresses'])
        OMap omap = new OMapBuilder()
                .registerAdapter(Person, strategy)
                .adaptNulls()
                .build()

        Map<String, Object> result = omap.toMap(person)
        assert result.size() == 3
        assert result["name"] == "charly"
        assertNull(result["document"])
        assertNull(result["addresses"])
    }

    void testToMapList() {

        List<Person> people = [new Person(name: 'charly', age: 65)]
        OMap omap = new OMapBuilder().build()

        List<Map<String, Object>> result = omap.toMapList(people)
        assert result.size() == 1
        assert result[0]["name"] == "charly"
        assert result[0]["age"] == 65
    }

    void testFromMap() {

        Map<String, Object> map = [
                "name": "charly",
                "age" : 65
        ]
        OMap omap = new OMapBuilder().build()

        Person person = (Person) omap.fromMap(map, Person)
        assert person.name == "charly"
        assert person.age == 65
    }

    void testComplexFromMap() {

        Map<String, Object> map = [
                "name"    : "charly",
                "age"     : 65,
                "document": [
                        "type"  : "dni",
                        "number": "26018550"
                ]
        ]
        OMap omap = new OMapBuilder().build()

        Person person = omap.fromMap(map, Person)
        assert person.name == "charly"
        assert person.age == 65
        assert person.document.type == "dni"
        assert person.document.number == "26018550"
    }

    void testFromMapWithEnum() {

        Map<String, Object> map = [
                "name": "mary",
                "age" : 34,
                "sex" : "FEMALE"
        ]

        OMap omap = new OMapBuilder().build()

        Person person = omap.fromMap(map, Person)
        assert person.name == "mary"
        assert person.age == 34
        assert person.sex == Sex.FEMALE
    }

    void testObjectWithCollectionAttributeFromMap() {

        Map<String, Object> map = [
                "name"     : "charly",
                "age"      : 65,
                "addresses": [
                        [
                                "street": "la esquina del infinito",
                                "number": 666
                        ],
                        [
                                "street": "donde el viento pega la vuelta",
                                "number": 18
                        ]
                ]
        ]
        OMap omap = new OMapBuilder().build()

        Person person = omap.fromMap(map, Person)
        assert person.name == "charly"
        assert person.age == 65
        assertNotNull(person.addresses)
        assert person.addresses.size() == 2
        assert person.addresses[0].street == 'la esquina del infinito'
        assert person.addresses[0].number == 666
        assert person.addresses[1].street == 'donde el viento pega la vuelta'
        assert person.addresses[1].number == 18
    }

    void testFromMapList() {

        List<Map<String, Object>> list = [[
                                                  "name": "charly",
                                                  "age" : 65
                                          ]]
        OMap omap = new OMapBuilder().build()

        List<Person> people = (List<Person>) omap.fromMapList(list, Person)
        assertNotNull(people)
        assert people.size() == 1
        assert people[0].name == "charly"
        assert people[0].age == 65
    }

    void testFromMapWithSnakeCase() {

        Map<String, Object> map = [
                "name"          : "mary",
                "age"           : 34,
                "marital_status": "single"
        ]

        OMap omap = new OMapBuilder().build()

        Person person = omap.fromMap(map, Person)
        assert person.name == "mary"
        assert person.age == 34
        assert person.maritalStatus == "single"
    }

    void testCustomFromMap() {

        Map<String, Object> map = ["name": "mary"]
        AdapterStrategy strategy = new CustomMemberAdapterStrategy(
                fieldNames: ['name', 'document'],
                methodNames: ['addresses'])
        OMap omap = new OMapBuilder()
                .registerAdapter(Person, strategy)
                .adaptNulls()
                .build()

        Person person = omap.fromMap(map, Person)
        assertNotNull(person)
        assert person.name == "mary"
        assert person.age == 0
        assertNull(person.addresses)
    }

    void testFromMapWithSnakeCaseInvalid() {

        Map<String, Object> map = [
                "name"         : "mary",
                "age"          : 34,
                "maritalStatus": "single"
        ]

        OMap omap = new OMapBuilder()
                .keysToSnakeCase()
                .build()

        shouldFail(NoSuchFieldException, { omap.fromMap(map, Person) })
    }

    void testFromMapForUpdate() {

        Map<String, Object> map = [
                "name"     : "charly",
                "sex"      : "MALE",
                "addresses": [
                        [
                                "street": "la esquina del infinito",
                                "number": 666
                        ],
                        [
                                "street": "donde el viento pega la vuelta",
                                "number": 18
                        ]
                ]
        ]
        OMap omap = new OMapBuilder().build()

        Map<String, Object> args = omap.fromMapForUpdate(map, Person)
        assert args.name == "charly"
        assert args.sex == Sex.MALE
        assertNotNull(args.addresses)
        assert args.addresses.size() == 2
        assert args.addresses[0] instanceof Address
        assert args.addresses[0].street == 'la esquina del infinito'
        assert args.addresses[0].number == 666
        assert args.addresses[1] instanceof Address
        assert args.addresses[1].street == 'donde el viento pega la vuelta'
        assert args.addresses[1].number == 18
    }

    void testFromMapWithInvalidProperty() {

        Map<String, Object> map = [
                "invalidProperty": "charly",
                "age"            : 65
        ]
        OMap omap = new OMapBuilder().build()

        shouldFail(NoSuchFieldException, { omap.fromMap(map, Person) })
    }
}

package namedEntities;

import java.util.List;

enum Category {
    PERSON,
    LOCATION,
    ORGANIZATION,
    EVENT,
    OTHER
}

enum Topics {
    POLITICS,
    SPORTS,
    ECONOMICS,
    HEALTH,
    TECHNOLOGY,
    CULTURE,
    BUSINESS,
    ENTERTAINMENT,
    OTHER
}

public class NamedEntity {
    // Atributos
    private String name;
    private Category category;
    private List<Topics> topic;

    // Constructor
    public NamedEntity(String name, Category category, List<Topics> topic) {
        this.name = name;
        this.category = category;
        this.topic = topic;
    }

    // Setters
    public void set_name(String name) {
        this.name = name;
    }

    public void set_category(Category category) {
        this.category = category;
    }

    public void set_topic(List<Topics> topic) {
        this.topic = topic;
    }

    // Getters
    public String get_name() {
        return name;
    }

    public Category get_category() {
        return category;
    }

    public List<Topics> get_topic() {
        return topic;
    }

    @Override
    public String toString() {
        return "NamedEntity{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", topic=" + topic +
                '}';
    }
}

class Person extends NamedEntity {
    private String id;
    private String country;

    // Constructor
    public Person(String name, Category category, List<Topics> topic, String id, String country) {
        super(name, category, topic);
        this.id = id;
        this.country = country;
    }

    // Setters
    public void set_id(String id) {
        this.id = id;
    }

    public void set_country(String country) {
        this.country = country;
    }

    // Getters
    public String get_id() {
        return id;
    }

    public String get_country() {
        return country;
    }
}

class Location extends NamedEntity {
    private Float latitude;
    private Float longitude;

    // Constructor
    public Location(String name, Category category, List<Topics> topic, Float latitude, Float longitude) {
        super(name, category, topic);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Setters
    public void set_latitude(Float latitude) {
        this.latitude = latitude;
    }

    public void set_longitude(Float longitude) {
        this.longitude = longitude;
    }

    // Getters
    public Float get_latitude() {
        return latitude;
    }

    public Float get_longitude() {
        return longitude;
    }
}

class Organization extends NamedEntity {
    private String headquarters;

    // Constructor
    public Organization(String name, Category category, List<Topics> topic, String headquarters) {
        super(name, category, topic);
        this.headquarters = headquarters;
    }

    // Setters
    public void set_headquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    // Getters
    public String get_headquarters() {
        return headquarters;
    }
}

class Other extends NamedEntity {
    // Constructor
    public Other(String name, Category category, List<Topics> topic) {
        super(name, category, topic);
    }
}
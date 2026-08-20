package com.airtribe.ridewise.model;

public class Rider {
    private String id;
    private String name;
    private double location;

    public Rider(String id, String name, double location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Rider{id='%s', name='%s', location=%.1f}".formatted(id, name, location);
    }
}

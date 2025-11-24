package com.flightapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "airlines")
public class Airline {
    @Id
    private String id;
    private String name;
    private String logo; // URL or base64 reference

    public Airline() {}
    // getters & setters
    // equals/hashCode/toString (omitted for brevity)
    // ... 
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
}

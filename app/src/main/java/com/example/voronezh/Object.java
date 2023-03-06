package com.example.voronezh;

public class Object {
    private long id;
    private String name;
    private String address;
    private String description;
    private int environ;
    private String location;
    private int type;
    private String phone;
    private String email;
    private String website;
    private int img;

    Object(long id, String name, String address, String description, int environ, String location, int type, String phone, String email, String website){
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.environ = environ;
        this.location = location;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.website = website;
    }

    Object(long id, String name){
        this.id = id;
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEnviron() {
        return environ;
    }

    public void setEnviron(int environ) {
        this.environ = environ;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return this.name;
    }

}

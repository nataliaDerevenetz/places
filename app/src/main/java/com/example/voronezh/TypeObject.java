package com.example.voronezh;

public class TypeObject {
    private String nameType; // название типа
    private int idType;  // id типа
    private int imgResource; // изображение

    public TypeObject(String name, int id, int img){

        this.nameType=name;
        this.idType=id;
        this.imgResource=img;
    }

    public String getName() {
        return this.nameType;
    }

    public void setName(String name) {
        this.nameType = name;
    }

    public int getIdType() {
        return this.idType;
    }

    public void setIdType(int id) {
        this.idType = id;
    }

    public int getImgResource() {
        return this.imgResource;
    }

    public void setImgResource(int img) {
        this.imgResource = img;
    }

}

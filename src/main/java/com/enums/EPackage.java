package com.enums;

/**
 * Created by Piotr on 2017-02-13.
 */
public enum EPackage {
    Toys("toys"),
    Furnitures("furnitures"),
    CarParts("carParts");
    private String name;

    EPackage(String name) {this.name = name;}

    public String toString() {return name;}
}

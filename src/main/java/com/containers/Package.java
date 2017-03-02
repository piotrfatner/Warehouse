package com.containers;

import com.enums.EPackage;

/**
 * Created by Piotr on 2017-02-13.
 */
public class Package {
    private String packageNumber;
    private int priority;
    private int shiftNumber;
    private String description;
    private String date;
    private EPackage type;

    public Package(String packageNumber, int priority, int shiftNumber, String description, String date, EPackage type) {
        this.packageNumber = packageNumber;
        this.priority = priority;
        this.shiftNumber = shiftNumber;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EPackage getType() {
        return type;
    }

    public void setType(EPackage type) {
        this.type = type;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("No: ");
        sb.append(packageNumber).append(" Prior: ").append(priority);
        sb.append(" ShiftNo: ").append(shiftNumber);
        return sb.toString();
    }
}

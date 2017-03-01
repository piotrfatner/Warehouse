package com.service;

import com.containers.Package;
import com.containers.Warehouse;
import com.enums.EPackage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Piotr on 2017-02-14.
 */
public class Crane {
    Logger craneLogger = Logger.getLogger(Crane.class);
    Warehouse warehouse;
    Stack<Package> lastAreaField;
    Stack<Package> nextToLastAreaField;
    public Crane(Warehouse warehouse){
        this.warehouse = warehouse;
        lastAreaField = warehouse.getArea()[warehouse.getAreaWidth() - 1][warehouse.getAreaLength() - 1]; // 3 3
        nextToLastAreaField = warehouse.getArea()[warehouse.getAreaWidth() - 2][warehouse.getAreaLength() - 1];
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void addPackage(Package pack) {

        for (int lengthPointer = 0; lengthPointer < warehouse.getAreaLength(); lengthPointer++) {
            for (int widthPointer = 0; widthPointer < warehouse.getAreaWidth(); widthPointer++) {

                if (isWarehouseFull(widthPointer,lengthPointer)) { // sprawdzenie czy nie zapelniam przedostaniego i ostatneigo miejsca
                    craneLogger.info("All warehouse is full!");
                    return;
                }

                if (checkIfMaxStack(warehouse.getArea()[widthPointer][lengthPointer])) { // sprawdzenie czy pole pelne -> zmienia wskazniki
                    // jesli pelny to
                    if (checkIfEndOfMagazineWidth(widthPointer)) { // jesli koniec szerokosci to przestawiam magazyn
                        lengthPointer++;
                        widthPointer = 0;
                    } else { // w innym wypadku dodajemy tylko szerokosc
                        widthPointer++;
                    }
                }

                if (warehouse.getArea()[widthPointer][lengthPointer].size() == 0) { // sprawdzenie czy puste pole -> od razu wstawia
                    warehouse.getArea()[widthPointer][lengthPointer].push(pack);
                    return;
                }

                if (!checkIfMaxStack(warehouse.getArea()[widthPointer][lengthPointer]) && warehouse.getArea()[widthPointer][lengthPointer].size() > 0) { // jesli mamy jeszcze miejsce na stosie w punkcie [0][0]
                    Package tempPackage = warehouse.getArea()[widthPointer][lengthPointer].pop();
                    if(tempPackage.getPriority() <= pack.getPriority()){
                        warehouse.getArea()[widthPointer][lengthPointer].push(tempPackage);
                    }
                    while ( tempPackage.getPriority() > pack.getPriority()) { // jesli temp package jest tej samej wartosci
                        if (tempPackage.getPriority() == 3) {
                            lastAreaField.push(tempPackage);
                            tempPackage.setShiftNumber(tempPackage.getShiftNumber()+1);
                        }
                        if (tempPackage.getPriority() == 2) {
                            nextToLastAreaField.push(tempPackage);
                            tempPackage.setShiftNumber(tempPackage.getShiftNumber()+1);
                        }
                        if(warehouse.getArea()[widthPointer][lengthPointer].empty()){
                            break;
                        }

                    }
                    warehouse.getArea()[widthPointer][lengthPointer].push(pack);// to wstawiamy packe
                    while (!nextToLastAreaField.empty()) {// i wstaiwamy reszte paczek z przedostatniego pola
                        tempPackage = nextToLastAreaField.pop();
                        if (checkIfMaxStack(warehouse.getArea()[widthPointer][lengthPointer])) { // jesli pelny to
                            if (checkIfEndOfMagazineWidth(widthPointer)) { // jesli koniec szerokosci to przestawiam magazyn
                                if (checkIfEndOfMagazineLenght(lengthPointer)) {
                                    craneLogger.info("All warehouse is full!");
                                    break;
                                }
                                lengthPointer++;
                                widthPointer = 0;
                            } else { // w innym wypadku dodajemy tylko szerokosc
                                widthPointer++;
                            }
                        }
                        warehouse.getArea()[widthPointer][lengthPointer].push(tempPackage);
                        tempPackage.setShiftNumber(tempPackage.getShiftNumber()+1);
                    }
                    while (!lastAreaField.empty()) {
                        tempPackage = lastAreaField.pop();

                        addPackage(tempPackage);
                        tempPackage.setShiftNumber(tempPackage.getShiftNumber()+1);

                    }
                    widthPointer = warehouse.getAreaWidth()-1;
                    lengthPointer = warehouse.getAreaLength()-1;
                    return;
                }
            }
        }
    }

    public void movePackage(Package packageToMove){
        if(lastAreaField.size()==0){
            lastAreaField.push(packageToMove);
            return;
        }

        if(lastAreaField.size()>0 && nextToLastAreaField.size() == 0){
            if(packageToMove.getPriority() < lastAreaField.peek().getPriority()){ // jesli paczka ktora chcemy wstawic ma mniejszy priorytet
                nextToLastAreaField.push(packageToMove);
                return;
            }
            else{
                lastAreaField.push(packageToMove);
                return;
            }
        }

        if(lastAreaField.size()>0 && nextToLastAreaField.size()>0){ // jesli dwa stosy zajete
            if(packageToMove.getPriority() == lastAreaField.peek().getPriority()){
                lastAreaField.push(packageToMove);
                return;
            }
            if(packageToMove.getPriority() == nextToLastAreaField.peek().getPriority()){
                nextToLastAreaField.push(packageToMove);
                return;
            }
            if((packageToMove.getPriority() < lastAreaField.peek().getPriority()) && (packageToMove.getPriority() < nextToLastAreaField.peek().getPriority())){ // najbardziej zaglebiona -> jesli dwa zajete i priorytet wstawiania mniejszy od dwojga
                while(!lastAreaField.empty()){ // dopoki sie nie oprozni
                    lastAreaField.peek().setShiftNumber(lastAreaField.peek().getShiftNumber()+1);
                    nextToLastAreaField.push(lastAreaField.pop());
                }
                lastAreaField.push(packageToMove);
                return;
            }
        }
    }


    public Package getPackageByNumber(String packageNumber){
        Stack<Package> stackWithPackage = getStackWithSearchedPackage(packageNumber);
        if(stackWithPackage != null){
            while(!stackWithPackage.peek().getPackageNumber().equalsIgnoreCase(packageNumber)){
                movePackage(stackWithPackage.pop());
            }
            Package searchedPackage = stackWithPackage.pop();
            insertPopedPackages(stackWithPackage);
            return searchedPackage;
        }
        return null;
    }

    public Stack<Package> getStackWithSearchedPackage(String packageNumber){
        for (int lengthPointer = 0; lengthPointer < warehouse.getAreaLength(); lengthPointer++) {
            for (int widthPointer = 0; widthPointer < warehouse.getAreaWidth(); widthPointer++) {
                List<Package> searchList = (List<Package>) warehouse.getArea()[widthPointer][lengthPointer];
                for(int listIterator = 0 ; listIterator<searchList.size(); listIterator++){
                    if(searchList.get(listIterator).getPackageNumber().equalsIgnoreCase(packageNumber)){
                        return warehouse.getArea()[widthPointer][lengthPointer];
                    }

                }
            }
        }
        return null;
    }

    public List<Package> getAllPackagesByType(EPackage typeOfPackage){
        List<Package> packageListWithProperType = new ArrayList<Package>();
        for (int lengthPointer = 0; lengthPointer < warehouse.getAreaLength(); lengthPointer++) {
            for (int widthPointer = 0; widthPointer < warehouse.getAreaWidth(); widthPointer++) {
                List<Package> searchList = (List<Package>) warehouse.getArea()[widthPointer][lengthPointer];
                for(int listIterator = 0 ; listIterator<searchList.size(); listIterator++){
                    if(searchList.get(listIterator).getType().equals(typeOfPackage)){
                        packageListWithProperType.add(searchList.get(listIterator));
                    }
                }
            }
        }
        return packageListWithProperType;
    }

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse(4,4,2);
        Stack<Package> exampleStack = new Stack<Package>();
        warehouse.getArea()[0][0] = exampleStack;

        Package tempPackage = new Package("1aa",2,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage2 = new Package("2aa",1,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Crane crane = new Crane(warehouse);
        crane.addPackage(tempPackage);
        crane.addPackage(tempPackage2);

        System.out.println(crane.getPackageByNumber("12aa"));
        System.out.println(crane.getAllPackagesByType(EPackage.Furnitures));
    }

    public void insertPopedPackages(Stack<Package> targetStack){
        if(!lastAreaField.empty() && lastAreaField.peek().getPriority() == 1){

            while(!lastAreaField.empty()){
                lastAreaField.peek().setShiftNumber(lastAreaField.peek().getShiftNumber()+1);
                targetStack.push(lastAreaField.pop());
            }

            while(!nextToLastAreaField.empty() && nextToLastAreaField.peek().getPriority() == 3){
                nextToLastAreaField.peek().setShiftNumber(nextToLastAreaField.peek().getShiftNumber()+1);
                lastAreaField.push(nextToLastAreaField.pop());
            }

            while(!nextToLastAreaField.empty()){
                nextToLastAreaField.peek().setShiftNumber(nextToLastAreaField.peek().getShiftNumber()+1);
                targetStack.push(nextToLastAreaField.pop());
            }

            while(!lastAreaField.empty()){
                lastAreaField.peek().setShiftNumber(lastAreaField.peek().getShiftNumber()+1);
                targetStack.push(lastAreaField.pop());
            }
        }
        else{
            while(!nextToLastAreaField.empty()){
                nextToLastAreaField.peek().setShiftNumber(nextToLastAreaField.peek().getShiftNumber()+1);
                targetStack.push(nextToLastAreaField.pop());
            }
            while(!lastAreaField.empty()){
                lastAreaField.peek().setShiftNumber(lastAreaField.peek().getShiftNumber()+1);
                targetStack.push(lastAreaField.pop());
            }
        }
    }

    public boolean checkIfMaxStack(Stack<Package> stack){
        if(stack.size()< warehouse.getHeightLimit()){
            return false;
        }
        return true;
    }

    public boolean checkIfEndOfMagazineWidth(int width){
        if(width == warehouse.getAreaWidth()){
            return true;
        }
        return false;
    }

    public boolean checkIfEndOfMagazineLenght(int length){
        if(length==warehouse.getAreaLength()){
            return true;
        }
        return false;
    }

    public boolean isWarehouseFull(int widthPointer, int lengthPointer){
        if(warehouse.getArea()[widthPointer][lengthPointer] == nextToLastAreaField || warehouse.getArea()[widthPointer][lengthPointer] == lastAreaField){
            return true;
        }
        return false;
    }
}

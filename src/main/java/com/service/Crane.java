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


    public void addPackage(Package newPackage){
        Stack<Package> stackWithEmptySlot = findStackToInsert();
        if(stackWithEmptySlot != null){
            while(!stackWithEmptySlot.empty() && stackWithEmptySlot.peek().getPriority()>newPackage.getPriority()){
                movePackage(stackWithEmptySlot.pop());
            }
            stackWithEmptySlot.push(newPackage);
            insertPopedPackages(stackWithEmptySlot);
        }
        else {
            return;
        }

    }

    public Stack<Package> findStackToInsert(){
        Stack<Package> stackWithEmptySlot = null;
        for (int lengthPointer = 0; lengthPointer < warehouse.getAreaLength(); lengthPointer++) {
            for (int widthPointer = 0; widthPointer < warehouse.getAreaWidth(); widthPointer++) {
                if(!isWarehouseFull(widthPointer,lengthPointer) && warehouse.getArea()[widthPointer][lengthPointer].size() < warehouse.getHeightLimit()){
                    stackWithEmptySlot = warehouse.getArea()[widthPointer][lengthPointer];
                    return stackWithEmptySlot;
                }
            }
        }
        return stackWithEmptySlot;
    }

    public void movePackage(Package packageToMove){
        if(lastAreaField.size()==0){
            lastAreaField.push(packageToMove);
            packageToMove.setShiftNumber(packageToMove.getShiftNumber()+1);
            return;
        }

        if(lastAreaField.size()>0 && nextToLastAreaField.size() == 0){
            if(packageToMove.getPriority() < lastAreaField.peek().getPriority()){ // jesli paczka ktora chcemy wstawic ma mniejszy priorytet
                nextToLastAreaField.push(packageToMove);
                packageToMove.setShiftNumber(packageToMove.getShiftNumber()+1);
                return;
            }
            else{
                lastAreaField.push(packageToMove);
                packageToMove.setShiftNumber(packageToMove.getShiftNumber()+1);
                return;
            }
        }

        if(lastAreaField.size()>0 && nextToLastAreaField.size()>0){ // jesli dwa stosy zajete
            if(packageToMove.getPriority() == lastAreaField.peek().getPriority()){
                lastAreaField.push(packageToMove);
                packageToMove.setShiftNumber(packageToMove.getShiftNumber()+1);
                return;
            }
            if(packageToMove.getPriority() == nextToLastAreaField.peek().getPriority()){
                nextToLastAreaField.push(packageToMove);
                packageToMove.setShiftNumber(packageToMove.getShiftNumber()+1);
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

    public void generateAndInsertPackages(int numberOfPackages){
        int iterator = 0;
        while(iterator<numberOfPackages){
            int numberOfPackage = iterator+1;
            if(iterator%3 == 1){
                String number = "1" + Integer.toString(numberOfPackage);
                Package packagePrior1 = new Package(number,1,0,"Car Part","02/13/2017", EPackage.CarParts);
                addPackage(packagePrior1);
            }
            if(iterator%3 == 0){
                String number = "2" + Integer.toString(numberOfPackage);
                Package packagePrior2 = new Package(number,2,0,"Toy","03/14/2017", EPackage.Toys);
                addPackage(packagePrior2);
            }
            if(iterator%3 == 2){
                String number = "3" + Integer.toString(numberOfPackage);
                Package packagePrior3 = new Package(number,3,0,"Toy","03/14/2017", EPackage.Toys);
                addPackage(packagePrior3);
            }
            iterator++;
        }
    }


    public boolean isWarehouseFull(int widthPointer, int lengthPointer){
        if(warehouse.getArea()[widthPointer][lengthPointer] == nextToLastAreaField || warehouse.getArea()[widthPointer][lengthPointer] == lastAreaField){
            return true;
        }
        return false;
    }
}

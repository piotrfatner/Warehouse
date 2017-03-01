package com.containers;

import com.enums.EPackage;

import java.util.Stack;

/**
 * Created by Piotr on 2017-02-13.
 */
public class Warehouse {
    private int areaWidth;
    private int areaLength;
    private int heightLimit;
    private Stack<Package>[][] area;

    public Warehouse(int width, int length, int height){
        heightLimit = height;
        areaWidth = width;
        areaLength = length;
        area = new Stack[width][length];
        for(int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                area[i][j]= new Stack<Package>();
            }
        }
    }

    public int getAreaWidth() {
        return areaWidth;
    }

    public void setAreaWidth(int areaWidth) {
        this.areaWidth = areaWidth;
    }

    public int getAreaLength() {
        return areaLength;
    }

    public void setAreaLength(int areaLength) {
        this.areaLength = areaLength;
    }

    public Stack<Package>[][] getArea() {
        return area;
    }

    public void setArea(Stack[][] area) {
        this.area = area;
    }

    public int getHeightLimit() {
        return heightLimit;
    }

    public void setHeightLimit(int heightLimit) {
        this.heightLimit = heightLimit;
    }

/*    public Crane getCrane() {
        return crane;
    }

    public void setCrane(Crane crane) {
        this.crane = crane;
    }*/

    public void appendPackages(){
        Stack<Package> tempStack = new Stack<Package>();
        Stack<Package> tempStack2 = new Stack<Package>();
        Package tempPackage = new Package("1",1,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage2 = new Package("2",1,0,"second Car Part","02/14/2017",EPackage.CarParts);
        Package tempPackage3 = new Package("2",1,0,"second Car Part","02/14/2017",EPackage.CarParts);
        tempStack.push(tempPackage);
        tempStack.push(tempPackage2);
        tempStack2.push(tempPackage3);
        this.getArea()[0][0]=tempStack;
        this.getArea()[1][0]= tempStack2;

    }

    /*public String toStrings(){
        Logger log = Logger.getLogger(Warehouse.class);

        *//*
        log.info("first capasity: "+area[1][1].size());
        log.info("second stack: "+area[1][2].size());
        Package retPackage = area[1][1].pop();
        log.info("second capacity: "+area[1][1].size());*//*
        return "cos";
        *//*int levels = 0;
        StringBuilder levelMap=new StringBuilder();
        while(levels<height){
            for(int j=0;j<length;j++){
                for(int i=0;i<width;i++){
                    levelMap.append("X");
                }
                levelMap.append("\n");
            }
            levelMap.append("\n\n");
            levels++;
        }
        return levelMap.toString();*//*
    }*/

    public String toString(){
        StringBuilder warehouseMap=new StringBuilder();
        for(int p=0;p<areaWidth;p++){
            warehouseMap.append("   ");
            warehouseMap.append(p+1);
        }
        for(int j=0;j<areaLength;j++){
            warehouseMap.append("\n").append(j+1);
            for(int i=0;i<areaWidth;i++){
                if(area[i][j].size()>0){
                    Stack<Package> tempStack = new Stack<Package>();
                    tempStack.addAll(area[i][j]);
                    while(!tempStack.empty()){
                        warehouseMap.append("-> ").append(tempStack.pop().toString());
                    }

                    warehouseMap.append("||     ");

                }
                else{
                    warehouseMap.append("   X   ");
                }
            }


        }

        return warehouseMap.toString();
    }

    public Stack<Package> reverseStack(Stack<Package> stackToReverse){
        Stack<Package> reversedStack = new Stack<Package>();
        while (!stackToReverse.empty())
        {
            reversedStack.push(stackToReverse.pop());
        }
        return reversedStack;
    }

}

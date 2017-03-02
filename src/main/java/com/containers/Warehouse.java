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


    public String toString(){
        StringBuilder warehouseMap=new StringBuilder();
        for(int p=0;p<areaWidth;p++){
            warehouseMap.append("      ");
            warehouseMap.append(p+1);
        }
        for(int j=0;j<areaLength;j++){
            warehouseMap.append("\n").append(j+1).append("  ");
            for(int i=0;i<areaWidth;i++){
                if(area[i][j].size()>0){
                    Stack<Package> tempStack = new Stack<Package>();
                    tempStack.addAll(area[i][j]);
                    while(!tempStack.empty()){
                        warehouseMap.append(tempStack.pop().toString()).append(" -> ");
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


    public String toString2(){
        StringBuilder warehouseMap=new StringBuilder();

        for(int j=0;j<areaLength;j++){
            for(int i=0;i<areaWidth;i++){
                if(area[i][j].size()>0){
                    warehouseMap.append("Field: ").append(j+1).append(" - ").append(i+1).append(":  ");
                    Stack<Package> tempStack = new Stack<Package>();
                    tempStack.addAll(area[i][j]);
                    while(!tempStack.empty()){
                        warehouseMap.append(tempStack.pop().toString()).append(" -> ");
                    }
                    warehouseMap.append("\n");
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

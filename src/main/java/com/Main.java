package com;

import com.containers.Package;
import com.containers.Warehouse;
import com.enums.EPackage;
import com.service.Crane;
import org.apache.log4j.Logger;

/**
 * Created by Piotr on 2017-02-13.
 */
public class Main {

    /*Zrob shift numberyyy!!*/
    public static void main(String[] args){
        Logger logMain = Logger.getLogger(Main.class);
        Warehouse warehouse = new Warehouse(4,4,2);
        Package tempPackage = new Package("1",2,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage2 = new Package("2",1,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage3 = new Package("1",3,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage4 = new Package("1",3,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage5 = new Package("1",3,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage6 = new Package("1",1,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage7 = new Package("1",1,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage8 = new Package("1",1,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage9 = new Package("1",1,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Package tempPackage10 = new Package("1",1,0,"first Car Part","02/13/2017", EPackage.CarParts);
        Crane cr = new Crane(warehouse);
        cr.addPackage(tempPackage);
        // logMain.info(warehouse.toString());
        cr.addPackage(tempPackage2);
        cr.addPackage(tempPackage3);
        cr.addPackage(tempPackage4);
        cr.addPackage(tempPackage5);
        cr.addPackage(tempPackage6);
        cr.addPackage(tempPackage7);
        cr.addPackage(tempPackage8);
        cr.addPackage(tempPackage9);
        cr.addPackage(tempPackage10);
        logMain.info(warehouse.toString());
        cr.movePackage(tempPackage3);
        cr.movePackage(tempPackage);
        // cr.movePackage(tempPackage8);
        // cr.movePackage(tempPackage9);
        logMain.info(warehouse.toString());
        cr.insertPopedPackages(cr.getWarehouse().getArea()[1][2]);
        logMain.info(warehouse.toString());
    }
}

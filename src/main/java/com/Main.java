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
        Warehouse warehouse = new Warehouse(4,4,5);
        Crane crane = new Crane(warehouse);
        crane.generateAndInsertPackages(70);
        logMain.info(warehouse.toString2());
    }
}

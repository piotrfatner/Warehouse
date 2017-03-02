package com.service;

import com.containers.Package;
import com.containers.Warehouse;
import com.enums.EPackage;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Piotr on 2017-03-01.
 */
public class CraneTest {
    Logger craneTestLogger = Logger.getLogger(CraneTest.class);
    Warehouse warehouse;
    Crane crane;
    Package expectedPackage;
    Package expectedPackageType1;
    Package expectedPackageType2;
    List<Package> expectedPackagesByTypeList;
    @Before
    public void setUp(){
        warehouse = new Warehouse(4,4,4);
        crane = new Crane(warehouse);
        crane.generateAndInsertPackages(8);
        expectedPackage = new Package("6az4",2,0,"first Car Part","02/13/2017", EPackage.CarParts);
        expectedPackageType1 = new Package("101lap",2,0,"laptop1","03/13/2017", EPackage.Laptops);
        expectedPackageType2 = new Package("102lap",2,0,"laptop2","04/13/2017", EPackage.Laptops);
        expectedPackagesByTypeList = new ArrayList<Package>();
        expectedPackagesByTypeList.add(expectedPackageType1);
        expectedPackagesByTypeList.add(expectedPackageType2);
        crane.addPackage(expectedPackage);
        crane.addPackage(expectedPackageType1);
        crane.addPackage(expectedPackageType2);
        crane.generateAndInsertPackages(8);
    }

    @Test
    public void getPackageByNumberTest(){
        craneTestLogger.info(warehouse.toString2());
        Package actualPackage = crane.getPackageByNumber("6az4");
        craneTestLogger.info(warehouse.toString2());
        craneTestLogger.info("Actual Package: \n"+actualPackage);
        assertEquals(expectedPackage,actualPackage);
    }

    @Test
    public void getAllPackagesByType(){
        List<Package> actualPackagesTypeList = crane.getAllPackagesByType(EPackage.Laptops);
        craneTestLogger.info("Actual packages with type: "+ EPackage.Laptops.toString());
        craneTestLogger.info(actualPackagesTypeList.toString());
        assertEquals(expectedPackagesByTypeList,actualPackagesTypeList);
    }




}

package test;

import org.testng.annotations.*;

public class TestNGHooks {

    @Test
    public void testSth(){
        System.out.println("Testing....");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before suites!");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("\tBefore test!");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("\t\tBefore class!");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("\t\t\tBefore method!");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("\t\t\tAfter method!");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("\t\tAfter class!");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("\tAfter test!");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After suite!");
    }
}
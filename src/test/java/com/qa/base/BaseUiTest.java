package com.qa.base;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
public class BaseUiTest {
 protected WebDriver driver;
 @BeforeMethod(alwaysRun=true) public void setUp(){driver=DriverFactory.createDriver();}
 @AfterMethod(alwaysRun=true) public void tearDown(){if(driver!=null) driver.quit();}
}
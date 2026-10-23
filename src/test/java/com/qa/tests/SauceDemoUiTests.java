package com.qa.tests;
import com.qa.base.BaseUiTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SauceDemoUiTests extends BaseUiTest {
 private static final String URL="https://www.saucedemo.com/";
 private void login(String u,String p){driver.get(URL);driver.findElement(By.id("user-name")).sendKeys(u);driver.findElement(By.id("password")).sendKeys(p);driver.findElement(By.id("login-button")).click();}
 private WebElement waitFor(By by){return new WebDriverWait(driver,Duration.ofSeconds(8)).until(ExpectedConditions.visibilityOfElementLocated(by));}
 @Test public void validLoginShowsInventory(){login("standard_user","secret_sauce");Assert.assertTrue(new WebDriverWait(driver,Duration.ofSeconds(8)).until(ExpectedConditions.urlContains("inventory.html")));Assert.assertTrue(driver.findElement(By.className("inventory_list")).isDisplayed());}
 @Test public void invalidLoginShowsError(){login("invalid_user","wrong_password");Assert.assertTrue(waitFor(By.cssSelector("[data-test='error']")).getText().contains("Username and password do not match"));}
 @DataProvider(name="lockedUser") public Object[][] lockedUser(){return new Object[][]{{"locked_out_user","secret_sauce"}};}
 @Test(dataProvider="lockedUser") public void lockedOutUserCannotLogin(String u,String p){login(u,p);Assert.assertTrue(waitFor(By.cssSelector("[data-test='error']")).getText().contains("locked out"));}
 @Test public void productCanBeAddedToCart(){login("standard_user","secret_sauce");waitFor(By.className("inventory_list"));driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();Assert.assertEquals(driver.findElement(By.className("shopping_cart_badge")).getText(),"1");}
 @Test public void cartDisplaysAddedProduct(){login("standard_user","secret_sauce");waitFor(By.className("inventory_list"));driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();driver.findElement(By.className("shopping_cart_link")).click();Assert.assertTrue(driver.findElement(By.className("cart_item")).getText().contains("Sauce Labs Backpack"));}
 @Test public void checkoutRequiresCustomerInformation(){login("standard_user","secret_sauce");waitFor(By.className("inventory_list"));driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();driver.findElement(By.className("shopping_cart_link")).click();driver.findElement(By.id("checkout")).click();driver.findElement(By.id("continue")).click();Assert.assertTrue(waitFor(By.cssSelector("[data-test='error']")).getText().toLowerCase().contains("first name"));}
}
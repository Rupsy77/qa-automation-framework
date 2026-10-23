package com.qa.base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
public final class DriverFactory {
 private DriverFactory() {}
 public static WebDriver createDriver() {
  ChromeOptions options=new ChromeOptions();
  if(Boolean.parseBoolean(System.getProperty("headless","true"))) options.addArguments("--headless=new");
  options.addArguments("--no-sandbox","--disable-dev-shm-usage","--window-size=1440,1000");
  WebDriver driver=new ChromeDriver(options);
  driver.manage().timeouts().implicitlyWait(Duration.ZERO);
  driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
  return driver;
 }
}
package com.qa.base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
public final class ReportManager {
 private static ExtentReports extent;
 private ReportManager() {}
 public static synchronized ExtentReports getInstance() {
  if(extent==null) {
   new File("reports").mkdirs();
   ExtentSparkReporter spark=new ExtentSparkReporter("reports/automation-report.html");
   spark.config().setDocumentTitle("QA Automation Report"); spark.config().setReportName("UI and API Regression Suite");
   extent=new ExtentReports(); extent.attachReporter(spark);
   extent.setSystemInfo("Framework","Selenium + TestNG + Rest Assured");
   extent.setSystemInfo("Java",System.getProperty("java.version"));
  }
  return extent;
 }
}
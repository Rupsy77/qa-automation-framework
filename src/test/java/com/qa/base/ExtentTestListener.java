package com.qa.base;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public class ExtentTestListener implements ITestListener {
 private final ConcurrentMap<String, ExtentTest> tests=new ConcurrentHashMap<>();
 @Override public void onTestStart(ITestResult result){
  String key=result.getMethod().getQualifiedName()+"-"+result.getStartMillis();
  ExtentTest test=ReportManager.getInstance().createTest(result.getMethod().getMethodName());
  tests.put(key,test); result.setAttribute("extentKey",key);
 }
 private ExtentTest current(ITestResult result){Object key=result.getAttribute("extentKey");return key==null?null:tests.get(key.toString());}
 @Override public void onTestSuccess(ITestResult result){ExtentTest t=current(result);if(t!=null)t.pass("Test passed");}
 @Override public void onTestFailure(ITestResult result){ExtentTest t=current(result);if(t!=null)t.fail(result.getThrowable());}
 @Override public void onTestSkipped(ITestResult result){ExtentTest t=current(result);if(t!=null)t.skip("Test skipped");}
 @Override public void onFinish(ITestContext context){ReportManager.getInstance().flush();}
}
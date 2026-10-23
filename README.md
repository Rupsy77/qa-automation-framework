# QA Automation Framework — Selenium + TestNG + Rest Assured

Small Java/Maven automation framework for [SauceDemo](https://www.saucedemo.com/) UI tests and [Restful-Booker](https://restful-booker.herokuapp.com/) API tests.

## Technology choices
- **Java 17 + Maven:** common Java stack and repeatable dependency management.
- **Selenium WebDriver:** browser UI automation with explicit waits.
- **TestNG:** assertions, lifecycle hooks, and data-driven tests with `@DataProvider`.
- **Rest Assured:** HTTP requests and response assertions.
- **ExtentReports:** HTML report at `reports/automation-report.html`.
- **GitHub Actions:** executes the suite on every push and pull request; uploads report artifacts.

## Project structure
```text
.github/workflows/automation-tests.yml
src/test/java/com/qa/base/
  BaseUiTest.java
  DriverFactory.java
  ReportManager.java\n  ExtentTestListener.java
src/test/java/com/qa/tests/
  SauceDemoUiTests.java
  RestfulBookerApiTests.java
pom.xml
testng.xml
README.md
```

## Coverage
### UI — 6 tests
1. Valid login opens inventory.
2. Invalid login displays an error (negative).
3. Locked-out user cannot log in (data-driven).
4. Add product to cart.
5. Cart displays selected product.
6. Checkout requires customer information (negative).

### API — 5 tests
1. Health endpoint returns `201`.
2. Booking list returns an array.
3. Create booking and verify retrieval.
4. Update booking using token authentication.
5. Invalid booking IDs return `404` (data-driven negative).

## Prerequisites
- JDK 17+
- Maven 3.9+
- Google Chrome installed
- Internet access to the public practice apps

## Run locally
```bash
mvn clean test
```
Run with a visible browser:
```bash
mvn clean test -Dheadless=false
```
Open `reports/automation-report.html` for the HTML report. Surefire output is in `target/surefire-reports/`.

## CI workflow
`.github/workflows/automation-tests.yml` runs on every push, pull request, or manual dispatch. The HTML report and Surefire output are uploaded as workflow artifacts, including when tests fail.

## Design notes
Driver setup and report creation are centralized. Explicit waits are used instead of fixed sleeps. API tests check status codes and response content. This deliberately small framework is easy to review; a production framework would add configuration profiles, richer evidence capture, and cleanup.

## What I would add with one more week
1. Add environment profiles and secret management.
2. Attach screenshots, browser logs, and request/response details on failure.
3. Add parallel execution and thread-safe reporting.
4. Add API schema/contract validation and booking cleanup.
5. Add retries only for known transient infrastructure errors and track flaky tests.
6. Add JMeter/Locust load testing and performance trends.
7. Expand cross-browser and accessibility coverage.

## AI/tool disclosure
Starter project prepared with ChatGPT assistance. Review, run, and understand the code before submitting. Public practice services may be temporarily unavailable or rate-limited.

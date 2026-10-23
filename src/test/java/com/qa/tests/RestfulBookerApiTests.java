package com.qa.tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestfulBookerApiTests {
 private static String token;
 @BeforeClass public void configure(){
  RestAssured.baseURI=System.getProperty("api.baseUrl","https://restful-booker.herokuapp.com");
  Response response=given().contentType("application/json").body("{\"username\":\"admin\",\"password\":\"password123\"}").post("/auth");
  Assert.assertEquals(response.statusCode(),200);token=response.jsonPath().getString("token");Assert.assertNotNull(token);
 }
 @Test public void healthEndpointReturnsCreated(){given().get("/ping").then().statusCode(201);}
 @Test public void bookingListReturnsArray(){given().get("/booking").then().statusCode(200).body("$",not(empty()));}
 @Test public void createAndReadBooking(){
  String body="{\"firstname\":\"Taylor\",\"lastname\":\"Tester\",\"totalprice\":125,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2026-11-01\",\"checkout\":\"2026-11-05\"},\"additionalneeds\":\"Breakfast\"}";
  int id=given().contentType("application/json").body(body).post("/booking").then().statusCode(200).body("booking.firstname",equalTo("Taylor")).extract().path("bookingid");
  given().get("/booking/"+id).then().statusCode(200).body("lastname",equalTo("Tester")).body("totalprice",equalTo(125));
 }
 @Test public void updateBookingWithAuth(){
  String body="{\"firstname\":\"Morgan\",\"lastname\":\"QA\",\"totalprice\":220,\"depositpaid\":false,\"bookingdates\":{\"checkin\":\"2026-12-10\",\"checkout\":\"2026-12-12\"},\"additionalneeds\":\"Late checkout\"}";
  int id=given().contentType("application/json").body(body).post("/booking").then().statusCode(200).extract().path("bookingid");
  given().cookie("token",token).contentType("application/json").body(body).put("/booking/"+id).then().statusCode(200).body("firstname",equalTo("Morgan"));
 }
 @DataProvider(name="invalidIds") public Object[][] invalidIds(){return new Object[][]{{-1},{0},{999999999}};}
 @Test(dataProvider="invalidIds") public void invalidBookingIdReturnsNotFound(int id){given().get("/booking/"+id).then().statusCode(404);}
}
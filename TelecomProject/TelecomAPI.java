package com.TelecomProject;

import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;


public class TelecomAPI 
{
	String tokenvalue;
	 String logintoken;
	 String id;
	 String dynamicEmail;
	 
@Test(priority=1)
 public void addNewUser() {
	String email= "Santhosh"+System.currentTimeMillis()+"@gmail.com";
	  Response res= given()
			  .log().all()
			  .header("Accept","application/json")
			  .header("Content-Type", "application/json")
			  .body("{ \n"
			  		+ "\"firstName\": \"Santhosh\", \n"
			  		+ "\"lastName\": \"Chityala\", \n"
			  		+ "\"email\": \"" + email + "\", \n"
			  		+ "\"password\": \"MyPassword\" \n"
			  		+ "}")
			  
			 .when().post("https://thinking-tester-contact-list.herokuapp.com/users");
			  
			  res.then()
			  .log().all()
	            .statusCode(201);
			  
			  tokenvalue=res.jsonPath().getString("token");
			  System.out.println("New User created with status code: "+tokenvalue);
			  System.out.println("User Profile get created with id: "+res.statusCode());
			  System.out.println("User Profile get created with id: "+res.statusLine());
 }
@Test(priority=2,dependsOnMethods ="addNewUser")
 public void getProfile()
 {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization", "Bearer " + tokenvalue)
	  
	  .when().get("https://thinking-tester-contact-list.herokuapp.com/users/me");
	  
	  res.then()
	  .log().body()
	  .statusLine(containsString("OK"))
       .statusCode(200);
	  System.out.println("User Profile get created with id: "+res.statusCode());
	  System.out.println("User Profile get created with id: "+res.statusLine());
 }
@Test(priority=3,dependsOnMethods ="getProfile") 
 public void updateUser()
 {
	 dynamicEmail = "Santhosh" + System.currentTimeMillis() + "@gmail.com"; 
	  Response res=given()
			  .header("Content-Type","application/json")
			  .header("Accept","application/json")
			  .header("Authorization", "Bearer " + tokenvalue)
	  .body("{ \n"
	  		+ "\"firstName\": \"SanthoshVirat\", \n"
	  		+ "\"lastName\": \"Chityala\", \n"
	  		+ "\"email\": \"" + dynamicEmail + "\", \n"
	  		+ "\"password\": \"MyNewPassword\" \n"
	  		+ "} ")
	  
	  .when().patch("https://thinking-tester-contact-list.herokuapp.com/users/me");
	  
	  res.then().log().body() 
	  .statusLine(containsString("OK"))
     .statusCode(200);
	  
	  System.out.println("User updated with status code : "+res.statusCode());
	    
 }
@Test(priority=4,dependsOnMethods ="updateUser")
 public void loginUser()
 {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .body("{ \n"
	  		+ " \n"
	  		+ " \n"
	  		+ "\"email\": \""+ dynamicEmail +"\", \n"
	  		+ "\"password\": \"MyNewPassword\" \n"
	  		+ "} ")
	  .when().post("https://thinking-tester-contact-list.herokuapp.com/users/login");
	  
	  res.then().log().body()
	  .statusLine(containsString("OK"))
     .statusCode(200);
	  
	  logintoken=res.jsonPath().getString("token");
	  System.out.println("User login with status code: "+res.statusCode());
	  System.out.println("User Profile get created with id: "+res.statusLine());
 }
@Test(priority=5,dependsOnMethods ="loginUser")
 public void addContact()
 {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization", "Bearer " + tokenvalue)
	  .body("{ \n"
	  		+ "\"firstName\": \"Srivani\", \n"
	  		+ "\"lastName\": \"Chityala\", \n"
	  		+ "\"birthdate\": \"2024-12-26\", \n"
	  		+ "\"email\": \"santhosh1735300207915@gmail.com\", \n"
	  		+ "\"phone\": \"8005555555\", \n"
	  		+ "\"street1\": \"12 Main St.\", \n"
	  		+ "\"street2\": \"Apartment S\", \n"
	  		+ "\"city\": \"Hyderabad\", \n"
	  		+ "\"stateProvince\": \"TS\", \n"
	  		+ "\"postalCode\": \"500044\", \n"
	  		+ "\"country\": \"India\" \n"
	  		+ "}")
	  
	  
	  
	  .when().post("https://thinking-tester-contact-list.herokuapp.com/contacts");
	  
	  res.then().log().body();
	  
	  id=res.jsonPath().getString("_id");
	  System.out.println("User contact created with status code: "+res.statusCode());
 }
 
@Test(priority=6,dependsOnMethods ="addContact") 
 
 public void getContactList()
 {
	  Response res=given()
			  .header("Content-Type","application/json")
			  .header("Accept","application/json")
			  .header("Authorization","Bearer "+logintoken)
			  
			  .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts");
	  
	  res.then().log().body();
	  
	  System.out.println("Contact list with code: "+res.statusCode());
	  
			  
 }
 
@Test(priority=7,dependsOnMethods ="getContactList") 
 public void getContact()
 {
	  Response res=given()
 .header("Content-Type","application/json")
 .header("Accept","application/json")
 .header("Authorization","Bearer "+logintoken)
 
 .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts");

res.then().log().body();

System.out.println("Contact data with code: "+res.statusCode());
	  
 }
@Test(priority=8,dependsOnMethods ="getContact")
 public void updateContact()
 {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization","Bearer "+logintoken)
	  
	  .body("{ \n"
	  		+ "\"firstName\": \"Amy\", \n"
	  		+ "\"lastName\": \"Miller\",\n"
	  		+ " \n"
	  		+ " \n"
	  		+ "\"birthdate\": \"1992-02-02\", \n"
	  		+ "\"email\": \"santhosh1735300207915@gmail.com\", \n"
	  		+ "\"phone\": \"8005554242\", \n"
	  		+ "\"street1\": \"13 School St.\", \n"
	  		+ "\"street2\": \"Apt. 5\", \n"
	  		+ "\"city\": \"Washington\", \n"
	  		+ "\"stateProvince\": \"QC\", \n"
	  		+ "\"postalCode\": \"A1A1A1\", \n"
	  		+ "\"country\": \"Canada\" \n"
	  		+ "} ")
	  
	  
	  
	  .when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/"+id);
	  
	  res.then().log().body();
	  System.out.println("User updated with code: "+res.statusCode());
 }
 
@Test(priority=9,dependsOnMethods ="updateContact") 
 public void updateContactpatch()
 {
	  Response res=given()
			  .header("Content-Type","application/json")
			  .header("Accept","application/json")
			  .header("Authorization","Bearer "+logintoken)
			  .body("{ \n"
			  		+ "\"firstName\": \"Anna\", \n"
			  		+ "\"lastName\": \"Chityala\"} ")
			  .when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/"+id);
	  
	  res.then().log().body();
	  System.out.println("User updated with code: "+res.statusCode());
 }
@Test(priority=10,dependsOnMethods ="updateContactpatch")
 public void logoutUser()
 {
	  Response res=given()
	  .header("Content-Type","application/json")
	  .header("Accept","application/json")
	  .header("Authorization","Bearer "+logintoken)
	  .when().post("https://thinking-tester-contact-list.herokuapp.com/users/logout");
	  
	  res.then().log().body();
	  System.out.println("User logout with code: "+res.statusCode());
 }
}


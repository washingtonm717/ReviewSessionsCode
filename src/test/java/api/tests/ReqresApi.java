package api.tests;



import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class ReqresApi {
	
	/*
	 * Send a get request https://reqres.in/api/users
	 * including query param -> page=2
	 * Accept type Json
	 * Verify status code 200, verify response body
	 */
	
	@Test
	public void getUsersTest() {
//		given().accept(ContentType.JSON)
//		.and().params("page", 2)
//		.when().get("https://reqres.in/api/users")
//		.then().assertThat().statusCode(200);
		
		
		Response response = given().accept(ContentType.JSON)
		.and().params("page", 2)
		.when().get("https://reqres.in/api/users");
		
		System.out.println(response.getStatusLine());
		System.out.println(response.getContentType());
		System.out.println(response.headers());
		System.out.println(response.body().asString());
		
		assertEquals (200, response.statusCode());
		assertTrue(response.contentType().contains("application/json"));
		
		Header header = new Header("X-Powered-By", "Express");
		assertTrue(response.headers().asList().contains(header));
		
		JsonPath json = response.jsonPath();
		assertEquals(12, json.getInt("total"));
		assertEquals(4, json.getInt("total_pages"));
		assertEquals(4, json.getInt("data.id[0]"));
		
		//Verify that Charles id is 5
		System.out.println(json.getString("data.find{it.first_name=='Charles'}.id"));// this sytanx is from Groovy
		
		assertEquals(5, json.getInt("data.find{it.first_name=='Charles'}.id"));
		
		// Assert using JsonPath
		/*
		 * "id": 6,
            "first_name": "Tracey",
            "last_name": "Ramos",
		 */
		
		assertEquals("Ramos", json.getString("data.find{it.id==6}.last_name"));
		assertEquals("Tracey", json.getString("data.find{it.id==6}.first_name"));
		
		
	}

}

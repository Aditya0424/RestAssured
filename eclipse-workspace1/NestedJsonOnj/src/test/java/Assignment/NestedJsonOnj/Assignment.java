package Assignment.NestedJsonOnj;

import static io.restassured.RestAssured.given;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import dataproviderexample.valuesfromexcel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import java.io.IOException;
import static org.hamcrest.Matchers.*;

public class Assignment {
	
	
	//TC01Create a Post Request by using JSON Object JSON body must contain nested JSON Object================//
	@SuppressWarnings("unchecked")
	@Test (enabled = true, dataProvider = "valuesfromExcel")
	public void jsonbody(String namePet, String status, String nameCat, String nameTag) {

		RestAssured.baseURI = "http://localhost:3000/";
		JSONObject categoryobject = new JSONObject();
		
		JSONObject bodyobject = new JSONObject();
		JSONObject tagsobject = new JSONObject();

		
		categoryobject.put("id", 0); 
		categoryobject.put("name", nameCat);

		bodyobject.put("id", 0);
		bodyobject.put("name", namePet);
		bodyobject.put("status", status);


		// JSON Array Body for photoURL Array
		JSONArray arraybody1 = new JSONArray();
		arraybody1.add("www.abc.com");

		tagsobject.put("id", 0);
		tagsobject.put("name", nameTag);
		
		// JSON Array Body for tags Array
		JSONArray arraybody2 = new JSONArray();
		arraybody2.add(tagsobject);
	
		
		// Adding all objects into the Rootbody
		bodyobject.put("category", categoryobject);
		bodyobject.put("photoUrls", arraybody1);
		bodyobject.put("tags", arraybody2);
		
		System.out.println(bodyobject);

		
	String response=	 given()
		 .body(bodyobject.toJSONString())
		 .headers("content-type","Application/JSON"). 
		 
		 when()
		 .post("students"). 
		 
		 then() 
		 .log().all()
		 .assertThat().statusCode(201)
		 .body("name", equalTo(namePet))
		 .body("status", equalTo(status))
		 .extract().response().asString();
	
	JsonPath js= new JsonPath(response);
	
	System.out.println(js.getString("name"));
	System.out.println(js.getString("status"));
	System.out.println(js.getInt("id"));
	}
	
	
	@DataProvider(name = "valuesfromExcel")
	public Object[][] exceldata() throws IOException {
		Object[][] data = valuesfromexcel.gettestdata();

		return data;

	}

	
	
	//TC02: Create one Soap request by storing the xml in the local directory and Assert Their status code================//
	
	@Test (enabled = true)
	public void soapexample() throws IOException {
		RestAssured.baseURI = "http://www.dneonline.com";

		FileInputStream fis = new FileInputStream(".\\Payload\\addreq.xml");

		given()
			.headers("content-type","text/xml")
			.body(IOUtils.toString(fis, "UTF-8")).
		when()
			.post("/calculator.asmx").
		then()
			.log().all()
			.assertThat().statusCode(200);
	
		
		
	}
	
	
	

	
}

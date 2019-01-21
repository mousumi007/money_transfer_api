package com.app.money.transfer.instapay;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.app.money.transfer.dao.InitializeDB;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class MoneyTransferTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and Main.startServer())
		// --
		// c.configuration().enable(new
		// org.glassfish.jersey.media.json.JsonJaxbFeature());
		InitializeDB.initializeDB();
		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	/**
	 * Test for moneyTransfer API HTTP 200 Response.
	 */
    @Test
	public void testmoneyTransferSuccessResponse() {

		RestAssured.baseURI = "http://localhost:8089/v1/instaPay/";
		RequestSpecification request = RestAssured.given();

		request.header("Content-Type", "application/json");

		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", "u800120");
		requestParams.put("accountId", "800120678");
		requestParams.put("beneficiaryAccountId", "800121680");
		requestParams.put("bankCode", "800");
		requestParams.put("reason", "Deposit");
		requestParams.put("amount", 5000);

		request.body(requestParams.toString());
		Response response = request.post("/accounts/transfer");

		ResponseBody body = response.getBody();
		
		int statusCode = response.getStatusCode();
		System.out.println(new Date());
		Assert.assertEquals(statusCode, 200);
		Assert.assertEquals("Success", body.jsonPath().get("transactionStatus"));
	}

	/**
	 * Insufficient Balance ## HTTP 417
	 */
	@Test
	public void testmoneyTransferInsufficientBalance() {

		RestAssured.baseURI = "http://localhost:8089/v1/instaPay/";
		RequestSpecification request = RestAssured.given();

		request.header("Content-Type", "application/json");

		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", "u800120");
		requestParams.put("accountId", "800120678");
		requestParams.put("beneficiaryAccountId", "800121680");
		requestParams.put("bankCode", "800");
		requestParams.put("reason", "Deposit");
		requestParams.put("amount", 50000000);

		request.body(requestParams.toString());
		Response response = request.post("/accounts/transfer");
		ResponseBody body = response.getBody();

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 417);
		Assert.assertEquals("INSUFFICIENT_BALANCE", body.jsonPath().get("errorMessage"));
	}

	/**
	 * Test for moneyTransfer API when both the account id's are same.
	 */
    @Test
	public void testmoneyTransferSameAccountId() {

		RestAssured.baseURI = "http://localhost:8089/v1/instaPay/";
		RequestSpecification request = RestAssured.given();

		request.header("Content-Type", "application/json");

		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", "u800121");
		requestParams.put("accountId", "800121679");
		requestParams.put("beneficiaryAccountId", "800121679");
		requestParams.put("bankCode", "800");
		requestParams.put("reason", "Deposit");
		requestParams.put("amount", 5000);

		request.body(requestParams.toString());
		Response response = request.post("/accounts/transfer");

		int statusCode = response.getStatusCode();
		ResponseBody body = response.getBody();

		Assert.assertEquals(statusCode, 403);
		Assert.assertEquals("ACCOUNT_ID_SAME", body.jsonPath().get("errorMessage"));
	}
    
    /**
	 * Test for moneyTransfer API HTTP 404 BAD_REQUEST.
	 */
    @Test
	public void testmoneyTransferBadRequest() {

		RestAssured.baseURI = "http://localhost:8089/v1/instaPay/";
		RequestSpecification request = RestAssured.given();

		request.header("Content-Type", "application/json");

		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", "u800121");
//		requestParams.put("accountId", "800121679");
		requestParams.put("beneficiaryAccountId", "800121679");
		requestParams.put("bankCode", "800");
		requestParams.put("reason", "Deposit");
		requestParams.put("amount", 5000);

		request.body(requestParams.toString());
		Response response = request.post("/accounts/transfer");

		int statusCode = response.getStatusCode();
		ResponseBody body = response.getBody();

		Assert.assertEquals(statusCode, 400);

	}
}

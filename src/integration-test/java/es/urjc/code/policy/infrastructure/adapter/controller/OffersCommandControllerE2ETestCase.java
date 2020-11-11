package es.urjc.code.policy.infrastructure.adapter.controller;


import static es.urjc.code.policy.base.AbstractControllerE2ETestCase.Resources.V1_CREATE_OFFER_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.google.gson.JsonObject;

import es.urjc.code.policy.base.AbstractControllerE2ETestCase;
import es.urjc.code.policy.base.TestDataProvider;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OffersCommandControllerE2ETestCase extends AbstractControllerE2ETestCase {

	private static final String OFFER_NUMBER = "offerNumber";
	private static final String COVERS_PRICES_ATTRIBUT = "coversPrices";
	private static final String TOTAL_PRICE_ATTRIBUT = "totalPrice";
	private static final String OFFER_CMD_CAR = "data/offerRequestCar.json";
	private static final String OFFER_CMD_FARM = "data/offerRequestFarm.json";
	private static final String OFFER_CMD_HOUSE = "data/offerRequestHouse.json";
	private static final String OFFER_CMD_TRAVEL = "data/offerRequestTravel.json";
	
	
	@Test
	void shouldCreateOfferCar() {
		//given
        JsonObject offerRequestJson = TestDataProvider.getRequestJson(OFFER_CMD_CAR);
        
        //when
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(offerRequestJson.toString())

                .when()
                .post(V1_CREATE_OFFER_ENDPOINT.build())
                .prettyPeek()
                .then();
        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body(OFFER_NUMBER, notNullValue())
                .body(TOTAL_PRICE_ATTRIBUT, notNullValue())
                .body(COVERS_PRICES_ATTRIBUT, notNullValue());
	}
	
	@Test
	void shouldCreateOfferHouse() {
		//given
        JsonObject offerRequestJson = TestDataProvider.getRequestJson(OFFER_CMD_HOUSE);
        
        //when
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(offerRequestJson.toString())

                .when()
                .post(V1_CREATE_OFFER_ENDPOINT.build())
                .prettyPeek()
                .then();
        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body(OFFER_NUMBER, notNullValue())
                .body(TOTAL_PRICE_ATTRIBUT, notNullValue())
                .body(COVERS_PRICES_ATTRIBUT, notNullValue());
	}
	
	@Test
	void shouldCreateOfferTravel() {
		//given
        JsonObject offerRequestJson = TestDataProvider.getRequestJson(OFFER_CMD_TRAVEL);
        
        //when
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(offerRequestJson.toString())

                .when()
                .post(V1_CREATE_OFFER_ENDPOINT.build())
                .prettyPeek()
                .then();
        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body(OFFER_NUMBER, notNullValue())
                .body(TOTAL_PRICE_ATTRIBUT, notNullValue())
                .body(COVERS_PRICES_ATTRIBUT, notNullValue());
	}
	
	@Test
	void shouldCreateOfferFarm() {
		//given
        JsonObject offerRequestJson = TestDataProvider.getRequestJson(OFFER_CMD_FARM);
        
        //when
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(offerRequestJson.toString())

                .when()
                .post(V1_CREATE_OFFER_ENDPOINT.build())
                .prettyPeek()
                .then();
        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body(OFFER_NUMBER, notNullValue())
                .body(TOTAL_PRICE_ATTRIBUT, notNullValue())
                .body(COVERS_PRICES_ATTRIBUT, notNullValue());
	}

}

package es.urjc.code.policy.infrastructure.adapter.controller;

import static es.urjc.code.policy.base.AbstractControllerE2ETestCase.Resources.V1_CREATE_OFFER_ENDPOINT;
import static es.urjc.code.policy.base.AbstractControllerE2ETestCase.Resources.V1_CREATE_POLICY_ENDPOINT;
import static es.urjc.code.policy.base.AbstractControllerE2ETestCase.Resources.V1_TERMINATE_POLICIES_ENDPOINT;
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
class TerminatePolicyE2ETestCase extends AbstractControllerE2ETestCase {

	private static final String OFFER_NUMBER = "offerNumber";
	private static final String POLICY_NUMBER = "policyNumber";

	private static final String CREATE_OFFER_CAR_CMD = "data/createOfferCar.json";
	private static final String CREATE_POLICY_CMD = "data/createPolicyCmd.json";
	private static final String TERMINATE_POLICY_CMD = "data/terminatePolicyCmd.json";
	
	@Test
	void shouldTerminatePolicyCarFromOffer() {
		//given
        JsonObject createOfferJson = TestDataProvider.getRequestJson(CREATE_OFFER_CAR_CMD);
        
        String offerNumber = given()
                .contentType(ContentType.JSON)
                .body(createOfferJson.toString())
                .when()
                .post(V1_CREATE_OFFER_ENDPOINT.build())
                .prettyPeek()
                .then().
                statusCode(HttpStatus.CREATED.value()).
                extract().path(OFFER_NUMBER);
        
        JsonObject createPolicyJson = TestDataProvider.getRequestJson(CREATE_POLICY_CMD);
        
        String policyNumber =  given()
                .contentType(ContentType.JSON)
                .body(createPolicyJson.toString().replace("replaceOfferNumber", offerNumber))
                .when()
                .post(V1_CREATE_POLICY_ENDPOINT.build())
                .prettyPeek()
                .then().
                statusCode(HttpStatus.CREATED.value()).
                extract().path(POLICY_NUMBER);
        
        JsonObject terminatePolicyJson = TestDataProvider.getRequestJson(TERMINATE_POLICY_CMD);
        
        //when
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(terminatePolicyJson.toString().replace("replacePolicyNumber", policyNumber))
                .when()
                .post(V1_TERMINATE_POLICIES_ENDPOINT.build())
                .prettyPeek()
                .then();
        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body(POLICY_NUMBER, notNullValue());
		
	}
}

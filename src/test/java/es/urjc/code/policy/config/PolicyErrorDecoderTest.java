package es.urjc.code.policy.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.urjc.code.policy.exception.BusinessException;
import es.urjc.code.policy.exception.CommunicationException;
import es.urjc.code.policy.exception.EntityNotFoundException;
import es.urjc.code.policy.exception.NotAvailableException;
import es.urjc.code.policy.handler.ErrorResponse;
import feign.Request;
import feign.Request.HttpMethod;
import feign.Response;

class PolicyErrorDecoderTest {

	private static final String METHOD_KEY = "test";
	private static final Integer GENERIC_ERROR = 1100;
	private static final String MESSAGE = "Default message";
	private static final String DOMAIN = "http://domain";

	private ObjectMapper mapper = new ObjectMapper();
	private PolicyErrorDecoder policyFeignErrorDecoder;

	@BeforeEach
	public void setUp() {
		this.policyFeignErrorDecoder = new PolicyErrorDecoder();
	}


	@Test
	void returnEntityNotFoundExceptionWhenNotFound() throws Exception {
		// given
		final String path = "/rest/404";
		final Response response = buildResponse(GENERIC_ERROR, 404, DOMAIN + path);

		// when
		final Exception result = policyFeignErrorDecoder.decode(METHOD_KEY, response);

		// then
		assertThat(result, is(instanceOf(EntityNotFoundException.class)));
	}

	@Test
	void returnBusinessExceptionWhenClientError() throws Exception {
		// given
		final String path = "/rest/422";
		final Response response = buildResponse(GENERIC_ERROR, 422, DOMAIN + path);
		// when
		final Exception result = policyFeignErrorDecoder.decode(METHOD_KEY, response);

		// then
		assertThat(result, is(instanceOf(BusinessException.class)));
	}

	@Test
	void returnNotAvailableExceptionWhenAnyServerError() throws Exception {
		// given
		final String path = "/rest/503";
		Response response = buildResponse(GENERIC_ERROR, 503, DOMAIN + path);

		// when
		Exception result = policyFeignErrorDecoder.decode(METHOD_KEY, response);

		// then
		assertThat(result, is(instanceOf(NotAvailableException.class)));
	}

	@Test
	void returnCommunicationExceptionWhenAnyServerError() throws Exception {
		// given
		final String path = "/rest/500";
		Response response = buildResponse(GENERIC_ERROR, 500, DOMAIN + path);

		// when
		Exception result = policyFeignErrorDecoder.decode(METHOD_KEY, response);

		// then
		assertThat(result, is(instanceOf(CommunicationException.class)));
	}

	private Response buildResponse(Integer errorCode, int status, String url) throws IOException {
		final ErrorResponse ospErrorResponse = new ErrorResponse.Builder().withStatus(errorCode).withMessage(MESSAGE).build();

		final Request request = Request.create(HttpMethod.GET, url, Collections.emptyMap(), new byte[0],Charset.defaultCharset());
		return Response.builder().status(status).headers(Collections.emptyMap()).request(request)
				.body(mapper.writeValueAsString(ospErrorResponse), StandardCharsets.UTF_8).build();
	}
	
	
}

package es.urjc.code.policy.config;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.urjc.code.policy.exception.BusinessException;
import es.urjc.code.policy.exception.CommunicationException;
import es.urjc.code.policy.exception.EntityNotFoundException;
import es.urjc.code.policy.exception.NotAvailableException;
import es.urjc.code.policy.handler.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

public class PolicyErrorDecoder implements ErrorDecoder {
	
	private static final Logger LOG = LoggerFactory.getLogger(PolicyErrorDecoder.class);
	protected final ErrorDecoder delegate;
	private ObjectMapper mapper;

	public PolicyErrorDecoder() {
		this.delegate = new ErrorDecoder.Default();
		this.mapper = new ObjectMapper();
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.mapper.registerModule(new Jdk8Module());
		this.mapper.registerModule(new JavaTimeModule());
		this.mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
	}
	
	@Override
	public Exception decode(String methodKey, Response response) {
		LOG.trace("An exception has been caught in {}, trying to parse the payload.", methodKey);

		if (response.body() == null) {
			LOG.error("Failed to parse the payload; response has no body.");
			return delegate.decode(methodKey, response);
		}

		HttpStatus status = HttpStatus.valueOf(response.status());
		return buildException(status, doDecodeResponse(response));
	}
	
	private ErrorResponse doDecodeResponse(Response response) {
		LOG.trace("Decode response");
		try {
			byte[] body = StreamUtils.copyToByteArray(response.body().asInputStream());
			LOG.trace("Has body {}", body.length > 0);
			Optional<ErrorResponse> errorOptional = this.buildErrorInfo(body);
			return errorOptional.orElseThrow();
		} catch (IOException e) {
			LOG.trace("Error on doDecodeResponse {}", e.getMessage());
			return this.buildDefaultErrorInfo(response);
		}
	}
	
	private Optional<ErrorResponse> buildErrorInfo(byte[] body) {
		try {
			LOG.trace("Mapping body {}", body);
			final ErrorResponse errorResponse = this.mapper.readValue(body, ErrorResponse.class);
			if (errorResponse == null) {
				return Optional.empty();
			} else {
				return Optional.of(errorResponse);
			}
		} catch (IOException ioex) {
			return Optional.empty();
		}
	}

	private ErrorResponse buildDefaultErrorInfo(Response response) {
		return (new ErrorResponse.Builder()).withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).withMessage(response.body().toString())
				.build();
	}

	private RuntimeException buildException(HttpStatus status, ErrorResponse errorResponse) {

		if (status == HttpStatus.NOT_FOUND) {
			return new EntityNotFoundException(errorResponse.getMessage());
		}

		if (status.is4xxClientError()) {
			return new BusinessException(errorResponse.getMessage());
		}

		if (status == HttpStatus.SERVICE_UNAVAILABLE) {
			return new NotAvailableException(errorResponse.getMessage());
		}

		if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
			return new CommunicationException(errorResponse.getMessage());
		}
		
		return new RuntimeException();
	}
}
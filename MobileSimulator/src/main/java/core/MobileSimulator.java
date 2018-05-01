package core;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * 
 */
public class MobileSimulator {

	public static String baseURL = "http://159.89.31.4:8080/Kolkata/rest";

	{
		Unirest.setObjectMapper(new ObjectMapper() {
			private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return jacksonObjectMapper.readValue(value, valueType);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			public String writeValue(Object value) {
				try {
					return jacksonObjectMapper.writeValueAsString(value);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	/**
	 * Makes a POST rest request.
	 * @param user {@link User}
	 * @return HttpResponse<String>
	 * @throws UnirestException
	 */
	public HttpResponse<String> addUser(User user) throws UnirestException {
		HttpResponse<String> r = Unirest.post(baseURL + "/user").header("Content-Type", "application/json").body(user).asString();
		return r;
	}
	
	/**
	 * Makes a GET rest request.
	 * @param cpr {@link String}
	 * @return HttpResponse<String>
	 * @throws UnirestException
	 */
	public String getBarcode(String cpr) throws UnirestException {
		HttpResponse<String> r = Unirest.get(baseURL + "/user/barcode/" + cpr).asString();
		return r.getBody();
	}
	
}
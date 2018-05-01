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
public class MerchantSimulator {
	
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
	 * @param merchant {@link Merchant}
	 * @return HttpResponse<String>
	 * @throws IOException
	 * @throws UnirestException
	 */
	public HttpResponse<String> createMerchant(Merchant merchant) throws IOException, UnirestException {
		HttpResponse<String> r = Unirest.post(baseURL + "/merchant").header("Content-Type", "application/json").body(merchant).asString();
		return r;
	}
	
	/**
	 * Makes a POST rest request.
	 * @param transaction {@link Transaction}
	 * @return HttpResponse<String>
	 * @throws IOException
	 * @throws UnirestException
	 */
	public HttpResponse<String> transaction(Transaction transaction) throws IOException, UnirestException {
		HttpResponse<String> r = Unirest.post(baseURL + "/transaction/transfer").header("Content-Type", "application/json").body(transaction).asString();		
		return r;
	}
}
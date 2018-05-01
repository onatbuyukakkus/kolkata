package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 */
public class ResetStorage {
	
	public String resetStorage() throws IOException {
		URL url = new URL("http://159.89.31.4:8080/Kolkata/rest/storage/reset");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		if (conn.getResponseCode() != 200) {
			return ("Failed : HTTP error code : "
					+ conn.getResponseCode() + " with message: " + conn.getResponseMessage());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String response = "";
		String output;
		while ((output = br.readLine()) != null) {
			response = response + " " + output ;
		}
		conn.disconnect();
		return response;
	}
}

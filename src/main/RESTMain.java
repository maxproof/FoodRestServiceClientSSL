package main;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

/**
 * Call the food rest service using SSL.
 * 
 * Use the following vm arguments when running:
 * -Djavax.net.ssl.trustStore=trust_store/keystore.jks
 * -Djavax.net.ssl.trustStorePassword=changeit
 *
 */
public class RESTMain {

	// REST Service
	public static final String REST_URL = "https://localhost:8181/FoodRestServiceProjectSSL/rs/foods";
	public static final int OK_STATUS = Response.Status.OK.getStatusCode();

	static {
		// for localhost testing only
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

			public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
				return hostname.equals("localhost");
			}

		});
	}

	/**
	 * Call the web service and display the response.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Response response;

		// create a client
		Client client = ClientBuilder.newBuilder().build();

		// call the service and get all foods
		response = client.target(REST_URL).request(MediaType.APPLICATION_JSON).get();
		printResponse(response);
		System.out.println();

	}

	/**
	 * Print the response object.
	 * 
	 * @param response
	 */
	private static void printResponse(Response response) {
		StatusType status = response.getStatusInfo();
		int statusCode = status.getStatusCode();
		if (statusCode == OK_STATUS) {
			System.out.println(response.readEntity(String.class));
		} else {
			System.out.printf("Service returned status: \"%d %s\"\n", statusCode, status.getReasonPhrase());
		}
	}

}

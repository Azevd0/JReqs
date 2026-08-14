package httpService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientService {
	private final HttpClient client;
	
	public HttpClientService() {
		this.client = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_1_1)
				.followRedirects(HttpClient.Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(20))
				.build();
	}
	
	public HttpResponse<String> sendRequest(String url, String method, String body) throws IOException, InterruptedException {
		boolean hasBody = method.equals("POST") || method.equals("PUT") || method.equals("PATCH");
		
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(url));
		
		HttpRequest.BodyPublisher bodyPublisher = hasBody
				? HttpRequest.BodyPublishers.ofString(body): HttpRequest.BodyPublishers.noBody();
		
		if(hasBody) {
			requestBuilder.header("Content-Type", "application/json");
		}
		
		switch(method) {
		case "GET": requestBuilder.GET();
		break;
		case "POST": requestBuilder.POST(bodyPublisher);
		break;
		case "PUT": requestBuilder.PUT(bodyPublisher);
		break;
		case "PATCH": requestBuilder.method("PATCH", bodyPublisher);
		break;
		case "DELETE": requestBuilder.DELETE();
		break;
		case "OPTIONS": requestBuilder.method("OPTIONS", bodyPublisher);
		}
		
		return client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
	}
	
	public void errorResponse(int statusCode, String method, String body) {
		if (statusCode >= 200 && statusCode < 300) {
			System.out.println("Status      : success!");
			System.out.println("Body        :");
			System.out.println(body);
		}
		if (statusCode == 405) {
			System.out.println("Warning    	: method " + method + " is not allowed.");
		}
		if(statusCode == 401) {
			System.out.println("Warning     : unauthorized");
		}
		if(statusCode == 403) {
			System.out.println("Warning     : access denied");
		}
		if (statusCode >= 400 
				&& statusCode < 500 
				&& statusCode != 405
				&& statusCode != 401
				&& statusCode!= 403) {
			System.out.println("Warning    	: bad request error");
		}
		if (statusCode >= 500) {
			System.out.println("Warning    	: internal server error");
		}
	}
}

package main;

import java.net.http.HttpResponse;

import httpService.HttpClientService;
import menu.ClientMenu;

public class App {

	public static void main(String[] args) {
		ClientMenu clientMenu = new ClientMenu();
		HttpClientService httpService = new HttpClientService();

		while (true) {
			try {
				System.out.println("================ Welcome to RequestSender ================");
				String url = clientMenu.readUrl();
				
				if (url.equalsIgnoreCase("x")) {
		            System.out.println("Closing RequestSender. Good Bye!");
		            break;
		        }
				String method = clientMenu.selectMethod();
				String body = clientMenu.verifyBody(method);

				System.out.printf("Sending request %s to %s ...", method, url);
				HttpResponse<String> response = httpService.sendRequest(url, method, body);

				System.out.println("\n================ Response Body ================");
				System.out.println("Status Code : " + response.statusCode());
				
				httpService.errorResponse(response.statusCode(), method, response.body());

				System.out.println("===========================================================");

			} catch (Exception ex) {
				System.err.println("Connection refused");
			}
		}

	}
}

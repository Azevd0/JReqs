package main;

import httpService.HttpClientService;
import menu.ClientMenu;

import java.net.http.HttpResponse;

public class MainMenu {

    ClientMenu clientMenu = new ClientMenu();
    HttpClientService httpService = new HttpClientService();

    public void showMenu(){
        while (true) {
            try {
                System.out.println("\n================ Welcome to JReqs ================");
                String url = clientMenu.readUrl();
                if (url.equalsIgnoreCase("x")) {
                    break;
                }
                String method = clientMenu.selectMethod();
                String body = clientMenu.verifyBody(method);

                System.out.printf("Sending request %s to %s ...", method, url);
                long startTime = System.currentTimeMillis();

                HttpResponse<String> response = httpService.sendRequest(url, method, body);
                long responseTimeMs = System.currentTimeMillis() - startTime;

                System.out.println("\n================ Response Body ================");
                System.out.println("Status Code   : " + response.statusCode());
                System.out.println("Response Time : " + responseTimeMs + " ms");

                if (response.body() == null) {
                    throw new Exception("Error! Json body not readable!");
                }
                String trimmedBody = response.body().trim();
                if (!trimmedBody.startsWith("{") && !trimmedBody.startsWith("[")) {
                    throw new Exception("Error! Json body not readable!");
                }
                System.out.println("Body          :\n"+ response.body());
                System.out.println("==================================================");
            } catch (Exception ex) {
                System.err.println("Connection imterrupted");
            }
        }
    }
}

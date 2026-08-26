package main;

import httpService.HttpClientService;
import menu.ClientMenu;
import utils.JsonFormatter;

import java.io.IOException;
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

                String responseBody = response.body();

                if (responseBody == null) {
                    throw new IOException("Error! Json body not readable!");
                }
                String trimmedBody = responseBody.trim();
                if (!trimmedBody.startsWith("{") && !trimmedBody.startsWith("[")) {
                    throw new IOException("Error! Json body not readable!");
                }
                System.out.println(JsonFormatter.formatJson(responseBody));

            } catch (IOException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
            catch (InterruptedException e) {
                System.err.println("Conection interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}

package menu;

import utils.JsonFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ClientMenu {

	private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public boolean isValidUrl(String uriString){
		try {
			URI uri = new URI(uriString);
			return uri.getScheme() != null && uri.getHost() !=null;
		} catch (URISyntaxException e) {
			return false;
		}
	}
	public boolean isHostResolvable(String uriString) {
		try {
			URI uri = new URI(uriString);
			String host = uri.getHost();

			if (host == null) {
				return false;
			}
			InetAddress address = InetAddress.getByName(host);
			return true;

		} catch (UnknownHostException | URISyntaxException e) {
			return false;
		}
	}
	public boolean isValidPort(String uriString){
		try {
            URI uri = new URI(uriString);
			int port = uri.getPort();
			String ip = "127.0.0.1";

			if(port == -1){
				return false;
			}
			try (Socket socket = new Socket(ip, port)) {
				return true;
            } catch (IOException e) {
                return false;
            }
        } catch (URISyntaxException e) {
            return false;
        }
	}

	public String readUrl() throws IOException {
		while (true) {
			System.out.print("Insert the url (or x to exit): ");
			String url = reader.readLine().trim();

			if (url.equalsIgnoreCase("x")) {
				return "x";
			}
			if (!isValidUrl(url)) {
				System.out.println("[!] Invalid or malformed URL. Please check the address (e.g., http://localhost:8080/api).");
				continue;
			}
			if (!isHostResolvable(url)) {
				System.out.println("[!] Host not found. Please check for typos in the domain.");
				continue;
			}
			if(!isValidPort(url)){
				System.out.println("[!] Port not found. Please check for port number in the domain (e.g., ':8080/').");
				continue;
			}
			return url;
		}
	}

	public String selectMethod() throws IOException {
		while (true) {
			System.out.println("What is the request type?\n1 - GET\n2 - POST\n3 - PUT\n4 - PATCH\n5 - DELETE\n6 - OPTIONS\nChoose from 1 to 6: \" ");
			String input = reader.readLine().trim();

			try {
				int option = Integer.parseInt(input);
				switch (option) {
					case 1: return "GET";
					case 2: return "POST";
					case 3: return "PUT";
					case 4: return "PATCH";
					case 5: return "DELETE";
					case 6: return "OPTIONS";
					default:
						System.out.println("[!] Invalid option. Please choose a number between 1 and 6.\n");
				}
			} catch (NumberFormatException e) {
				System.out.println("[!] Invalid input, enter numbers only.\n");
			}
		}
	}

	public Map<String, String> readHeaders() throws IOException {
		Map<String, String> headers = new HashMap<>();

		while (true) {
			System.out.println("\nDo you want to add a Header?\n1 - Authorization (Bearer Token)\n2 - Content-Type\n3 - Custom Header (Key / Value)\n0 - Done / Skip\nChoose an option: ");
			String input = reader.readLine().trim();
			int option;

			try {
				option = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("[!] Invalid input, enter numbers only.");
				continue;
			}

			switch (option) {
				case 1:
					System.out.print("Insert Token: ");
					String token = reader.readLine().trim();
					headers.put("Authorization", "Bearer " + token);
					System.out.println("Authorization header added!");
					break;

				case 2:
					System.out.print("Insert Content-Type (e.g., application/json, text/plain): ");
					String contentType = reader.readLine().trim();
					headers.put("Content-Type", contentType);
					System.out.println("Content-Type header added!");
					break;

				case 3:
					System.out.print("Insert Header Key: ");
					String key = reader.readLine().trim();
					System.out.print("Insert Header Value: ");
					String value = reader.readLine().trim();

					if (!key.isEmpty() && !value.isEmpty()) {
						headers.put(key, value);
						System.out.println("Custom header added!");
						break;
					}
					System.out.println("Key and Value cannot be empty.");
					break;

				case 0:
					return headers;

				default:
					System.out.println("[!] Invalid option. Choose between 0 and 3.");
					break;
			}
		}
	}

	public String verifyBody(String method) throws IOException {
		boolean needsBody = method.equals("POST") || method.equals("PUT") || method.equals("PATCH");

		if(!needsBody) {
			return "";
		}
		System.out.println("Enter the JSON body or press Enter if you do not need to write JSON. (Tap enter on a empty line to confirm):");
		String jsonBody = reader.readLine();

		System.out.println("\n[Registred payload]:\n"+ JsonFormatter.formatJson(jsonBody));
		System.out.println("----------------------------------------------");
		return jsonBody;

	}

}

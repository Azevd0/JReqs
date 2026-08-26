package menu;

import utils.JsonFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

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
			InetAddress.getByName(host);
			return true;

		} catch (UnknownHostException e) {
			return false;
		} catch (Exception e) {
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
				System.out.println("[!] Host not found. Please check for typos in the domain (e.g., 'locahost' instead of 'localhost').");
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

	public String verifyBody(String method) throws IOException {

		boolean needsBody = method.equals("POST") || method.equals("PUT") || method.equals("PATCH");

		if(!needsBody) {
			return "";
		}
		System.out.println("Insert the json body (Tap enter on a empty line to confirm):");
		String jsonBody = reader.readLine();

		System.out.println("\n[Registred payload]:\n"+ JsonFormatter.formatJson(jsonBody));
		System.out.println("----------------------------------------------");
		return jsonBody;

	}

}

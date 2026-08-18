package menu;

import utils.JsonFormatter;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMenu {

	private final Scanner scan;

	public ClientMenu() {
		this.scan = new Scanner(System.in);
	}

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

	public String readUrl() {
		while (true) {
			System.out.print("Insert the url (or x to exit): ");
			String url = scan.nextLine().trim();

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
			return url;
		}
	}

	public String selectMethod() {
		while (true) {
			System.out.println("What is the request type?\n1 - GET\n2 - POST\n3 - PUT\n4 - PATCH\n5 - DELETE\n6 - OPTIONS\nChoose from 1 to 6: \" ");
			String input = scan.nextLine().trim();

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
	
	public String verifyBody(String method) {

		boolean needsBody = method.equals("POST") || method.equals("PUT") || method.equals("PATCH");

		if(!needsBody) {
			return "";
		}
		System.out.println("Insert the json body (Tap enter on a empty line to confirm):");
		StringBuilder jsonInput = new StringBuilder();

		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line.trim().isEmpty()) {
				break;
			}
			jsonInput.append(line).append("\n");
		}

		String rawBody = jsonInput.toString();
		System.out.println("DEBUG raw=" + rawBody);
		try (var fw = new java.io.FileWriter("/tmp/debug_body.log", true)) {
			fw.write("=== ciclo ===\n" + rawBody + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n[Registred payload]:\n"+ JsonFormatter.formatJson(rawBody));
		System.out.println("----------------------------------------------");
		return rawBody;

	}

}

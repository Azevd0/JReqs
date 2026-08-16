package menu;

import java.net.URI;
import java.net.URISyntaxException;;
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

	public String readUrl() {
		while (true) {
			System.out.print("Insert the url (or x to exit): ");
			String url = scan.nextLine().trim();

			if (url.equalsIgnoreCase("x")) {
				return "x";
			}
			if (isValidUrl(url)) {
				return url;
			}
			System.out.println("[!] Invalid or malformed URL. Please check the address (e.g., http://localhost:8080/api).");
		}
	}
	public String selectMethod() {
		while (true) {
			System.out.println("What is the request type?\n1 - GET\n2 - POST\n3 - PUT\n4 - PATCH\n5 - DELETE\n6 - OPTIONS\nChoose from 1 to 6: \" ");			String input = scan.nextLine().trim();

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
		return scan.nextLine();
	}
	
}

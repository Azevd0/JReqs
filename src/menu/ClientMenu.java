package menu;

import java.util.Scanner;

public class ClientMenu {

	private final Scanner scan;
	
	public ClientMenu() {
		this.scan = new Scanner(System.in);
	}
	
	public String readUrl() {
		System.out.print("Insert the url (or x to exit):");
		return scan.nextLine().trim();
	}
	
	public String selectMethod() {
		String method = "";
		int option = 0;
		
		while(option < 1 || option > 6) {
			System.out.print("Qual é o tipo da requisição? \n"
					+ "1 - GET \n"
					+ "2 - POST \n"
					+ "3 - PUT \n"
					+ "4 - PATCH \n"
					+ "5 - DELETE\n"
					+ "6 - OPTIONS \n"
					+ "Escolha entre 1 e 6:");
			
			try {
				option = Integer.parseInt(scan.nextLine());
				
				switch(option) {
				case 1: method = "GET";
				break;
				case 2: method = "POST";
				break;
				case 3: method = "PUT";
				break;
				case 4: method = "PATCH";
				break;
				case 5: method = "DELETE";
				break;
				case 6: method = "OPTIONS";
				default:
					System.err.println("Inserção inválida, digite um número de 1 a 6.");
				}
			}catch(NumberFormatException ex) {
				System.err.println("Inserção inválida, digite apenas números");
			}
		}
		return method;
	}
	
	public String verifyBody(String method) {
		boolean needsBody = method.equals("POST") || method.equals("PUT") || method.equals("PATCH");
		if(!needsBody) {
			return "";
		}
		System.out.println("Digite o corpo em json:");
		return scan.nextLine();
	}
	
}

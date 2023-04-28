package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {

	public static void main(String[] args) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Luka\\eclipse-workspace\\hw04-0036533686\\src\\main\\java\\hr\\fer\\oprpp1\\hw04\\db\\database.txt"), StandardCharsets.UTF_8);
		try {
			boolean showing = false;
			StudentDatabase db = new StudentDatabase(lines);
			Scanner sc = new Scanner(System.in);
			String komanda = sc.nextLine();
			String[] poljeShowElemenata = null;
			sc.close();
			if(komanda.substring(0, 4).equals("exit")) {
				System.out.println("Goodbye!");
				System.exit(0);
			}else if(komanda.substring(0, 5).equals("query")) {
				komanda = komanda.substring(5);
				komanda = komanda.trim();
				if(komanda.contains("showing")) {
					if(komanda.trim().endsWith("jmbag") || komanda.trim().endsWith("firstName") || komanda.trim().endsWith("lastName") || komanda.trim().endsWith("grade")) {
						poljeShowElemenata = komanda.substring(komanda.indexOf("showing") + 8).split(", ");
						showing = true;
						komanda = komanda.substring(0, komanda.indexOf("showing"));
						komanda = komanda.trim();
					}else {
						throw new Exception("Krivo, zadana showing komanda bez argumenata!");
					}
				}
			}else {
				throw new Exception("Kriva komanda!");
			}
			QueryParser qp = new QueryParser(komanda);
			List<ConditionalExpression> listaUpita = qp.getQuery();
			List<StudentRecord> l = new ArrayList<>();
			if(qp.isDirectQuery()) {
				l.add(db.forJMBAG(qp.getQueriedJMBAG()));
			}else {
				IFilter filtar = new QueryFilter(listaUpita);
				l = db.filter(filtar);
			}
			//ISPIS
			int najDIme, najDPrez, velicinaListe = l.size();
			if(velicinaListe != 0) {
				najDIme = najDPrez = 0;
				for(StudentRecord re : l) {
					if(re.getFirstName().length() > najDIme) {
						najDIme = re.getFirstName().length();
					}
					if(re.getLastName().length() > najDPrez) {
						najDPrez = re.getLastName().length();
					}
				}
				if(!showing) {
					String pocetnaCrta = "+============+=";
					for(int i = 0; i <= najDPrez; i++) {
						pocetnaCrta += "=";
					}
					pocetnaCrta += "+=";
					for(int i = 0; i <= najDIme; i++) {
						pocetnaCrta += "=";
					}
					pocetnaCrta += "+===+";
					System.out.println(pocetnaCrta);
					String redak;
					for(StudentRecord re : l) {
						redak = "| " + re.getJmbag() + " | " + re.getLastName();
						for(int j = 0; j < najDPrez - re.getLastName().length(); j++) {
							redak += " ";
						}
						redak += " | " + re.getFirstName();
						for(int j = 0; j < najDIme - re.getFirstName().length(); j++) {
							redak += " ";
						}
						redak += " | " + re.getGrade() + " |";
						System.out.println(redak);
					}
					System.out.println(pocetnaCrta);
				}else {//ispis u slucaju rijeci showing
					String pocetnaCrta = "+=";
					int brojElem = poljeShowElemenata.length;
					for(int j = 0; j < brojElem; j++) {
						String elementPrik = poljeShowElemenata[j];
						if(elementPrik.equals("grade")) {
							pocetnaCrta += "==+";
							if(j < brojElem - 1) {
								pocetnaCrta += "=";
							}
						}else if(elementPrik.equals("firstName")) {
							for(int i = 0; i < najDIme; i++) {
								pocetnaCrta += "=";
							}
							pocetnaCrta += "=+";
							if(j < brojElem - 1) {
								pocetnaCrta += "=";
							}
						}else if(elementPrik.equals("lastName")) {
							for(int i = 0; i < najDPrez; i++) {
								pocetnaCrta += "=";
							}
							pocetnaCrta += "=+";
							if(j < brojElem - 1) {
								pocetnaCrta += "=";
							}
						}else if(elementPrik.equals("jmbag")) {
							for(int i = 0; i < 10; i++) {
								pocetnaCrta += "=";
							}
							pocetnaCrta += "=+";
							if(j < brojElem - 1) {
								pocetnaCrta += "=";
							}
						}
					}
					System.out.println(pocetnaCrta);
					String redak;
					for(StudentRecord re : l) {
						redak = "| ";
						for(int j = 0; j < brojElem; j++) {
							if(poljeShowElemenata[j].equals("jmbag")) {
								redak += re.getJmbag() + " |";
								if(j < brojElem - 1) {
									redak += " ";
								}
							}else if(poljeShowElemenata[j].equals("grade")) {
								redak += re.getGrade() + " |";
								if(j < brojElem - 1) {
									redak += " ";
								}
							}else if(poljeShowElemenata[j].equals("firstName")) {
								redak += re.getFirstName();
								for(int d = 0; d < najDIme - re.getFirstName().length(); d++) {
									redak += " ";
								}
								redak += " |";
								if(j < brojElem - 1) {
									redak += " ";
								}
							}else if(poljeShowElemenata[j].equals("lastName")) {
								redak += re.getLastName();
								for(int d = 0; d < najDPrez - re.getLastName().length(); d++) {
									redak += " ";
								}
								redak += " |";
								if(j < brojElem - 1) {
									redak += " ";
								}
							}
						}
						System.out.println(redak);
					}
					System.out.println(pocetnaCrta);
				}
			}
			System.out.println("Records selected: " + velicinaListe);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
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
			if(komanda.startsWith("exit")) {
				System.out.println("Goodbye!");
				System.exit(0);
			}else if(komanda.startsWith("query")) {
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
                StringBuilder pocetnaCrta;
                if(!showing) {
                    pocetnaCrta = new StringBuilder("+============+=");
                    pocetnaCrta.append("=".repeat(Math.max(0, najDPrez + 1)));
					pocetnaCrta.append("+=");
                    pocetnaCrta.append("=".repeat(Math.max(0, najDIme + 1)));
					pocetnaCrta.append("+===+");
					System.out.println(pocetnaCrta);
					StringBuilder redak;
					for(StudentRecord re : l) {
						redak = new StringBuilder("| " + re.getJmbag() + " | " + re.getLastName());
                        redak.append(" ".repeat(Math.max(0, najDPrez - re.getLastName().length())));
						redak.append(" | ").append(re.getFirstName());
                        redak.append(" ".repeat(Math.max(0, najDIme - re.getFirstName().length())));
						redak.append(" | ").append(re.getGrade()).append(" |");
						System.out.println(redak);
					}
                }else {//ispis u slucaju rijeci showing
                    pocetnaCrta = new StringBuilder("+=");
					int brojElem = poljeShowElemenata.length;
					for(int j = 0; j < brojElem; j++) {
						String elementPrik = poljeShowElemenata[j];
                        switch (elementPrik) {
                            case "grade" -> {
                                pocetnaCrta.append("==+");
                                if (j < brojElem - 1) {
                                    pocetnaCrta.append("=");
                                }
                            }
                            case "firstName" -> {
                                pocetnaCrta.append("=".repeat(najDIme));
                                pocetnaCrta.append("=+");
                                if (j < brojElem - 1) {
                                    pocetnaCrta.append("=");
                                }
                            }
                            case "lastName" -> {
                                pocetnaCrta.append("=".repeat(najDPrez));
                                pocetnaCrta.append("=+");
                                if (j < brojElem - 1) {
                                    pocetnaCrta.append("=");
                                }
                            }
                            case "jmbag" -> {
                                pocetnaCrta.append("=".repeat(10));
                                pocetnaCrta.append("=+");
                                if (j < brojElem - 1) {
                                    pocetnaCrta.append("=");
                                }
                            }
                        }
					}
					System.out.println(pocetnaCrta);
					StringBuilder redak;
					for(StudentRecord re : l) {
						redak = new StringBuilder("| ");
						for(int j = 0; j < brojElem; j++) {
                            switch (poljeShowElemenata[j]) {
                                case "jmbag" -> {
                                    redak.append(re.getJmbag()).append(" |");
                                    if (j < brojElem - 1) {
                                        redak.append(" ");
                                    }
                                }
                                case "grade" -> {
                                    redak.append(re.getGrade()).append(" |");
                                    if (j < brojElem - 1) {
                                        redak.append(" ");
                                    }
                                }
                                case "firstName" -> {
                                    redak.append(re.getFirstName());
                                    redak.append(" ".repeat(Math.max(0, najDIme - re.getFirstName().length())));
                                    redak.append(" |");
                                    if (j < brojElem - 1) {
                                        redak.append(" ");
                                    }
                                }
                                case "lastName" -> {
                                    redak.append(re.getLastName());
                                    redak.append(" ".repeat(Math.max(0, najDPrez - re.getLastName().length())));
                                    redak.append(" |");
                                    if (j < brojElem - 1) {
                                        redak.append(" ");
                                    }
                                }
                            }
						}
						System.out.println(redak);
					}
                }
                System.out.println(pocetnaCrta);
            }
			System.out.println("Records selected: " + velicinaListe);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
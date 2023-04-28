package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		String putanja = "C:\\Users\\Luka\\output.txt";
		Path path = Paths.get(putanja);
		try {
			List<String> listaLinija = Files.readAllLines(path);
			String st = "";
			for(String s : listaLinija) {
				st += s + "\n";
			}
			System.out.println(st);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
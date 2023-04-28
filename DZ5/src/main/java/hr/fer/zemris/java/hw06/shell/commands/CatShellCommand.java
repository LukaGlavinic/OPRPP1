package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CatShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		String setZnakova, putDoDatoteke;
		putDoDatoteke = setZnakova = "";
		char[] poljeZnakova = arguments.toCharArray();
		int i = 1;
		if(poljeZnakova[0] == '"') {
			while(!(poljeZnakova[i] == '"' && poljeZnakova[i - 1] != '\\')) {
				if(poljeZnakova[i] == '\\' && i + 1 < poljeZnakova.length - 1 && (poljeZnakova[i + 1] == '\\' || poljeZnakova[i + 1] == '"')) {
					putDoDatoteke += Character.toString(poljeZnakova[i + 1]);
					i++;
				}else {
					putDoDatoteke += Character.toString(poljeZnakova[i]);
				}
				i++;
			}
			i += 2;
			if(i < poljeZnakova.length) {
				while(i < poljeZnakova.length) {
					setZnakova += Character.toString(poljeZnakova[i]);
				}
			}
		}else {
			String[] polje = arguments.split("\\s+");
			putDoDatoteke = polje[0];
			if(polje.length > 1) {
				setZnakova = polje[1];
			}
		}
		Path put;
		Charset set = Charset.defaultCharset();
		try {
			put = Paths.get(putDoDatoteke);
			if(!put.isAbsolute()) {
				put = put.toAbsolutePath();
			}
		}catch(Exception e) {
			env.writeln("Nepravilno zadan put do datoteke za naredbu cat");
			return ShellStatus.CONTINUE;
		}
		if(!setZnakova.equals("")) {
			try {
				if(Charset.isSupported(setZnakova)) {
					set = Charset.forName(setZnakova);
				}
			}catch(Exception e) {
				env.writeln("Greška kod imena seta znakova");
				return ShellStatus.CONTINUE;
			}
		}
		try {
			List<String> l = Files.readAllLines(put, set);
			for(String s : l) {
				env.writeln(s);
			}
		}catch(Exception e) {
			env.writeln("Greška kod èitanje datoteke u naredbi cat");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Uzima 1 ili 2 argumenta");
		lista.add("Prvi argument je put do neke datoteke i obavezan je");
		lista.add("Drugi argument je ime seta znakova koji se koristi za prevoðenje okteta u znakove");
		lista.add("Ako se drugi argument ne navede uzima se pretpostavljeni set znakova platforme");
		lista.add("Komanda otvara danu datoteku i ispisuje njen sadržaj na konzolu");
		return Collections.unmodifiableList(lista);
	}
}
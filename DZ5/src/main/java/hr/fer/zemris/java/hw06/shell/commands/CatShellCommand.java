package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CatShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		StringBuilder setZnakova;
        StringBuilder putDoDatoteke;
        putDoDatoteke = new StringBuilder(setZnakova = new StringBuilder());
		char[] poljeZnakova = arguments.toCharArray();
		int i = 1;
		if(poljeZnakova[0] == '"') {
			while(!(poljeZnakova[i] == '"' && poljeZnakova[i - 1] != '\\')) {
				if(poljeZnakova[i] == '\\' && i + 1 < poljeZnakova.length - 1 && (poljeZnakova[i + 1] == '\\' || poljeZnakova[i + 1] == '"')) {
					putDoDatoteke.append(poljeZnakova[i + 1]);
					i++;
				}else {
					putDoDatoteke.append(poljeZnakova[i]);
				}
				i++;
			}
			i += 2;
			if(i < poljeZnakova.length) {
				setZnakova.append(poljeZnakova[i]);
			}
		}else {
			String[] polje = arguments.split("\\s+");
			putDoDatoteke = new StringBuilder(polje[0]);
			if(polje.length > 1) {
				setZnakova = new StringBuilder(polje[1]);
			}
		}
		Path put;
		Charset set = Charset.defaultCharset();
		try {
			put = Paths.get(putDoDatoteke.toString());
			if(!put.isAbsolute()) {
				put = put.toAbsolutePath();
			}
		}catch(Exception e) {
			env.writeln("Nepravilno zadan put do datoteke za naredbu cat");
			return ShellStatus.CONTINUE;
		}
		if(!setZnakova.isEmpty()) {
			try {
				if(Charset.isSupported(setZnakova.toString())) {
					set = Charset.forName(setZnakova.toString());
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
        return List.of("Uzima 1 ili 2 argumenta", "Prvi argument je put do neke datoteke i obavezan je", "Drugi argument je ime seta znakova koji se koristi za prevoðenje okteta u znakove", "Ako se drugi argument ne navede uzima se pretpostavljeni set znakova platforme", "Komanda otvara danu datoteku i ispisuje njen sadržaj na konzolu");
	}
}
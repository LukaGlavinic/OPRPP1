package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class MkdirShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		String  putDoDatoteke = "";
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
		}else {
			String[] polje = arguments.split("\\s+");
			if(polje.length > 1) {
				env.writeln("Nepravilan broj argumenata za naredbu tree");
				return ShellStatus.CONTINUE;
			}
			putDoDatoteke = polje[0];
		}
		File file;
		try {
			file = new File(putDoDatoteke);
		}catch(Exception e) {
			env.writeln("Nepravilno zadan put do datoteke za naredbu mkdir");
			return ShellStatus.CONTINUE;
		}
		boolean bool = file.mkdirs();
	      if(!bool) {
	    	env.writeln("Neuspješno kreiranje direktorija");
			return ShellStatus.CONTINUE;
	      }
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Uzima 1 argument koji je direktorij");
		lista.add("Izraðuje ispravnu strukturu direktorija");
		return Collections.unmodifiableList(lista);
	}
}
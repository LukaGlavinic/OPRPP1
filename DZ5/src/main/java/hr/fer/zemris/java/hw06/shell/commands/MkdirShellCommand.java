package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.File;
import java.util.List;

public class MkdirShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		StringBuilder putDoDatoteke = new StringBuilder();
		char[] poljeZnakova = arguments.toCharArray();
		int i = 1;
		if(poljeZnakova[0] == '"') {
			getPutDoDatoteke(putDoDatoteke, poljeZnakova, i);
		}else {
			String[] polje = arguments.split("\\s+");
			if(polje.length > 1) {
				env.writeln("Nepravilan broj argumenata za naredbu tree");
				return ShellStatus.CONTINUE;
			}
			putDoDatoteke = new StringBuilder(polje[0]);
		}
		File file;
		try {
			file = new File(putDoDatoteke.toString());
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

	static void getPutDoDatoteke(StringBuilder putDoDatoteke, char[] poljeZnakova, int i) {
		TreeShellCommand.getPutDoDatoteke(putDoDatoteke, poljeZnakova, i);
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
        return List.of("Uzima 1 argument koji je direktorij", "Izraðuje ispravnu strukturu direktorija");
	}
}
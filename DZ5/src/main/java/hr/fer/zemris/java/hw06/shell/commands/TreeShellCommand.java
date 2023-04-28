package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class TreeShellCommand implements ShellCommand{
	
	public static void ispisStabla(Environment env, File direk, int dubina) {
		String s = "";
		for(int i = 0; i < dubina; i++) {
			s += "  ";
		}
		s += direk.getName();
		env.writeln(s);
		if(direk.listFiles() != null && direk.listFiles().length > 0) {
			for(File djete : direk.listFiles()) {
				ispisStabla(env, djete, dubina + 1);
			}
		}
	}

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
			env.writeln("Nepravilno zadan put do datoteke za naredbu tree");
			return ShellStatus.CONTINUE;
		}
		if(!file.exists()) {
			env.writeln("Ne postoji zadani direktorij za naredbu tree");
		}else {
			ispisStabla(env, file, 0);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Uzima 1 argument koji je ime direktorija");
		lista.add("Ispisuje na konzolu stablo tog direktorija");
		lista.add("Svaka razina stabla pomièe ispis za 2 znaka udesno");
		return Collections.unmodifiableList(lista);
	}
}
package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class TreeShellCommand implements ShellCommand{
	
	public static void ispisStabla(Environment env, File direk, int dubina) {
        String s = "  ".repeat(Math.max(0, dubina)) +
                direk.getName();
		env.writeln(s);
		if(direk.listFiles() != null) {
            Objects.requireNonNull(direk.listFiles());
            for (File djete : Objects.requireNonNull(direk.listFiles())) {
                ispisStabla(env, djete, dubina + 1);
            }
        }
	}

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

	static void getPutDoDatoteke(StringBuilder putDoDatoteke, char[] poljeZnakova, int i) {
		while(!(poljeZnakova[i] == '"' && poljeZnakova[i - 1] != '\\')) {
			if(poljeZnakova[i] == '\\' && i + 1 < poljeZnakova.length - 1 && (poljeZnakova[i + 1] == '\\' || poljeZnakova[i + 1] == '"')) {
				putDoDatoteke.append(poljeZnakova[i + 1]);
				i++;
			}else {
				putDoDatoteke.append(poljeZnakova[i]);
			}
			i++;
		}
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
        return List.of("Uzima 1 argument koji je ime direktorija", "Ispisuje na konzolu stablo tog direktorija", "Svaka razina stabla pomièe ispis za 2 znaka udesno");
	}
}
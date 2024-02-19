package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.List;
import java.util.SortedMap;

public class HelpShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		SortedMap<String, ShellCommand> mapa = env.commands();
		if(arguments.isEmpty()) {
			env.writeln("Supported commands are:");
			for(String s : mapa.keySet()) {
				env.writeln(s);
			}
		}else {
			String[] polje = arguments.split("\\s+");
			if(polje.length > 1) {
				env.writeln("Nepravilan broj argumenata za naredbu help");
			}else {
				String komanda = polje[0];
				ShellCommand k = mapa.get(komanda);
				if(k == null) {
					env.writeln("Ne postoji komanda " + komanda);
				}else {
					env.writeln(k.getCommandName());
					List<String> lista = k.getCommandDescription();
					for(String s : lista) {
						env.writeln(s);
					}
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
        return List.of("Uzima 0 ili 1 argument koji je ime naredbe", "Ako nema argumenata ispisuje na konzolu sve podržane naredbe", "Ako ima argument onda ispisuje ime i opis dane naredbe ili grešku");
	}
}
package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.List;

public class SymbolShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argume = arguments.trim().split("\\s+");
		if(argume.length < 1 || argume.length > 2) {
			env.writeln("Nepravilan broj argumenata za naredbu symbol");
		}else if(argume[0].equals("PROMPT")) {
			if(argume.length < 2) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol().toString() + "'");
			}else {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol().toString() + "' to '" + "'" + argume[1] + "'");
				env.setPromptSymbol(argume[1].charAt(0));
			}
		}else if(argume[0].equals("MORELINES")) {
			if(argume.length < 2) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol().toString() + "'");
			}else {
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol().toString() + "' to '" + "'" + argume[1] + "'");
				env.setMorelinesSymbol(argume[1].charAt(0));
			}
		}else if(argume[0].equals("MULTILINE")) {
			if(argume.length < 2) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol().toString() + "'");
			}else {
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol().toString() + "' to '" + "'" + argume[1] + "'");
				env.setMultilineSymbol(argume[1].charAt(0));
			}
		}else {
			env.writeln("Nepravilni argumenti za naredbu symbol");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
        return List.of("Ima ili 1 ili 2 argumenta", "Ispisuje trenutno korišteni znak ili mijenja trenutni znak u predani znak", "Ako je promjenjen znak ispisuje stari znak i novi znak");
	}
}
package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.trim().length() > 0) {
			env.writeln("Nepravilni argumenti za naredbu charsets");
			return ShellStatus.CONTINUE;
		}
		Map<String, Charset> charsets = Charset.availableCharsets();
		Iterator<Charset> iterator = charsets.values().iterator();
		while (iterator.hasNext()) {
			Charset all = (Charset)iterator.next();
			env.writeln(all.displayName());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Nema argumenata");
		lista.add("Lista imena svih podržanih setova znakova za tvoju Java platformu");
		lista.add("Jedno ime seta se ispisuje po liniji");
		return Collections.unmodifiableList(lista);
	}
}
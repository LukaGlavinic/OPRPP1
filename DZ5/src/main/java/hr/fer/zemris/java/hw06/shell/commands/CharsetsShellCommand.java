package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class CharsetsShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.trim().isEmpty()) {
			env.writeln("Nepravilni argumenti za naredbu charsets");
			return ShellStatus.CONTINUE;
		}
		Map<String, Charset> charsets = Charset.availableCharsets();
        for (Charset all : charsets.values()) {
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
        return List.of("Nema argumenata", "Lista imena svih podržanih setova znakova za tvoju Java platformu", "Jedno ime seta se ispisuje po liniji");
	}
}
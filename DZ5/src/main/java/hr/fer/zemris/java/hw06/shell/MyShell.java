package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.*;

import java.io.*;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyShell implements Environment{
	
	private Character PROMPT;
	private Character MORELINES;
	private Character MULTILINE;
	private ShellStatus status;
	private final SortedMap<String, ShellCommand> commands;
    private BufferedWriter writer;
	
	public MyShell() {
		PROMPT = '>';
		MORELINES = '\\';
		MULTILINE = '|';
		status = ShellStatus.CONTINUE;
		commands = new TreeMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("help", new HelpShellCommand());
	}

	public static void main(String[] args) {
		
		MyShell ljuska = new MyShell();
		String linija;
		String[] poljeLinije;
		String imeKomande = null;
        StringBuilder argumenti = new StringBuilder();
        ShellCommand komanda;
		boolean vise = false;
		int i;
		try {
			ljuska.writeln("Welcome to MyShell v 1.0");
			do {
				if(!vise) {
					ljuska.write(ljuska.getPromptSymbol().toString() + " ");
					linija = ljuska.readLine();
					if(linija.isBlank()) {
						continue;
					}
					poljeLinije = linija.split("\\s+");
					imeKomande = poljeLinije[0];
					i = 1;
				}else {
					ljuska.write(ljuska.getMultilineSymbol().toString() + " ");
					linija = ljuska.readLine();
					if(linija.isBlank()) {
						continue;
					}
					poljeLinije = linija.split("\\s+");
					i = 0;
				}
				if(poljeLinije[poljeLinije.length - 1].equals(ljuska.getMorelinesSymbol().toString())) {
					if(!vise) {
						vise = true;
					}else {
						argumenti.append(" ");
					}
					for(; i < poljeLinije.length - 1; i++) {
						argumenti.append(poljeLinije[i]);
						if(i < poljeLinije.length - 2) {
							argumenti.append(" ");
						}
					}
				}else {
					if(vise) {
						argumenti.append(" ");
					}
					vise = false;
					for(; i < poljeLinije.length; i++) {
						argumenti.append(poljeLinije[i]);
						if(i < poljeLinije.length - 1) {
							argumenti.append(" ");
						}
					}
					if(!ljuska.commands.containsKey(imeKomande)) {
						ljuska.writeln("Ne postoji ta komanda!");
						continue;
					}
					komanda = ljuska.commands.get(imeKomande);
					if(komanda == null) {
						ljuska.writeln("Ne postoji ta komanda");
						continue;
					}
					ljuska.status = komanda.executeCommand(ljuska, argumenti.toString());
					argumenti = new StringBuilder();
				}
			}while(ljuska.status != ShellStatus.TERMINATE);
		}catch(ShellIOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String readLine() {
		try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
		} catch (IOException e) {
			throw new ShellIOException("Pogreška pri èitanju");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(System.out));
			writer.write(text, 0, text.length());
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException("Pogreška pri pisanju");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(System.out));
			writer.write(text, 0, text.length());
			writer.write("\n");
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException("Pogreška pri pisanju");
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
        return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINE;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINE = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPT;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPT = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINES;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINES = symbol;
	}
}
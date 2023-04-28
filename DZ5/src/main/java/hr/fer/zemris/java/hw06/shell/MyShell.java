package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

public class MyShell implements Environment{
	
	private Character PROMPT;
	private Character MORELINES;
	private Character MULTILINE;
	private ShellStatus status;
	private SortedMap<String, ShellCommand> commands;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public MyShell() {
		this.PROMPT = '>';
		this.MORELINES = '\\';
		this.MULTILINE = '|';
		this.status = ShellStatus.CONTINUE;
		this.commands = new TreeMap<>();
		this.commands.put("exit", new ExitShellCommand());
		this.commands.put("symbol", new SymbolShellCommand());
		this.commands.put("ls", new LsShellCommand());
		this.commands.put("charsets", new CharsetsShellCommand());
		this.commands.put("copy", new CopyShellCommand());
		this.commands.put("mkdir", new MkdirShellCommand());
		this.commands.put("tree", new TreeShellCommand());
		this.commands.put("hexdump", new HexdumpShellCommand());
		this.commands.put("cat", new CatShellCommand());
		this.commands.put("help", new HelpShellCommand());
	}

	public static void main(String args[]) {
		
		MyShell ljuska = new MyShell();
		String linija;
		String[] poljeLinije;
		String imeKomande = null, argumenti = "";
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
						argumenti += " ";
					}
					for(; i < poljeLinije.length - 1; i++) {
						argumenti += poljeLinije[i];
						if(i < poljeLinije.length - 2) {
							argumenti += " ";
						}
					}
				}else {
					if(vise) {
						argumenti += " ";
					}
					vise = false;
					for(; i < poljeLinije.length; i++) {
						argumenti += poljeLinije[i];
						if(i < poljeLinije.length - 1) {
							argumenti += " ";
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
					ljuska.status = komanda.executeCommand(ljuska, argumenti);
					argumenti = "";
				}
			}while(ljuska.status != ShellStatus.TERMINATE);
		}catch(ShellIOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String readLine() {
		try {
			this.reader = new BufferedReader(new InputStreamReader(System.in));
			String s = this.reader.readLine();
			return s;
		} catch (IOException e) {
			throw new ShellIOException("Pogreška pri èitanju");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
			this.writer.write(text, 0, text.length());
			this.writer.flush();
		} catch (IOException e) {
			throw new ShellIOException("Pogreška pri pisanju");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
			this.writer.write(text, 0, text.length());
			this.writer.write("\n");
			this.writer.flush();
		} catch (IOException e) {
			throw new ShellIOException("Pogreška pri pisanju");
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		SortedMap<String, ShellCommand> unmSM = Collections.unmodifiableSortedMap(this.commands);
		return unmSM;
	}

	@Override
	public Character getMultilineSymbol() {
		return this.MULTILINE;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.MULTILINE = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.PROMPT;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.PROMPT = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.MORELINES;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.MORELINES = symbol;
	}
}
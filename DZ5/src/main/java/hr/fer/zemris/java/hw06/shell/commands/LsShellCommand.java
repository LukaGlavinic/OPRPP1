package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class LsShellCommand implements ShellCommand{
	
	public static void ispisListe(Environment env, Path direk) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File[] djeca = direk.toFile().listFiles();
		if(djeca.length > 0) {
			for(File djete : djeca) {
				long velicina;
				boolean daLiJeDirektorij, citljivo, pisljivo, izvrsljivo;
				String ime;
				try {
					velicina = Files.size(djete.toPath());
					daLiJeDirektorij = Files.isDirectory(djete.toPath());
					citljivo = Files.isReadable(djete.toPath());
					pisljivo = Files.isWritable(djete.toPath());
					izvrsljivo = Files.isExecutable(djete.toPath());
					ime = djete.getName();
				} catch (IOException e1) {
					env.writeln("Nepravilno èitanje velièine datoteke u naredbi ls");
					return;
				}
				BasicFileAttributeView faView = Files.getFileAttributeView(direk, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = null;
				String formattedDateTime;
				try {
					attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				} catch (IOException e) {
					env.writeln("Nepravilno èitanje atributa ls");
					return;
				}
				String linija = "";
				if(daLiJeDirektorij) {
					linija += "d";
				}else {
					linija += "-";
				}
				if(citljivo) {
					linija += "r";
				}else {
					linija += "-";
				}
				if(pisljivo) {
					linija += "w";
				}else {
					linija += "-";
				}
				if(izvrsljivo) {
					linija += "x ";
				}else {
					linija += "- ";
				}
				int duljinaBroja = Long.toString(velicina).length();
				for(int i = 0; i < 10 - duljinaBroja; i++) {
					linija += " ";
				}
				linija += Long.toString(velicina) + " ";
				linija += formattedDateTime + " ";
				linija += ime;
				env.writeln(linija);
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
				env.writeln("Nepravilan broj argumenata za naredbu ls");
				return ShellStatus.CONTINUE;
			}
			putDoDatoteke = polje[0];
		}
		Path put;
		try {
			put = Paths.get(putDoDatoteke);
		}catch(Exception e) {
			env.writeln("Nepravilno zadan put do datoteke za naredbu ls");
			return ShellStatus.CONTINUE;
		}
		if(!Files.exists(put)) {
			env.writeln("Ne postoji zadani direktorij za naredbu ls");
		}else {
			ispisListe(env, put);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Uzima 1 argument koji je direktorij");
		lista.add("Ispisuje listu tog direktorija");
		lista.add("Ispis ima format od 4 stupca");
		lista.add("Prvi stupac govori da li je direktorij direktorij, èitljiv, pisljiv, ili izvršujuæi");
		lista.add("Drugi stupac govori velièinu objekta u oktetima i poravnat je udesno i zauziva 10 znakova");
		lista.add("Treæi stupac govori datum kreiranja objekta");
		lista.add("Èetvrti stupac govori ime objekta");
		return Collections.unmodifiableList(lista);
	}
}
package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

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
import java.util.List;

public class LsShellCommand implements ShellCommand{
	
	public static void ispisListe(Environment env, Path direk) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File[] djeca = direk.toFile().listFiles();
        assert djeca != null;
        for (File djete : djeca) {
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
            BasicFileAttributes attributes;
            String formattedDateTime;
            try {
                attributes = faView.readAttributes();
                FileTime fileTime = attributes.creationTime();
                formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
            } catch (IOException e) {
                env.writeln("Nepravilno èitanje atributa ls");
                return;
            }
            StringBuilder linija = new StringBuilder();
            if (daLiJeDirektorij) {
                linija.append("d");
            } else {
                linija.append("-");
            }
            if (citljivo) {
                linija.append("r");
            } else {
                linija.append("-");
            }
            if (pisljivo) {
                linija.append("w");
            } else {
                linija.append("-");
            }
            if (izvrsljivo) {
                linija.append("x ");
            } else {
                linija.append("- ");
            }
            int duljinaBroja = Long.toString(velicina).length();
            linija.append(" ".repeat(Math.max(0, 10 - duljinaBroja)));
            linija.append(velicina).append(" ");
            linija.append(formattedDateTime).append(" ");
            linija.append(ime);
            env.writeln(linija.toString());
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
				env.writeln("Nepravilan broj argumenata za naredbu ls");
				return ShellStatus.CONTINUE;
			}
			putDoDatoteke = new StringBuilder(polje[0]);
		}
		Path put;
		try {
			put = Paths.get(putDoDatoteke.toString());
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

	static void getPutDoDatoteke(StringBuilder putDoDatoteke, char[] poljeZnakova, int i) {
		MkdirShellCommand.getPutDoDatoteke(putDoDatoteke, poljeZnakova, i);
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
        return List.of("Uzima 1 argument koji je direktorij", "Ispisuje listu tog direktorija", "Ispis ima format od 4 stupca", "Prvi stupac govori da li je direktorij direktorij, èitljiv, pisljiv, ili izvršujuæi", "Drugi stupac govori velièinu objekta u oktetima i poravnat je udesno i zauziva 10 znakova", "Treæi stupac govori datum kreiranja objekta", "Èetvrti stupac govori ime objekta");
	}
}
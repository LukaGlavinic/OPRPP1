package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CopyShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		String[] dvaDir = arguments.split("\\s+");
		String putDoDatoteke1 = "", putDoDatoteke2 = "";
		String[] putoviDoDat = {putDoDatoteke1, putDoDatoteke2};
		if(dvaDir.length < 2 || dvaDir.length > 2) {
			env.writeln("Nepravilan broj argumenata za naredbu copy");
			return ShellStatus.CONTINUE;
		}
		for(int j = 0; j < dvaDir.length; j++) {
			char[] poljeZnakova = dvaDir[j].toCharArray();
			if(poljeZnakova[0] == '"') {
				int i = 1;
				while(!(poljeZnakova[i] == '"' && poljeZnakova[i - 1] != '\\')) {
					if(poljeZnakova[i] == '\\' && i + 1 < poljeZnakova.length - 1 && (poljeZnakova[i + 1] == '\\' || poljeZnakova[i + 1] == '"')) {
						putoviDoDat[j] += Character.toString(poljeZnakova[i + 1]);
						i++;
					}else {
						putoviDoDat[j] += Character.toString(poljeZnakova[i]);
					}
					i++;
				}
			}else {
				putoviDoDat[j] = dvaDir[j];
			}
		}
		Path in, out;
		try {
			in = Paths.get(putoviDoDat[0]);
			out = Paths.get(putoviDoDat[1]);
		}catch(Exception e) {
			env.writeln("Nepravilno zadan put do datoteke za naredbu copy");
			return ShellStatus.CONTINUE;
		}
		if(!in.toFile().exists()) {
			env.writeln("Ne postoji zadani direktorij za naredbu copy");
		}else if(!in.toFile().isFile() || in.toFile().isDirectory()) {
			env.writeln("Ulazna datoteka nije datoteka za naredbu copy");
		}else {
			List<String> l;
			try {
				l = Files.readAllLines(in);
			} catch (IOException e) {
				env.writeln("Ne mogu èitati linije iz ulazne datoteke za naredbu copy");
				return ShellStatus.CONTINUE;
			}
			if(out.toFile().isDirectory()) {
				String p = out.toFile().getAbsolutePath();
				p += "\\" + in.toFile().getName();
				out = (new File(p)).toPath();
			}
			if(out.toFile().exists()) {
				env.writeln("Da li želiš prepisati ovu datoteku?");
				env.write(env.getPromptSymbol() + " ");
				String odgovor = env.readLine();
				if(!odgovor.equals("yes")) {
					env.writeln("Nije dozvoljeno prepisivanje");
					return ShellStatus.CONTINUE;
				}
			}else {
				try {
					out.toFile().createNewFile();
				} catch (IOException e) {
					env.writeln("Nije moguæe kreiranje datoteke za naredbu copy");
					return ShellStatus.CONTINUE;
				}
			}
			FileWriter pisac;
			try {
				pisac = new FileWriter(out.toFile(), false);
				for(int k = 0; k < l.size(); k++) {
					try {
						pisac.write(l.get(k));
						if(k < l.size() - 1) {
							pisac.write("\n");
						}
					} catch (IOException e) {
						env.writeln("Greška kod pisanja u izlaznu datoteku za naredbu copy");
						pisac.close();
						return ShellStatus.CONTINUE;
					}
				}
				pisac.close();
			} catch (IOException e) {
				env.writeln("Ne mogu pisati linije u izlaznu datoteku za naredbu copy");
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Uzima 2 argumenta");
		lista.add("Prvi argument je ime izvorne datoteke");
		lista.add("Drugi argument je ime odredišne datoteke");
		lista.add("Ako odredišna datoteka postoji treba pitati korisnika da li se smije pisati preko nje");
		lista.add("Ako je drugi argument direktorij pretpostavlja se da korisnik želi prekopirati izvornu datoteku u taj direktorij sa istim imenom");
		return Collections.unmodifiableList(lista);
	}
}
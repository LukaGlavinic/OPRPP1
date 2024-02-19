package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class HexdumpShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		StringBuilder putDoDatoteke = new StringBuilder();
		char[] poljeZnakova = arguments.toCharArray();
		int i = 1;
		if(poljeZnakova[0] == '"') {
			LsShellCommand.getPutDoDatoteke(putDoDatoteke, poljeZnakova, i);
		}else {
			String[] polje = arguments.split("\\s+");
			if(polje.length > 1) {
				env.writeln("Nepravilan broj argumenata za naredbu hexdump");
				return ShellStatus.CONTINUE;
			}
			putDoDatoteke = new StringBuilder(polje[0]);
		}
		File file;
		try {
			file = new File(putDoDatoteke.toString());
		}catch(Exception e) {
			env.writeln("Nepravilno zadan put do datoteke za naredbu hexdump");
			return ShellStatus.CONTINUE;
		}
		if(!file.exists()) {
			env.writeln("Ne postoji zadani direktorij za naredbu hexdump");
		}else if(!file.isFile() || file.isDirectory()) {
			env.writeln("Zadana datoteka nije datoteka za naredbu hexdump");
		}else {
			try (InputStream fis = Files.newInputStream(file.toPath())) {
	            // drži jedan oktet podatka
	            int i1;
	            // broji koliko je linija ispisano
	            int count = 0, brojProcZnakova = 0;
	            StringBuilder ispis = new StringBuilder();
	            StringBuilder hex1 = new StringBuilder();
	            StringBuilder hex2 = new StringBuilder();
	            StringBuilder input = new StringBuilder();
	            while ((i1 = fis.read()) != -1) {
		            brojProcZnakova++;
		            if(brojProcZnakova == 1) {
		            	input.append(" ");
		            }
		            if(brojProcZnakova < 9) {
		            	if(brojProcZnakova == 8) {
		            		hex1.append(String.format("%02X", i1));
		            	}else {
		            		hex1.append(String.format("%02X ", i1));
		            	}
		            	if (!(Character.isISOControl(i1) || i1 < 32 || i1 > 127)) {
		                    input.append((char) i1);
		                } else {
		                    input.append(".");
		                }
		            }else if(brojProcZnakova < 17) {
		            	hex2.append(String.format("%02X ", i1));
		            	if (!(Character.isISOControl(i1) || i1 < 32 || i1 > 127)) {
		                    input.append((char) i1);
		                } else {
		                    input.append(".");
		                }
		            }else {
						count = getCount(env, count, ispis, hex1, hex2, input);
						brojProcZnakova = 0;
		            }
	            }
	            if(brojProcZnakova > 0) {
					getCount(env, count, ispis, hex1, hex2, input);
				}
	        } catch (FileNotFoundException e) {
	        	env.writeln("Greška kod otvaranja datoteke");
			} catch (IOException e) {
				env.writeln("Greška kod èitanja datoteke");
			}
		}
		return ShellStatus.CONTINUE;
	}

	private int getCount(Environment env, int count, StringBuilder ispis, StringBuilder hex1, StringBuilder hex2, StringBuilder input) {
		ispis.append(String.format("%08X: ", count * 16), 0, 10);
		ispis.append(String.format("%-23s|%-24s|%-16s", hex1, hex2, input));
		hex1.setLength(0);
		hex2.setLength(0);
		input.setLength(0);
		env.writeln(ispis.toString());
		count++;
		ispis.setLength(0);
		return count;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
        return List.of("Uzima 1 argument koji je ime datoteke", "Proizvodi izlaz hexadekadskih znakova", "Ako je vrijednost nekog znaka manja od 32 ili veæa od 127 umjesto nje se zapisuje .");
	}
}
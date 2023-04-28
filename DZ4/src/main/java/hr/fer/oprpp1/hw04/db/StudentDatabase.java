package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDatabase {
	
	private List<StudentRecord> listaStudenata;
	private HashMap<String, StudentRecord> mapaStudenata;
	
	public StudentDatabase(List<String> listaStudenataString) throws Exception {
		this.listaStudenata = new ArrayList<>();
		this.mapaStudenata = new HashMap<>();
		for(String s : listaStudenataString) {
			String[] poljeAtributa = s.split("	");
			String jmbag = poljeAtributa[0];
			String prezime = poljeAtributa[1];
			String ime = poljeAtributa[2];
			int ocjena = Integer.parseInt(poljeAtributa[3]);
			if(mapaStudenata.containsKey(jmbag)) {
				throw new Exception("Jmbag veæ postoji, dupilikat je!");
			}else if(ocjena > 5 || ocjena < 1) {
				throw new Exception("Ocjena je nepravilna!");
			}else {
				StudentRecord rec = new StudentRecord(jmbag, prezime, ime, ocjena);
				mapaStudenata.put(jmbag, rec);
				listaStudenata.add(rec);
			}
		}
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		if(!mapaStudenata.containsKey(jmbag)) {
			return null;
		}
		return mapaStudenata.get(jmbag);
	}
	
	public List<StudentRecord> filter(IFilter filter) throws Exception {
		List<StudentRecord> tempList = new ArrayList<>();
		for(StudentRecord r : listaStudenata) {
			if(filter.accepts(r)) {
				tempList.add(r);
			}
		}
		return tempList;
	}
}
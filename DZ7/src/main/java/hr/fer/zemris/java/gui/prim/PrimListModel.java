package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer>{
	
	private List<Integer> listaBrojeva;
	private List<ListDataListener> promatraci;
	
	public PrimListModel() {
		this.listaBrojeva = new ArrayList<>();
		this.promatraci = new ArrayList<>();
		this.listaBrojeva.add(1);
	}
	
	public void add(int element) {
		int pos = listaBrojeva.size();
		listaBrojeva.add(element);
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : promatraci) {
			l.intervalAdded(event);
		}
	}

	public int next() {
		int primBroj = (int) listaBrojeva.get(listaBrojeva.size() - 1);
		int sljedeci;
		int i = 1;
		while(!isPrimeBruteForce(primBroj + i)) {
			i++;
		}
		sljedeci = primBroj + i;
		return sljedeci;
	}
	
	public static boolean isPrimeBruteForce(int number) {
	    for (int i = 2; i < number; i++) {
	        if (number % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}

	@Override
	public int getSize() {
		return listaBrojeva.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return listaBrojeva.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		promatraci.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		promatraci.remove(l);
	}
}
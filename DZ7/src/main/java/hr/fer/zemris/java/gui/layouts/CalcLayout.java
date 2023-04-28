package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class CalcLayout implements LayoutManager2{
	
	private int razmakRedStup;
	private HashMap<Component, RCPosition> mapaPozicija;
	
	public CalcLayout() {
		this.razmakRedStup = 0;
		this.mapaPozicija = new HashMap<>();
	}
	
	public CalcLayout(int razmakRedStup) {
		this.razmakRedStup = razmakRedStup;
		this.mapaPozicija = new HashMap<>();
	}

	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("NE POZIVAJ OVO!");
	}

	public void removeLayoutComponent(Component comp) {
		mapaPozicija.remove(comp);
	}

	public Dimension preferredLayoutSize(Container parent) {
		/*int visinaKont = parent.getHeight(), sirinaKont = parent.getHeight();
		
		for(Component c : mapaPozicija.keySet()) {
			visinaKont += c.getPreferredSize().height + razmakRedStup;
			sirinaKont += c.getPreferredSize().width + razmakRedStup;
		}
		return new Dimension(visinaKont, sirinaKont);*/
		
		//izracun preferirane visine i sirine stupaca i redova
		RCPosition poz11 = new RCPosition(1, 1);
		int visinaRetka = Integer.MIN_VALUE, sirinaStupca = Integer.MIN_VALUE;
		int najveciRed = 0, najveciStup = 0;
		int visinaKont, sirinaKont;
		Set<Component> setKomponenti = mapaPozicija.keySet();
		Iterator<Component> iter = setKomponenti.iterator();
		while(iter.hasNext()) {
			Component c = iter.next();
			RCPosition pozicija = mapaPozicija.get(c);
			if(pozicija.getRow() > najveciRed) {
				najveciRed = pozicija.getRow();
			}
			if(pozicija.getColumn() > najveciStup) {
				najveciStup = pozicija.getColumn();
			}
			if(mapaPozicija.get(c).equals(poz11)) {
				//System.out.println(c.getPreferredSize().width);
				//System.out.println(c.getPreferredSize().height);
				najveciStup = najveciStup < 5 ? 5 : najveciStup;
				if(c.getPreferredSize().height > visinaRetka) {
					visinaRetka = c.getPreferredSize().height;
				}
			}else {
				if(c.getPreferredSize().height > visinaRetka) {
					visinaRetka = c.getPreferredSize().height;
				}
				if(c.getPreferredSize().width > sirinaStupca) {
					sirinaStupca = c.getPreferredSize().width;
				}
			}
		}
		visinaKont = najveciRed * visinaRetka + (najveciRed - 1) * razmakRedStup;
		sirinaKont = najveciStup * sirinaStupca + (najveciStup - 1) * razmakRedStup;
		return new Dimension(sirinaKont, visinaKont);
	}

	public Dimension minimumLayoutSize(Container parent) {
		RCPosition poz11 = new RCPosition(1, 1);
		int visinaRetka = Integer.MIN_VALUE, sirinaStupca = Integer.MIN_VALUE;
		int najveciRed = 0, najveciStup = 0;
		int visinaKont, sirinaKont;
		Set<Component> setKomponenti = mapaPozicija.keySet();
		Iterator<Component> iter = setKomponenti.iterator();
		while(iter.hasNext()) {
			Component c = iter.next();
			RCPosition pozicija = mapaPozicija.get(c);
			if(pozicija.getRow() > najveciRed) {
				najveciRed = pozicija.getRow();
			}
			if(pozicija.getColumn() > najveciStup) {
				najveciStup = pozicija.getColumn();
			}
			if(mapaPozicija.get(c).equals(poz11)) {
				najveciStup = 5;
				if(c.getMinimumSize().height > visinaRetka) {
					visinaRetka = c.getMinimumSize().height;
				}
			}else {
				if(c.getMinimumSize().height > visinaRetka) {
					visinaRetka = c.getMinimumSize().height;
				}
				if(c.getMinimumSize().width > sirinaStupca) {
					sirinaStupca = c.getMinimumSize().width;
				}
			}
		}
		visinaKont = najveciRed * visinaRetka + (najveciRed - 1) * razmakRedStup;
		sirinaKont = najveciStup * sirinaStupca + (najveciStup - 1) * razmakRedStup;
		return new Dimension(sirinaKont, visinaKont);
	}

	public void layoutContainer(Container parent) {
		//parent.getInsets().set(50, 100, 200, 400);
		// TODO Auto-generated method stub
		int lBor = parent.getInsets().left;
		int gornjiBor = parent.getInsets().top;
		int donjiBor = parent.getInsets().bottom;
		int dBor = parent.getInsets().right;
		
		Dimension dimKont = parent.getSize();
		/*System.out.println(lBor);
		System.out.println(gornjiBor);
		System.out.println(donjiBor);
		System.out.println(dBor);*/
		int xKont = parent.getLocation().x, yKont = parent.getLocation().y;
		
		int sirinaKont = dimKont.width - lBor - dBor, visinaKont = dimKont.height - gornjiBor - donjiBor;
		
		int dostupnaSirinaZaCelije = sirinaKont - 6 * razmakRedStup, dostupnaVisinaZaCelije = visinaKont - 4 * razmakRedStup;
		//System.out.println(dostupnaSirinaZaCelije);
		//System.out.println(dostupnaVisinaZaCelije);
		
		int visinaReda = (int) Math.ceil((double)dostupnaVisinaZaCelije / 5);
		int manjaVisinaReda = (int) Math.floor((double)dostupnaVisinaZaCelije / 5);
		
		//System.out.println(visinaReda);
		//System.out.println(manjaVisinaReda);
		
		int sirinaStupca = (int) Math.ceil(dostupnaSirinaZaCelije / 7);
		int manjaSirinaStupca = (int) Math.floor(dostupnaSirinaZaCelije / 7);
		
		int kolikoImaDodatneSirine = 7 * sirinaStupca - dostupnaSirinaZaCelije;
		int kolikoImaDodatneVisine = 5 * visinaReda - dostupnaVisinaZaCelije;
		
		/*HashMap<Integer, Integer> mapaDodatakaRed = new HashMap<>();
		HashMap<Integer, Integer> mapaDodatakaStup = new HashMap<>();
		for(int i = 1; i <= 5; i++) {
			mapaDodatakaRed.put(i, kolikoImaDodatneVisine);
		}
		for(int i = 1; i <= 7; i++) {
			mapaDodatakaStup.put(i, kolikoImaDodatneSirine);
		}*/
		
		boolean neparnoDodSir = kolikoImaDodatneSirine % 2 == 0 ? false : true;
		boolean neparnoDodVis = kolikoImaDodatneVisine % 2 == 0 ? false : true;
		//System.out.println(kolikoImaDodatneSirine);
		//System.out.println(kolikoImaDodatneVisine);
		
		int brojac = 0;
		for(Entry<Component, RCPosition> set : mapaPozicija.entrySet()) {
			//brojac++;
			Component komponenta = set.getKey();
			RCPosition pozicija = set.getValue();
			int x = pozicija.getRow(), y = pozicija.getColumn();
			int pozicijaX = 0, pozicijaY = 0, sirina = 0, visina = 0;
			
			if(x == 1 && y == 1) {
				pozicijaX = xKont + lBor;
				pozicijaY = yKont + gornjiBor;
				sirina = manjaSirinaStupca * 5 + 4 * razmakRedStup;
				visina = manjaVisinaReda;
			}/*else if(x == 1) {
				pozicijaY = yKont + gornjiBor;
				pozicijaX = xKont + lBor + y * manjaSirinaStupca + razmakRedStup;
				if(kolikoImaDodatneSirine > 0) {
					
				}
				
			}*/
			else {
				if(y != 1) {
					pozicijaX = xKont + lBor + (y - 1) * (manjaSirinaStupca + razmakRedStup);
				}else {
					pozicijaX = xKont + lBor + (y - 1) * manjaSirinaStupca;
				}
				if(x != 1) {
					pozicijaY = yKont + gornjiBor + (x - 1) * (manjaVisinaReda + razmakRedStup);
				}else {
					pozicijaY = yKont + gornjiBor + (x - 1) * manjaVisinaReda;
				}
				visina = manjaVisinaReda;
				sirina = manjaSirinaStupca;
			}
			komponenta.setBounds(pozicijaX, pozicijaY, sirina, visina);
		}
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null) {
			throw new NullPointerException("Predana komponenta ili ogranièenja je null!");
		}
		
		if(!(constraints instanceof RCPosition) && !(constraints instanceof String)) {
			throw new IllegalArgumentException("Nepravilni tip ogranièenja!");
		}
		
		RCPosition pozicija = constraints instanceof String 
								? RCPosition.parse((String) constraints) 
								: (RCPosition) constraints;
		
		if(pozicija.getRow() > 5 || pozicija.getRow() < 1 || pozicija.getColumn() > 7 || pozicija.getColumn() < 1) {
			throw new CalcLayoutException("Nepravilno zadana ogranièenja!");
		}
		
		if(pozicija.getRow() == 1 && pozicija.getColumn() < 6 && pozicija.getColumn() > 1) {
			throw new CalcLayoutException("Nepravilno zadana ogranièenja za prvi redak!");
		}
		
		for(RCPosition p : mapaPozicija.values()) {
			if(p.getColumn() == pozicija.getColumn() && p.getRow() == pozicija.getRow()) {
				throw new CalcLayoutException("Komponenta veæ postoji na toj poziciji!");
			}
		}
		
		mapaPozicija.put(comp, pozicija);
	}

	public Dimension maximumLayoutSize(Container target) {
		RCPosition poz11 = new RCPosition(1, 1);
		int visinaRetka = Integer.MIN_VALUE, sirinaStupca = Integer.MIN_VALUE;
		int najveciRed = 0, najveciStup = 0;
		int visinaKont, sirinaKont;
		Set<Component> setKomponenti = mapaPozicija.keySet();
		Iterator<Component> iter = setKomponenti.iterator();
		while(iter.hasNext()) {
			Component c = iter.next();
			RCPosition pozicija = mapaPozicija.get(c);
			if(pozicija.getRow() > najveciRed) {
				najveciRed = pozicija.getRow();
			}
			if(pozicija.getColumn() > najveciStup) {
				najveciStup = pozicija.getColumn();
			}
			if(mapaPozicija.get(c).equals(poz11)) {
				najveciStup = 5;
				if(c.getMaximumSize().height > visinaRetka) {
					visinaRetka = c.getMaximumSize().height;
				}
			}else {
				if(c.getMaximumSize().height > visinaRetka) {
					visinaRetka = c.getMaximumSize().height;
				}
				if(c.getMaximumSize().width > sirinaStupca) {
					sirinaStupca = c.getMaximumSize().width;
				}
			}
		}
		visinaKont = najveciRed * visinaRetka + (najveciRed - 1) * razmakRedStup;
		sirinaKont = najveciStup * sirinaStupca + (najveciStup - 1) * razmakRedStup;
		return new Dimension(sirinaKont, visinaKont);
	}

	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	public void invalidateLayout(Container target) {}
}
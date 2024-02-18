package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{

	private int size;
	private TableEntry<K, V>[] table;
	private int modificationCount;
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.size = 0;
		this.table = new TableEntry[16];
		this.modificationCount = 0;
	}
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Zadani kapacitet je nedozvoljen!");
		}
		this.size = 0;
		this.modificationCount = 0;
		int pocetniKapacitet;
		double potencija = Math.log(capacity) / Math.log(2);
		if(potencija % 1 == 0) {
			pocetniKapacitet = capacity;
		}else {
			potencija = Math.ceil(potencija);
			pocetniKapacitet = (int) Math.pow(2, potencija);
		}
		this.table = new TableEntry[pocetniKapacitet];
	}
	
	public static class TableEntry<K, V> {
		private K kljuc;
		private V vrijednost;
		private TableEntry<K, V> next;
		
		public K getKljuc() {
			return kljuc;
		}
		public V getVrijednost() {
			return vrijednost;
		}
		public void setVrijednost(V vrijednost) {
			this.vrijednost = vrijednost;
		}
	}
	
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		private int savedModificationCount;
		private int brojac;
		private TableEntry<K, V> trenutni;
		
		public IteratorImpl() {
			this.savedModificationCount = modificationCount;
			this.brojac = 0;
			this.trenutni = null;
		}
		@Override
		public boolean hasNext() {
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Nastupila je promjena strukture kolekcije!");
			}
            return brojac < size;
        }
		@Override
		@SuppressWarnings("rawtypes")
		public SimpleHashtable.TableEntry next() {
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Nastupila je promjena strukture kolekcije!");
			}
			if(!hasNext()) {
				throw new NoSuchElementException("Nema daljnjih elemenata!");
			}else {
				if(brojac == 0) {
                    for (TableEntry<K, V> kvTableEntry : table) {
                        if (kvTableEntry != null) {
                            trenutni = kvTableEntry;
                            brojac++;
                            return trenutni;
                        }
                    }
				}else if(trenutni.next != null) {
					trenutni = trenutni.next;
					brojac++;
					return trenutni;
				}else {
					for(int trenutniIndex = 0, i = 0; i < table.length; i++) {
						trenutni = table[i];
						while(trenutni != null) {
							if(trenutniIndex == brojac) {
								brojac++;
								return trenutni;
							}
							trenutniIndex++;
							if(trenutni.next != null) {
								trenutni = trenutni.next;
							}else {
								break;
							}
						}
					}
				}
			}
			return null;
		}
		@Override
		public void remove() {
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Nastupila je promjena strukture kolekcije!");
			}
			try {
				if(containsKey(trenutni.kljuc)) {
					SimpleHashtable.this.remove(trenutni.kljuc);
					this.savedModificationCount++;
					brojac--;
				}else {
					throw new ConcurrentModificationException("Nastupila je promjena strukture kolekcije!");
				}
			}catch(NullPointerException e) {
				throw new IllegalStateException("Nedozvoljen ponovni poziv remove!");
			}
		}
	}
	/**
	 * metoda dodaje zadani par u kolekciju, ako ima premalo mjesta udvorstruèuje velièinu tablice i onda dodaje
	 * @param key kljuè koji se dodaje
	 * @param value vrijednost koja se dodaje
	 * @return vraæa null ako kljuè nije postojao, inaèe vrijednost koja je prije bila zapisana pod istim kljuèem
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Predani kljuc je null!");
		}
		if((double) this.size / this.table.length < 0.75) {
			int index = Math.abs(key.hashCode()) % this.table.length;
			if(this.table[index] == null) {
				TableEntry<K, V> noviPodatak = new TableEntry<>();
				noviPodatak.kljuc = key;
				noviPodatak.vrijednost = value;
				noviPodatak.next = null;
				this.table[index] = noviPodatak;
				this.size++;
				this.modificationCount++;
				return null;
			}else {
				V povratna;
				TableEntry<K, V> trenutni = this.table[index];
				while(trenutni != null) {
					if(trenutni.kljuc == key) {
						povratna = trenutni.getVrijednost();
						trenutni.setVrijednost(value);
						return povratna;
					}
					trenutni = trenutni.next;
				}
				trenutni = this.table[index];
				while(trenutni.next != null) {
					trenutni = trenutni.next;
				}
				trenutni.next = new TableEntry<>();
				trenutni = trenutni.next;
				trenutni.kljuc = key;
				trenutni.vrijednost = value;
				trenutni.next = null;
				this.size++;
				this.modificationCount++;
			}
		}else {
			TableEntry<K, V>[] originalnaTablica = this.toArray();
			this.table = new TableEntry[this.table.length * 2];
            for (TableEntry<K, V> kvTableEntry : originalnaTablica) {
                int index = Math.abs(kvTableEntry.kljuc.hashCode()) % this.table.length;
                if (this.table[index] == null) {
                    TableEntry<K, V> noviPodatak = new TableEntry<>();
                    noviPodatak.kljuc = kvTableEntry.kljuc;
                    noviPodatak.vrijednost = kvTableEntry.vrijednost;
                    noviPodatak.next = null;
                    this.table[index] = noviPodatak;
                } else {
                    TableEntry<K, V> trenutni = this.table[index];
                    while (trenutni.next != null) {
                        trenutni = trenutni.next;
                    }
                    trenutni.next = new TableEntry<>();
                    trenutni = trenutni.next;
                    trenutni.kljuc = kvTableEntry.kljuc;
                    trenutni.vrijednost = kvTableEntry.vrijednost;
                }
            }
			int index = Math.abs(key.hashCode()) % this.table.length;
			if(this.table[index] == null) {
				TableEntry<K, V> noviPodatak = new TableEntry<>();
				noviPodatak.kljuc = key;
				noviPodatak.vrijednost = value;
				this.table[index] = noviPodatak;
				this.size++;
				this.modificationCount++;
				return null;
			}else {
				V povratna;
				TableEntry<K, V> trenutni = this.table[index];
				while(trenutni != null) {
					if(trenutni.kljuc == key) {
						povratna = trenutni.getVrijednost();
						trenutni.setVrijednost(value);
						return povratna;
					}
					trenutni = trenutni.next;
				}
				trenutni = this.table[index];
				while(trenutni.next != null) {
					trenutni = trenutni.next;
				}
				trenutni.next = new TableEntry<>();
				trenutni = trenutni.next;
				trenutni.kljuc = key;
				trenutni.vrijednost = value;
				trenutni.next = null;
				this.size++;
				this.modificationCount++;
			}
		}
		return null;
	}
	/**
	 * vraæa vrijednost kojoj se dostupa preko zadanog kljuèa inaèe null
	 * @param key kljuè èija se vrijednost traži
	 * @return vrijednost od zadanog kljuèa
	 */
	public V get(Object key) {
		TableEntry<K, V> trenutni;
        for (TableEntry<K, V> kvTableEntry : this.table) {
            trenutni = kvTableEntry;
            while (trenutni != null) {
                if (trenutni.getKljuc().equals(key)) {
                    return trenutni.getVrijednost();
                }
                trenutni = trenutni.next;
            }
        }
		return null;
	}
	/**
	 * vraæa trenutni broj elemenata u kolekciji
	 * @return broj elemenata u kolekciji
	 */
	public int size() {
		return this.size;
	}
	/**
	 * provjera da li postoji zadani kljuè u kolekciji
	 * @param key koji se provjerava
	 * @return true ako postoji inaèe false
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			throw new NullPointerException("Predani kljuc je null!");
		}
		int index = Math.abs(key.hashCode()) % this.table.length;
		TableEntry<K, V> trenutni = this.table[index];
		while(trenutni != null) {
			if(trenutni.getKljuc().equals(key)) {
				return true;
			}
			trenutni = trenutni.next;
		}
		return false;
	}
	/**
	 * provjera da li postoji zadana vrijednost u kolekciji
	 * @param value vrijednost koja se provjerava
	 * @return true ako postoji inaèe false
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> trenutni;
        for (TableEntry<K, V> kvTableEntry : this.table) {
            trenutni = kvTableEntry;
            while (trenutni != null) {
                if (trenutni.getVrijednost() == value) {
                    return true;
                }
                trenutni = trenutni.next;
            }
        }
		return false;
	}
	/**
	 * ako postoji briše iz kolekcije kljuè i njegovu vrijedost
	 * @param key kljuè koji se sa svojom vrijednošæu briše iz kolekcije
	 * @return obrisanu vrijednost, inaèe null
	 */
	public V remove(Object key) {
		if(key == null) {
			return null;
		}
		int index = Math.abs(key.hashCode()) % this.table.length;
		TableEntry<K, V> trenutni = this.table[index];
		V povratna;
		if(trenutni == null) {
			return null;
		}else if(trenutni.kljuc.equals(key)) {
			this.table[index] = trenutni.next;
			povratna = trenutni.vrijednost;
			trenutni.vrijednost = null;
			trenutni.kljuc = null;
            this.size--;
			this.modificationCount++;
			return povratna;
		}else if(trenutni.next != null) {
			while(trenutni.next != null) {
				if(trenutni.next.kljuc.equals(key)) {
					povratna = trenutni.next.vrijednost;
					trenutni.next.vrijednost = null;
					trenutni.next.kljuc = null;
					trenutni.next = trenutni.next.next;
					trenutni.next.next = null;
					this.size--;
					this.modificationCount++;
					return povratna;
				}
				trenutni = trenutni.next;
			}
			if(trenutni.kljuc.equals(key)) {
				povratna = trenutni.vrijednost;
				trenutni.kljuc = null;
				trenutni.vrijednost = null;
                this.size--;
				this.modificationCount++;
				return povratna;
			}
		}
		return null;
	}
	/**
	 * provjerava da li je kolekcija prazna
	 * @return true ako je inaèe false
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	/**
	 * vraæa String koji predstavlja kolekciju
	 */
	public String toString() {
		StringBuilder mapa = new StringBuilder("[");
		TableEntry<K, V> trenutni;
		for(int i = 0, brojIspisanih = 0; i < this.table.length; i++) {
			trenutni = this.table[i];
			while(trenutni != null) {
				mapa.append(trenutni.getKljuc().toString()).append("=").append(trenutni.getVrijednost().toString());
				brojIspisanih++;
				if(brojIspisanih < this.size) {
					mapa.append(", ");
				}
				trenutni = trenutni.next;
			}
		}
		mapa.append("]");
		return mapa.toString();
	}
	
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray() {
		TableEntry<K, V>[] novaTablica = new TableEntry[this.size];
		TableEntry<K, V> trenutni;
		int j = 0;
        for (TableEntry<K, V> kvTableEntry : this.table) {
            trenutni = kvTableEntry;
            while (trenutni != null) {
                novaTablica[j] = trenutni;
                j++;
                trenutni = trenutni.next;
            }
        }
		return novaTablica;
	}
	/**
	 * briše sve elemente iz kolekcije
	 */
	public void clear() {
		TableEntry<K, V> trenutni;
		for(int i = 0; i < this.table.length; i++) {
			trenutni = this.table[i];
			while(trenutni != null) {
				trenutni.kljuc = null;
				trenutni.vrijednost = null;
				trenutni = trenutni.next;
				this.size--;
			}
			this.table[i] = null;
		}
	}
	/**
	 * vraæa novi iterator za iteriranje nad elementima kolekcije
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
		return new IteratorImpl();
	}
}
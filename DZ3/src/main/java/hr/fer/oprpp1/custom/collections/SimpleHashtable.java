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
		size = 0;
		table = new TableEntry[16];
		modificationCount = 0;
	}
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Zadani kapacitet je nedozvoljen!");
		}
		size = 0;
		modificationCount = 0;
		int pocetniKapacitet;
		double potencija = Math.log(capacity) / Math.log(2);
		if(potencija % 1 == 0) {
			pocetniKapacitet = capacity;
		}else {
			potencija = Math.ceil(potencija);
			pocetniKapacitet = (int) Math.pow(2, potencija);
		}
		table = new TableEntry[pocetniKapacitet];
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
			savedModificationCount = modificationCount;
			brojac = 0;
			trenutni = null;
		}
		@Override
		public boolean hasNext() {
			if(savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Nastupila je promjena strukture kolekcije!");
			}
            return brojac < size;
        }
		@SuppressWarnings({"rawtypes", "unchecked"})
		@Override
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
					savedModificationCount++;
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
	 * metoda dodaje zadani par u kolekciju, ako ima premalo mjesta udvorstru�uje veli�inu tablice i onda dodaje
	 *
	 * @param key   klju� koji se dodaje
	 * @param value vrijednost koja se dodaje
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Predani kljuc je null!");
		}
		if((double) size / table.length < 0.75) {
			int index = Math.abs(key.hashCode()) % table.length;
			if(table[index] == null) {
				TableEntry<K, V> noviPodatak = new TableEntry<>();
				noviPodatak.kljuc = key;
				noviPodatak.vrijednost = value;
				noviPodatak.next = null;
				table[index] = noviPodatak;
            }else {
				tableMethod(key, value, index);
			}
        }else {
			TableEntry<K, V>[] originalnaTablica = this.toArray();
			table = new TableEntry[table.length * 2];
            for (TableEntry<K, V> kvTableEntry : originalnaTablica) {
                int index = Math.abs(kvTableEntry.kljuc.hashCode()) % table.length;
                if (table[index] == null) {
                    TableEntry<K, V> noviPodatak = new TableEntry<>();
                    noviPodatak.kljuc = kvTableEntry.kljuc;
                    noviPodatak.vrijednost = kvTableEntry.vrijednost;
                    noviPodatak.next = null;
                    table[index] = noviPodatak;
                } else {
                    TableEntry<K, V> trenutni = table[index];
                    while (trenutni.next != null) {
                        trenutni = trenutni.next;
                    }
                    trenutni.next = new TableEntry<>();
                    trenutni = trenutni.next;
                    trenutni.kljuc = kvTableEntry.kljuc;
                    trenutni.vrijednost = kvTableEntry.vrijednost;
                }
            }
			int index = Math.abs(key.hashCode()) % table.length;
			if(table[index] == null) {
				TableEntry<K, V> noviPodatak = new TableEntry<>();
				noviPodatak.kljuc = key;
				noviPodatak.vrijednost = value;
				table[index] = noviPodatak;
            }else {
				tableMethod(key, value, index);
            }
        }
        size++;
        modificationCount++;
    }

	private void tableMethod(K key, V value, int index) {
		TableEntry<K, V> trenutni = table[index];
		while(trenutni != null) {
			if(trenutni.kljuc == key) {
trenutni.setVrijednost(value);
				return;
			}
			trenutni = trenutni.next;
		}
		trenutni = table[index];
		while(trenutni.next != null) {
			trenutni = trenutni.next;
		}
		trenutni.next = new TableEntry<>();
		trenutni = trenutni.next;
		trenutni.kljuc = key;
		trenutni.vrijednost = value;
		trenutni.next = null;
	}

	/**
	 * vra�a vrijednost kojoj se dostupa preko zadanog klju�a ina�e null
	 * @param key klju� �ija se vrijednost tra�i
	 * @return vrijednost od zadanog klju�a
	 */
	public V get(Object key) {
		TableEntry<K, V> trenutni;
        for (TableEntry<K, V> kvTableEntry : table) {
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
	 * vra�a trenutni broj elemenata u kolekciji
	 * @return broj elemenata u kolekciji
	 */
	public int size() {
		return size;
	}
	/**
	 * provjera da li postoji zadani klju� u kolekciji
	 * @param key koji se provjerava
	 * @return true ako postoji ina�e false
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			throw new NullPointerException("Predani kljuc je null!");
		}
		int index = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> trenutni = table[index];
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
	 * @return true ako postoji ina�e false
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> trenutni;
        for (TableEntry<K, V> kvTableEntry : table) {
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
	 * ako postoji bri�e iz kolekcije klju� i njegovu vrijedost
	 * @param key klju� koji se sa svojom vrijedno��u bri�e iz kolekcije
	 * @return obrisanu vrijednost, ina�e null
	 */
	public V remove(Object key) {
		if(key == null) {
			return null;
		}
		int index = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> trenutni = table[index];
		V povratna;
		if(trenutni == null) {
			return null;
		}else if(trenutni.kljuc.equals(key)) {
			table[index] = trenutni.next;
			povratna = trenutni.vrijednost;
			trenutni.vrijednost = null;
			trenutni.kljuc = null;
            size--;
			modificationCount++;
			return povratna;
		}else if(trenutni.next != null) {
			while(trenutni.next != null) {
				if(trenutni.next.kljuc.equals(key)) {
					povratna = trenutni.next.vrijednost;
					trenutni.next.vrijednost = null;
					trenutni.next.kljuc = null;
					trenutni.next = trenutni.next.next;
					trenutni.next.next = null;
					size--;
					modificationCount++;
					return povratna;
				}
				trenutni = trenutni.next;
			}
			if(trenutni.kljuc.equals(key)) {
				povratna = trenutni.vrijednost;
				trenutni.kljuc = null;
				trenutni.vrijednost = null;
                size--;
				modificationCount++;
				return povratna;
			}
		}
		return null;
	}
	/**
	 * provjerava da li je kolekcija prazna
	 * @return true ako je ina�e false
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	/**
	 * vra�a String koji predstavlja kolekciju
	 */
	public String toString() {
		StringBuilder mapa = new StringBuilder("[");
		TableEntry<K, V> trenutni;
		for(int i = 0, brojIspisanih = 0; i < table.length; i++) {
			trenutni = table[i];
			while(trenutni != null) {
				mapa.append(trenutni.getKljuc().toString()).append("=").append(trenutni.getVrijednost().toString());
				brojIspisanih++;
				if(brojIspisanih < size) {
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
		TableEntry<K, V>[] novaTablica = new TableEntry[size];
		TableEntry<K, V> trenutni;
		int j = 0;
        for (TableEntry<K, V> kvTableEntry : table) {
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
	 * bri�e sve elemente iz kolekcije
	 */
	public void clear() {
		TableEntry<K, V> trenutni;
		for(int i = 0; i < table.length; i++) {
			trenutni = table[i];
			while(trenutni != null) {
				trenutni.kljuc = null;
				trenutni.vrijednost = null;
				trenutni = trenutni.next;
				size--;
			}
			table[i] = null;
		}
	}
	/**
	 * vra�a novi iterator za iteriranje nad elementima kolekcije
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
		return new IteratorImpl();
	}
}
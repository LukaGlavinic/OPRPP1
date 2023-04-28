package hr.fer.oprpp1.custom.collections;
/**
 * sučelje s metodama za rad nad kolekcijom
 * @author Luka
 *
 */
public interface List extends Collection{
/**
 * metoda koja vra�a objekt kolekcije na zadanom indexu
 * @param index zadan za pristup �lanu na toj poziciji kolekcije
 * @return objekt �lana kolekcije na zadanom indexu
 */
	public Object get(int index);
	/**
	 * metoda ume�e zadanu vrijednost na zadanu poziciju u kolekciju
	 * @param value zadana vrijednost za umetnuti u kolekciju
	 * @param position pozicija umetanja
	 */
	public void insert(Object value, int position);
	/**
	 * metoda za vra�anje indeksa predanog objekta ako postoji, ina�e baca iznimku
	 * @param value objekt �ija se pozicija traži i ako postoji vra�a
	 * @return index na kojoj se objekt value nalazi
	 */
	public int indexOf(Object value);
	/**
	 * metoda za brisanje �lana na predanom indeksu iz kolekcije
	 * @param index indeks na kojem se iz kolekcije bri�e �lan
	 */
	public void remove(int index);
}
package hr.fer.oprpp1.custom.collections;
/**
 * suƒçelje s metodama za rad nad kolekcijom
 * @author Luka
 *
 */
public interface List extends Collection{
/**
 * metoda koja vraÊa objekt kolekcije na zadanom indexu
 * @param index zadan za pristup Ëlanu na toj poziciji kolekcije
 * @return objekt Ëlana kolekcije na zadanom indexu
 */
	public Object get(int index);
	/**
	 * metoda umeÊe zadanu vrijednost na zadanu poziciju u kolekciju
	 * @param value zadana vrijednost za umetnuti u kolekciju
	 * @param position pozicija umetanja
	 */
	public void insert(Object value, int position);
	/**
	 * metoda za vraÊanje indeksa predanog objekta ako postoji, inaËe baca iznimku
	 * @param value objekt Ëija se pozicija tra≈æi i ako postoji vraËa
	 * @return index na kojoj se objekt value nalazi
	 */
	public int indexOf(Object value);
	/**
	 * metoda za brisanje Ëlana na predanom indeksu iz kolekcije
	 * @param index indeks na kojem se iz kolekcije briöe Ëlan
	 */
	public void remove(int index);
}
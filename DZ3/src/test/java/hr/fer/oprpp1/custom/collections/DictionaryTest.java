package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void putTest() {
		Object povratna;
		Dictionary<Integer, String> rijecnik = new Dictionary<>();
		povratna = rijecnik.put(1, "Franjo");
		assertEquals(null, povratna);
		povratna = rijecnik.put(1, "Marko");
		assertEquals("Franjo", povratna);
		assertEquals("Marko", rijecnik.get(1));
	}
	
	@Test
	public void getTest() {
		Object povratna;
		Dictionary<Integer, String> rijecnik = new Dictionary<>();
		povratna = rijecnik.put(1, "Franjo");
		assertEquals(null, povratna);
		povratna = rijecnik.put(1, "Marko");
		assertEquals("Franjo", povratna);
		assertEquals("Marko", rijecnik.get(1));
		assertEquals(null, rijecnik.get(2));
	}
	
	@Test
	public void removeTest() {
		Object povratna;
		Dictionary<Integer, String> rijecnik = new Dictionary<>();
		povratna = rijecnik.put(1, "Franjo");
		assertEquals(null, povratna);
		povratna = rijecnik.put(1, "Marko");
		assertEquals("Franjo", povratna);
		assertEquals("Marko", rijecnik.get(1));
		assertEquals(null, rijecnik.get(2));
		assertEquals("Marko", rijecnik.remove(1));
		assertEquals(null, rijecnik.get(1));
	}
}
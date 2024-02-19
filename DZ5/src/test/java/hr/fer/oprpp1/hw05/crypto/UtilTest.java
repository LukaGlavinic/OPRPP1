package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {

	@Test
	public void hexToByteTest() {
		String a = "01aE22";
		byte[] b = {1, -82, 34};
		assertEquals(b, Util.hextobyte(a));
	}
	
	@Test
	public void byteToHexTest() {
		String a = "01aE22";
		byte[] b = {1, -82, 34};
		assertEquals(a, Util.bytetohex(b));
	}
}
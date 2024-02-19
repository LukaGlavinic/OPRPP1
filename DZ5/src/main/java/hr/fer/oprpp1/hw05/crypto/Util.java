package hr.fer.oprpp1.hw05.crypto;

public class Util {

	public static byte[] hextobyte(String keyText) {
		String regex = "^[0-9A-Fa-f]+$";
		if(keyText.length() % 2 != 0 || !keyText.matches(regex)) {
			throw new IllegalArgumentException("Krivo zadan string!");
		}else if(keyText.isEmpty()) {
			return new byte[0];
		}
		byte[] rez = new byte[keyText.length() / 2];
		for (int i = 0; i < rez.length; i++) {
			   int index = i * 2;
			   int j = Integer.parseInt(keyText.substring(index, index + 2), 16);
			   rez[i] = (byte) j;
			}
		return rez;
	}
	
	public static String bytetohex(byte[] bytearray) {
		if(bytearray.length == 0) {
			return "";
		}
		char[] HEX = "0123456789abcdef".toCharArray();
		char[] hexZnakovi = new char[bytearray.length * 2];
		for(int i = 0; i < bytearray.length; i++) {
			int j = bytearray[i] & 0xFF;
			hexZnakovi[i * 2] = HEX[j>>>4];
			hexZnakovi[i * 2 + 1] = HEX[j & 0x0F];
		}
		return new String(hexZnakovi);
	}
}
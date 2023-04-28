package hr.fer.oprpp1.hw05.crypto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	public static void main(String[] args) {
		String method = args[0];
		String fileName = args[1];
		boolean encrypt = false;
		Scanner sc = new Scanner(System.in);
		if(method.equals("checksha")) {
			System.out.println("Please provide expected sha-256 digest for " + fileName);
			System.out.print("> ");
			String shaDig = sc.nextLine();
			try {
				MessageDigest digObj = MessageDigest.getInstance("SHA-256");
				Path p = Paths.get("C:\\Users\\Luka\\eclipse-workspace\\hw05-0036533686\\src\\main\\java\\hr\\fer\\oprpp1\\hw05\\crypto\\" + fileName);
				InputStream is = null;
				try {
					is = Files.newInputStream(p);
					byte[] buff = new byte[4096];
					while(true) {
						int r = is.read(buff);
						if(r<1) break;
						if(r == buff.length) {
							digObj.update(buff);
						}else {
							for(int i = 0; i < r; i++) {
								digObj.update(buff[i]);
							}
						}
					}
					byte[] rez = digObj.digest();
					String rezHex = Util.bytetohex(rez);
					if(rez != null && rez.length > 0) {
						System.out.print("Digest complete! ");
						if(rezHex.equals(shaDig)) {
							System.out.print("Digest of " + fileName + " matches expected digest.");
						}else {
							System.out.println("Digest of " + fileName +  " does not match the expected digest. Digest");
							System.out.println("was: " + rezHex);
						}
					}
				} catch(IOException ex) {
					ex.printStackTrace();
				} finally {
					if(is!=null) {
						try { is.close(); } catch(IOException ignorable) {}
					}
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}else {
			if(method.equals("encrypt")) {
				encrypt = true;
			}
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print("> ");
			String keyText = sc.nextLine();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print("> ");
			String ivText = sc.nextLine();
			String destinationFile = args[2];
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
			try {
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			InputStream is = null;
			OutputStream os = null;
			Path pi = Paths.get("C:\\Users\\Luka\\eclipse-workspace\\hw05-0036533686\\src\\main\\java\\hr\\fer\\oprpp1\\hw05\\crypto\\" + fileName);
			File f = new File("C:\\Users\\Luka\\eclipse-workspace\\hw05-0036533686\\src\\main\\java\\hr\\fer\\oprpp1\\hw05\\crypto\\" + destinationFile);
			Path po = f.toPath();
			try {
				is = Files.newInputStream(pi);
				os = Files.newOutputStream(po);
				byte[] buff = new byte[4096];
				int r = is.read(buff);
				while(r >= 0) {
					os.write(cipher.update(buff, 0, r));
					r = is.read(buff);
				}
				os.write(cipher.doFinal());
			    os.flush();
				if(encrypt) {
					System.out.println("Encryption completed. Generated file " + destinationFile + " based on file " + fileName);
				}else {
					System.out.println("Decryption completed. Generated file " + destinationFile + " based on file " + fileName);
				}
			} catch(IOException ex) {
				ex.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} finally {
				if(is!=null) {
					try { is.close(); os.close();} catch(IOException ignorable) {}
				}
			}
		}
		sc.close();
	}
}
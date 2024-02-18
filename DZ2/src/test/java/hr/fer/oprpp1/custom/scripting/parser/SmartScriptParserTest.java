package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmartScriptParserTest {
	@Test
	public void testPrimjer1() {
		String docBody = readExample(1);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			assertEquals(1, document.numberOfChildren());
			TextNode tekst = new TextNode("Ovo je \nsve jedan text node\n");
			
			assertEquals(tekst, document.getChild(0));
	}
	//@Disabled
	@Test
	public void testPrimjer2() {
		String docBody = readExample(2);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			assertEquals(1, document.numberOfChildren());
			TextNode tekst = new TextNode("Ovo je \nsve jedan {$ text node\n");
			
			assertEquals(tekst, document.getChild(0));
	}
	@Disabled
	@Test
	public void testPrimjer3() {
		String docBody = readExample(3);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			assertEquals(1, document.numberOfChildren());
			TextNode tekst = new TextNode("Ovo je \nsve jedan \\{$ text node\n");
			System.out.println(((TextNode)document.getChild(0)).getText());
			assertEquals(tekst, document.getChild(0));
	}
	@Disabled
	@Test
	public void testPrimjer6() {
		String docBody = readExample(6);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			System.out.println(((TextNode)document.getChild(0)).getText());
			System.out.println(document.getChild(1));
			System.out.println(((TextNode)document.getChild(2)).getText());
			assertEquals(3, document.numberOfChildren());
			TextNode tekst = new TextNode("Ovo je OK ");
			

			ElementString string = new ElementString("String ide\nu više redaka\nčak tri", true);
			Element[] elementi = new Element[]{string};
			EchoNode echo = new EchoNode(elementi);
			
			assertEquals(echo, document.getChild(1));
	}
	@Disabled
	@Test
	public void testPrimjer7() {
		String docBody = readExample(7);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			System.out.println(((TextNode)document.getChild(0)).getText());
			System.out.println(document.getChild(1));
			System.out.println(((TextNode)document.getChild(2)).getText());
			assertEquals(3, document.numberOfChildren());
			TextNode tekst = new TextNode("Ovo je OK ");
			
			ElementString string = new ElementString("String ide\nu \"više\" \nredaka\novdje a stvarno četiri", true);
			Element[] elementi = new Element[]{string};
			EchoNode echo = new EchoNode(elementi);
			
			assertEquals(echo, document.getChild(1));
	}
	
	@Disabled
	@Test
	public void testPrimjer8() {
		String docBody = readExample(8);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			System.out.println(((TextNode)document.getChild(0)).getText());
			System.out.println(document.getChild(1));
			System.out.println(((TextNode)document.getChild(2)).getText());
			assertEquals(3, document.numberOfChildren());
			TextNode tekst = new TextNode("Ovo je OK ");
			
			ElementString string = new ElementString("String ide\nu \"više\" \nredaka\novdje a stvarno četiri", true);
			Element[] elementi = new Element[]{string};
			EchoNode echo = new EchoNode(elementi);
			
			assertEquals(echo, document.getChild(1));
	}
	@Disabled
	@Test
	public void testPrimjer9() {
		String docBody = readExample(9);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			System.out.println(((TextNode)document.getChild(0)).getText());
			System.out.println(document.getChild(1));
			System.out.println(((TextNode)document.getChild(2)).getText());
			assertEquals(3, document.numberOfChildren());
			TextNode tekst = new TextNode("Ovo je OK ");
			
			ElementString string = new ElementString("String ide\nu \"više\" \nredaka\novdje a stvarno četiri", true);
			Element[] elementi = new Element[]{string};
			EchoNode echo = new EchoNode(elementi);
			
			assertEquals(echo, document.getChild(1));
	}
	
	@Test
	public void testPrimjerGlavni() {
		String docBody = readExample(9);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			assertEquals(4, document.numberOfChildren());
			TextNode tekstNode1 = new TextNode("This is sample text.\n");
			
			ElementVariable var1 = new ElementVariable("i");
			ElementConstantInteger i1 = new ElementConstantInteger(1);
			ElementConstantInteger i2 = new ElementConstantInteger(10);
			ElementConstantInteger i3 = new ElementConstantInteger(1);
			ForLoopNode forLoopNode1 = new ForLoopNode(var1, i1, i2, i3);
			
			TextNode tekst2 = new TextNode("\nThis is ");
			
			ElementVariable var2 = new ElementVariable("i");
			Element[] elementi = new Element[]{var2};
			EchoNode echo = new EchoNode(elementi);
			
			TextNode tekst3 = new TextNode("-th time this message is generated.\n");
			TextNode tekst4 = new TextNode("\n");
			
			ElementVariable var3 = new ElementVariable("i");
			ElementConstantInteger i4 = new ElementConstantInteger(0);
			ElementConstantInteger i5 = new ElementConstantInteger(10);
			ElementConstantInteger i6 = new ElementConstantInteger(2);
			ForLoopNode forLoopNode2 = new ForLoopNode(var3, i4, i5, i6);
			
			TextNode tekst5 = new TextNode("\nsin(");
			
			ElementVariable var4 = new ElementVariable("i");
			Element[] elementi2 = new Element[]{var4};
			EchoNode echo2 = new EchoNode(elementi2);
			
			TextNode tekst6 = new TextNode("^2) = ");
			
			ElementVariable var5 = new ElementVariable("i");
			ElementVariable var6 = new ElementVariable("i");
			ElementOperator oper = new ElementOperator("*");
			ElementFunction funk = new ElementFunction("@sin");
			ElementString stringUnut = new ElementString("0.000", true);
			ElementFunction funk2 = new ElementFunction("@decfmt");
			Element[] elementi3 = new Element[]{var5, var6, oper, funk, stringUnut, funk2};
			EchoNode echo3 = new EchoNode(elementi3);
			
			TextNode tekst7 = new TextNode("\n");
			
			assertEquals(tekstNode1, document.getChild(0));
			assertEquals(forLoopNode1, document.getChild(1));
			assertEquals(tekst2, document.getChild(1).getChild(0));
			assertEquals(forLoopNode2, document.getChild(3));
	}

	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
              return new String(data, StandardCharsets.UTF_8);
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
}
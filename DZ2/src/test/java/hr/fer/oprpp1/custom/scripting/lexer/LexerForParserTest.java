package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class LexerForParserTest {

	@Test
	public void testNotNull() {
		LexerForParser lexer = new LexerForParser("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new LexerForParser(null));
	}

	@Test
	public void testEmpty() {
		LexerForParser lexer = new LexerForParser("");
		
		assertEquals(LexTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		LexerForParser lexer = new LexerForParser("");
		
		LexToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		LexerForParser lexer = new LexerForParser("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptParserException.class, lexer::nextToken);
	}
	
	@Test
	public void testJustText() {
		LexerForParser lexer = new LexerForParser(readExample(1));
		LexToken actual = lexer.nextToken();
		LexToken expected = new LexToken(LexTokenType.TEKST, new ElementString(readExample(1), false));
		assertEquals(expected.getType(), actual.getType());
		assertEquals(expected.getValue().asText(), actual.getValue().asText());
	}
	
	//@Disabled
	@Test
	public void testIznimkaParsiranjeNepravilnogEsceapa() {
		LexerForParser lexer = new LexerForParser(readExample(8));
		LexToken actual = lexer.nextToken();
		// will obtain EOF
		// will throw!
		LexToken expected = new LexToken(LexTokenType.TEKST, new ElementString(readExample(8), false));
		assertEquals(expected.getType(), actual.getType());
		lexer.nextToken();
		assertThrows(SmartScriptParserException.class, lexer::nextToken);
	}
	
	@Test
	public void testForTag() {
		LexerForParser lexer = new LexerForParser("{$ FOR i 1 10 1 $}");
		LexToken actual1 = lexer.nextToken();
		LexToken actual2 = lexer.nextToken();
		LexToken actual3 = lexer.nextToken();
		LexToken actual4 = lexer.nextToken();
		LexToken actual5 = lexer.nextToken();
		LexToken expected1 = new LexToken(LexTokenType.TAGFOR, new ElementString("FOR", false));
		LexToken expected2 = new LexToken(LexTokenType.VARIABLA, new ElementVariable("i"));
		LexToken expected3 = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(1));
		LexToken expected4 = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(10));
		LexToken expected5 = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(1));
		
		assertEquals(expected1.getType(), actual1.getType());
		assertEquals(expected1.getValue().asText(), actual1.getValue().asText());
		
		
		assertEquals(expected2.getType(), actual2.getType());
		assertEquals(expected2.getValue().asText(), actual2.getValue().asText());
		
		
		assertEquals(expected3.getType(), actual3.getType());
		assertEquals(expected3.getValue().asText(), actual3.getValue().asText());
		
		
		assertEquals(expected4.getType(), actual4.getType());
		assertEquals(expected4.getValue().asText(), actual4.getValue().asText());
		
		
		assertEquals(expected5.getType(), actual5.getType());
		assertEquals(expected5.getValue().asText(), actual5.getValue().asText());
		
	}
	@Test
	public void testIzPrimjeraIzZadace() {
		LexerForParser lexer = 
				new LexerForParser
				("This is sample text.{$ FOR i 1.5 10 1 $}	This is {$= i $}-th time this message is generated.{$END$}{$FOR i 0 10 2 $}sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}{$END$}");
		//TEKST I FOR
		LexToken actual1 = lexer.nextToken();
		LexToken actual2 = lexer.nextToken();
		LexToken actual3 = lexer.nextToken();
		LexToken actual4 = lexer.nextToken();
		
		
		LexToken expected0 = new LexToken(LexTokenType.TEKST, new ElementString("This is sample text.", false));
		LexToken expected1 = new LexToken(LexTokenType.TAGFOR, new ElementString("FOR", false));
		LexToken expected2 = new LexToken(LexTokenType.VARIABLA, new ElementVariable("i"));
		LexToken expected3 = new LexToken(LexTokenType.NUMBER, new ElementConstantDouble(1.5));
		LexToken expected4 = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(10));
		LexToken expected5 = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(1));
		
		assertEquals(expected0.getType(), actual1.getType());
		assertEquals(expected0.getValue().asText(), actual1.getValue().asText());
		
		
		assertEquals(expected1.getType(), actual2.getType());
		assertEquals(expected1.getValue().asText(), actual2.getValue().asText());
		
		
		assertEquals(expected2.getType(), actual3.getType());
		assertEquals(expected2.getValue().asText(), actual3.getValue().asText());
		
		
		assertEquals(expected3.getType(), actual4.getType());
		assertEquals(expected3.getValue().asText(), actual4.getValue().asText());
		
		
		LexToken actual5 = lexer.nextToken();
		LexToken actual6 = lexer.nextToken();
		
		assertEquals(expected4.getType(), actual5.getType());
		assertEquals(expected4.getValue().asText(), actual5.getValue().asText());
		
		
		assertEquals(expected5.getType(), actual6.getType());
		assertEquals(expected5.getValue().asText(), actual6.getValue().asText());
		
		//TEKST DO ENDA I FORTAG
		LexToken actual7 = lexer.nextToken();
		LexToken actual8 = lexer.nextToken();
		LexToken actual9 = lexer.nextToken();
		LexToken actual10 = lexer.nextToken();
		LexToken actual11 = lexer.nextToken();
		LexToken actual12 = lexer.nextToken();
		
		LexToken expected6 = new LexToken(LexTokenType.TEKST, new ElementString("	This is ", false));
		LexToken expected7 = new LexToken(LexTokenType.TAGECHO, new ElementString("ECHO", false));
		LexToken expected8 = new LexToken(LexTokenType.VARIABLA, new ElementVariable("i"));
		LexToken expected9 = new LexToken(LexTokenType.TEKST, new ElementString("-th time this message is generated.", false));
		LexToken expected10 = new LexToken(LexTokenType.TAGEND, new ElementString("END", false));
		LexToken expected11 = new LexToken(LexTokenType.TAGFOR, new ElementString("FOR", false));
		
		assertEquals(expected6.getType(), actual7.getType());
		assertEquals(expected6.getValue().asText(), actual7.getValue().asText());
		
		
		assertEquals(expected7.getType(), actual8.getType());
		assertEquals(expected7.getValue().asText(), actual8.getValue().asText());
		
		
		assertEquals(expected8.getType(), actual9.getType());
		assertEquals(expected8.getValue().asText(), actual9.getValue().asText());
		
		
		assertEquals(expected9.getType(), actual10.getType());
		assertEquals(expected9.getValue().asText(), actual10.getValue().asText());
		
		
		assertEquals(expected10.getType(), actual11.getType());
		assertEquals(expected10.getValue().asText(), actual11.getValue().asText());
		
		
		assertEquals(expected11.getType(), actual12.getType());
		assertEquals(expected11.getValue().asText(), actual12.getValue().asText());
		
		
		//OD VARIJABLE i UKLJUČIVO S ECHO
		LexToken actual13 = lexer.nextToken();
		LexToken actual14 = lexer.nextToken();
		LexToken actual15 = lexer.nextToken();
		LexToken actual16 = lexer.nextToken();
		LexToken actual17 = lexer.nextToken();
		LexToken actual18 = lexer.nextToken();
		
		LexToken expected12 = new LexToken(LexTokenType.VARIABLA, new ElementVariable("i"));
		LexToken expected13 = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(0));
		LexToken expected14 = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(10));
		LexToken expected15 = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(2));
		LexToken expected16 = new LexToken(LexTokenType.TEKST, new ElementString("sin(", false));
		LexToken expected17 = new LexToken(LexTokenType.TAGECHO, new ElementString("ECHO", false));
		
		assertEquals(expected12.getType(), actual13.getType());
		assertEquals(expected12.getValue().asText(), actual13.getValue().asText());
		
		
		assertEquals(expected13.getType(), actual14.getType());
		assertEquals(expected13.getValue().asText(), actual14.getValue().asText());
		
		
		assertEquals(expected14.getType(), actual15.getType());
		assertEquals(expected14.getValue().asText(), actual15.getValue().asText());
		
		
		assertEquals(expected15.getType(), actual16.getType());
		assertEquals(expected15.getValue().asText(), actual16.getValue().asText());
		
		
		assertEquals(expected16.getType(), actual17.getType());
		assertEquals(expected16.getValue().asText(), actual17.getValue().asText());
		
		
		assertEquals(expected17.getType(), actual18.getType());
		assertEquals(expected17.getValue().asText(), actual18.getValue().asText());
		
		
		//VARIJABLA I PA UKLJUČIVO S *
		LexToken actual19 = lexer.nextToken();
		LexToken actual20 = lexer.nextToken();
		LexToken actual21 = lexer.nextToken();
		LexToken actual22 = lexer.nextToken();
		LexToken actual23 = lexer.nextToken();
		LexToken actual24 = lexer.nextToken();
		
		LexToken expected18 = new LexToken(LexTokenType.VARIABLA, new ElementVariable("i"));
		LexToken expected19 = new LexToken(LexTokenType.TEKST, new ElementString("^2) = ", false));
		LexToken expected20 = new LexToken(LexTokenType.TAGECHO, new ElementString("ECHO", false));
		LexToken expected21 = new LexToken(LexTokenType.VARIABLA, new ElementVariable("i"));
		LexToken expected22 = new LexToken(LexTokenType.VARIABLA, new ElementVariable("i"));
		LexToken expected23 = new LexToken(LexTokenType.OPERATOR, new ElementOperator("*"));
		
		assertEquals(expected18.getType(), actual19.getType());
		assertEquals(expected18.getValue().asText(), actual19.getValue().asText());
		
		
		assertEquals(expected19.getType(), actual20.getType());
		assertEquals(expected19.getValue().asText(), actual20.getValue().asText());
		
		
		assertEquals(expected20.getType(), actual21.getType());
		assertEquals(expected20.getValue().asText(), actual21.getValue().asText());
		
		
		assertEquals(expected21.getType(), actual22.getType());
		assertEquals(expected21.getValue().asText(), actual22.getValue().asText());
		
		
		assertEquals(expected22.getType(), actual23.getType());
		assertEquals(expected22.getValue().asText(), actual23.getValue().asText());
		
		
		assertEquals(expected23.getType(), actual24.getType());
		assertEquals(expected23.getValue().asText(), actual24.getValue().asText());
		
		//OD FUNKCIJE SIN DO KRAJA
		LexToken actual25 = lexer.nextToken();
		LexToken actual26 = lexer.nextToken();
		LexToken actual27 = lexer.nextToken();
		LexToken actual28 = lexer.nextToken();
		
		LexToken expected24 = new LexToken(LexTokenType.FUNKCIJA, new ElementFunction("@sin"));
		LexToken expected25 = new LexToken(LexTokenType.STRINGUTAGU, new ElementString("0.000", true));
		LexToken expected26 = new LexToken(LexTokenType.FUNKCIJA, new ElementFunction("@decfmt"));
		LexToken expected27 = new LexToken(LexTokenType.TAGEND, new ElementString("END", false));
		
		assertEquals(expected24.getValue().asText(), actual25.getValue().asText());
		
		assertEquals(expected24.getType(), actual25.getType());
		
		
		assertEquals(expected25.getType(), actual26.getType());
		assertEquals(expected25.getValue().asText(), actual26.getValue().asText());
		
		
		assertEquals(expected26.getType(), actual27.getType());
		assertEquals(expected26.getValue().asText(), actual27.getValue().asText());
		
		
		assertEquals(expected27.getType(), actual28.getType());
		assertEquals(expected27.getValue().asText(), actual28.getValue().asText());
		
		
		LexToken actual29 = lexer.nextToken();
		LexToken expected28 = new LexToken(LexTokenType.EOF, null);
		
		assertEquals(expected28.getType(), actual29.getType());
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
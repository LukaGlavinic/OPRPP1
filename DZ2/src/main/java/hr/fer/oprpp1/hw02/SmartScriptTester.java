package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;


public class SmartScriptTester {

	public static void main(String[] args) {
				
		String docBody = "{$= i i * @sin \"0.000\" @decfmt $}";
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
		String originalDocumentBody = document.toString();
		//System.out.println(originalDocumentBody); // should write something like original content of docBody
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		boolean same = document.equals(document2);
		System.out.println(same);
	}

}

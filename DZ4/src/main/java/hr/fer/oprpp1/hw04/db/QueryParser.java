package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {
	
	private boolean direktan;
	private int trenutniIndex;
	private final List<ConditionalExpression> listaUpita;

	public QueryParser(String upit) throws Exception {
		direktan = false;
		trenutniIndex = 0;
		listaUpita = new ArrayList<>();
		char[] poljeZnUpita = upit.toCharArray();
		ConditionalExpression prviUpit = nextExpression(poljeZnUpita);
		while(prviUpit != null) {
			listaUpita.add(prviUpit);
			prviUpit = nextExpression(poljeZnUpita);
		}
	}
	
	public boolean isDirectQuery() {
		if(listaUpita.size() == 1 && listaUpita.get(0).getFieldGetter() == FieldValueGetters.JMBAG && listaUpita.get(0).getComparisonOperator() == ComparisonOperators.EQUALS) {
			direktan = true;
		}
		return direktan;
	}
	public String getQueriedJMBAG() {
		if(!direktan) {
			throw new IllegalStateException("Upit nije bio direktan");
		}else {
			return listaUpita.get(0).getStringLiteral();
		}
	}
	public List<ConditionalExpression> getQuery() {
		return listaUpita;
	}
	
	public ConditionalExpression nextExpression(char[] polje) throws Exception {
		while(trenutniIndex < polje.length && (polje[trenutniIndex] == ' ' || polje[trenutniIndex] == '\t')) {//otkloni razmake i tabove
			trenutniIndex++;
		}
		if(trenutniIndex >= polje.length) {
			return null;
		}
		IFieldValueGetter varijabla;
		IComparisonOperator operator = null;
		StringBuilder literal;
		if(polje[trenutniIndex] == 'f' && polje[trenutniIndex+1] == 'i' && polje[trenutniIndex+2] == 'r' && polje[trenutniIndex+3] == 's' &&
				polje[trenutniIndex+4] == 't' && polje[trenutniIndex+5] == 'N' && polje[trenutniIndex+6] == 'a' && polje[trenutniIndex+7] == 'm' && polje[trenutniIndex+8] == 'e') {
			varijabla = FieldValueGetters.FIRST_NAME;
			trenutniIndex += 9;
		}else if(polje[trenutniIndex] == 'l' && polje[trenutniIndex+1] == 'a' && polje[trenutniIndex+2] == 's' && polje[trenutniIndex+3] == 't' &&
				polje[trenutniIndex+4] == 'N' && polje[trenutniIndex+5] == 'a' && polje[trenutniIndex+6] == 'm' && polje[trenutniIndex+7] == 'e') {
			varijabla = FieldValueGetters.LAST_NAME;
			trenutniIndex += 8;
		}else if(polje[trenutniIndex] == 'j' && polje[trenutniIndex+1] == 'm' && polje[trenutniIndex+2] == 'b' && polje[trenutniIndex+3] == 'a' &&
				polje[trenutniIndex+4] == 'g') {
			varijabla = FieldValueGetters.JMBAG;
			trenutniIndex += 5;
		}else {
			throw new Exception("Upit ne valja!");
		}
		while(trenutniIndex < polje.length && (polje[trenutniIndex] == ' ' || polje[trenutniIndex] == '\t')) {//otkloni razmake i tabove
			trenutniIndex++;
		}
		if(polje[trenutniIndex] == '=') {
			operator = ComparisonOperators.EQUALS;
			trenutniIndex++;
		}else if(polje[trenutniIndex] == '<') {
			if(polje[trenutniIndex+1] == '=') {
                trenutniIndex += 2;
			}
			operator = ComparisonOperators.LESS;
			trenutniIndex++;
		}else if(polje[trenutniIndex] == '>') {
			if(polje[trenutniIndex+1] == '=') {
                trenutniIndex += 2;
			}
			operator = ComparisonOperators.GREATER;
			trenutniIndex++;
		}else if(polje[trenutniIndex] == '!' && polje[trenutniIndex+1] == '=') {
			operator = ComparisonOperators.NOT_EQUALS;
			trenutniIndex += 2;
		}else if(polje[trenutniIndex] == 'L' && polje[trenutniIndex+1] == 'I' && polje[trenutniIndex+2] == 'K' && polje[trenutniIndex+3] == 'E') {
			operator = ComparisonOperators.LIKE;
			trenutniIndex += 4; 
		}
		while(trenutniIndex < polje.length && (polje[trenutniIndex] == ' ' || polje[trenutniIndex] == '\t')) {//otkloni razmake i tabove
			trenutniIndex++;
		}
		if(polje[trenutniIndex] == '"') {
			literal = new StringBuilder();
			trenutniIndex++;
			while(polje[trenutniIndex] != '"') {
				literal.append(polje[trenutniIndex]);
				trenutniIndex++;
			}
			trenutniIndex++;
		}else {
			throw new Exception("Upit ne valja!");
		}
		while(trenutniIndex < polje.length && (polje[trenutniIndex] == ' ' || polje[trenutniIndex] == '\t')) {//otkloni razmake i tabove
			trenutniIndex++;
		}
		ConditionalExpression izraz = new ConditionalExpression(varijabla, literal.toString(), operator);
		if(trenutniIndex < polje.length && trenutniIndex+3 < polje.length && Character.toLowerCase(polje[trenutniIndex]) == 'a' && Character.toLowerCase(polje[trenutniIndex+1]) == 'n' && Character.toLowerCase(polje[trenutniIndex+2]) == 'd') {
			trenutniIndex += 3;
		}
		return izraz;
	}
}
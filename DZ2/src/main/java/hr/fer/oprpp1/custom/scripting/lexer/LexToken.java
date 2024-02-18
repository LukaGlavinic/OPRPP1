package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class LexToken {
	
	private final LexTokenType type;
	private final Element value;
	
	public LexToken(LexTokenType type, Element value) {
		this.type = type;
		this.value = value;
	}

	public Element getValue() {
		return value;
	}
	
	public LexTokenType getType() {
		return type;
	}
}
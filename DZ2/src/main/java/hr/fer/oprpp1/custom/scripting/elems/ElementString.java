package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element{

	private String value;
	private boolean tag;
	
	public ElementString(String value, boolean tag) {
		super();
		this.value = value;
		this.tag = tag;
	}

	@Override
	public String asText() {
		 if(tag) {
		     return "\"" + this.value + "\"";
		 } else {
		     return this.value;
		 }
	}
}
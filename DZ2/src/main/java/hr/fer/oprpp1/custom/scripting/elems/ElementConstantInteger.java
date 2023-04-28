package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantInteger extends Element{

	private int value;
	
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		//TO DO
		if (this == obj) {
            return true;
        }
		if (obj instanceof ElementConstantInteger) {
			ElementConstantInteger el = (ElementConstantInteger)obj;
        	
            return value == el.value;
        }
        return false;
	}
}
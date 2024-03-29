package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element{

	private final double value;
	
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj instanceof ElementConstantDouble el) {

            return value == el.value;
        }
        return false;
	}
}
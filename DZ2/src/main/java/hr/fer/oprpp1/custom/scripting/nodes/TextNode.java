package hr.fer.oprpp1.custom.scripting.nodes;

public class TextNode extends Node{

	private final String text;
	
	public TextNode(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj instanceof TextNode node) {
            return text.equals(node.text);
        }
        return false;
	}
	
	@Override
    public String toString() {
       return this.getText();
    }
}
package hr.fer.oprpp1.custom.scripting.nodes;

public class TextNode extends Node{

	private String text;
	
	public TextNode(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
	
	@Override
	public boolean equals(Object obj) {
		//TO DO
		if (this == obj) {
            return true;
        }
        if (obj instanceof TextNode) {
        	TextNode node = (TextNode)obj;
        	return text.equals(node.text);
            
        }
        return false;
	}
	
	@Override
    public String toString() {
       return this.getText();
       //return this.text;
    }
}
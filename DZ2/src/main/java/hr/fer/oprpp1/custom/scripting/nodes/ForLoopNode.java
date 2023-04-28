package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node{

	private ElementVariable variable;
	
	private Element startExpression;
	
	private Element endExpression;
	
	private Element stepExpression;

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ElementVariable getVariable() {
		return this.variable;
	}

	public Element getStartExpression() {
		return this.startExpression;
	}

	public Element getEndExpression() {
		return this.endExpression;
	}

	public Element getStepExpression() {
		return this.stepExpression;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
            return true;
        }
        if (obj instanceof ForLoopNode) {
            ForLoopNode node = (ForLoopNode)obj;
        
            if(node.startExpression.equals(this.startExpression) &&
                    node.endExpression.equals(this.endExpression) && 
                    node.stepExpression.equals(this.stepExpression)&&
                    node.variable.equals(this.variable)) {
                return true;
            }
        }
        return false;
	}
	@Override
    public String toString() {
       String djeca = "{$for ";
       djeca += this.variable.asText() + " " + this.startExpression.asText() + " " + this.endExpression.asText() + " ";
       if(this.stepExpression != null) djeca += this.stepExpression.asText() + " ";
       djeca += "$}";
       djeca += super.toString();
       djeca += "{$end$}";
       return djeca;
    }
}
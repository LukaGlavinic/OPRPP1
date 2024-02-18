package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node{

	private final ElementVariable variable;
	
	private final Element startExpression;
	
	private final Element endExpression;
	
	private final Element stepExpression;

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
            return true;
        }
        if (obj instanceof ForLoopNode node) {

            return node.startExpression.equals(startExpression) &&
                    node.endExpression.equals(endExpression) &&
                    node.stepExpression.equals(stepExpression) &&
                    node.variable.equals(variable);
        }
        return false;
	}
	@Override
    public String toString() {
       String djeca = "{$for ";
       djeca += variable.asText() + " " + startExpression.asText() + " " + endExpression.asText() + " ";
       if(stepExpression != null) djeca += stepExpression.asText() + " ";
       djeca += "$}";
       djeca += super.toString();
       djeca += "{$end$}";
       return djeca;
    }
}
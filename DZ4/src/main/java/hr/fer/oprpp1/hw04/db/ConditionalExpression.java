package hr.fer.oprpp1.hw04.db;


public class ConditionalExpression {

	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	public ConditionalExpression(IFieldValueGetter varijabla, String literal, IComparisonOperator operator) {
		super();
		this.fieldGetter = varijabla;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
		
	}

	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
package hr.fer.oprpp1.hw04.db;


public class ConditionalExpression {

	private final IFieldValueGetter fieldGetter;
	private final String stringLiteral;
	private final IComparisonOperator comparisonOperator;
	
	public ConditionalExpression(IFieldValueGetter varijabla, String literal, IComparisonOperator operator) {
		super();
		fieldGetter = varijabla;
		stringLiteral = literal;
		comparisonOperator = operator;
		
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
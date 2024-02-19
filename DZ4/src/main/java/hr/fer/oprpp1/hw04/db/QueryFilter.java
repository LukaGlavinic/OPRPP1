package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter{
	
	List<ConditionalExpression> listaUpita;

	public QueryFilter(List<ConditionalExpression> upiti) {
		listaUpita = upiti;
	}

	@Override
	public boolean accepts(StudentRecord record) throws Exception {
		for(ConditionalExpression exp : listaUpita) {
			if(!exp.getComparisonOperator().satisfied(exp.getFieldGetter().get(record), exp.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}
}
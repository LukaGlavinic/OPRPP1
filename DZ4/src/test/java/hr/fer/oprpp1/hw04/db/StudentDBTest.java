package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentDBTest {
	@Test
	public void forJMBAGTest() throws Exception {
		List<String> listaStudenataString = Files.readAllLines(Paths.get("C:\\\\Users\\\\Luka\\\\eclipse-workspace\\\\hw04-0036533686\\\\src\\\\main\\\\java\\\\hr\\\\fer\\\\oprpp1\\\\hw04\\\\db\\\\database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(listaStudenataString);
		StudentRecord r = new StudentRecord("0000000007", "Èima", "Sanjin", 4);
		assertEquals(r, db.forJMBAG("0000000007"));
	}
	
	@Test
	public void fieldValueGetterTest() {
		StudentRecord r = new StudentRecord("0000000007", "Èima", "Sanjin", 4);
		IFieldValueGetter getterFirstName = FieldValueGetters.FIRST_NAME;
		assertEquals("Sanjin", getterFirstName.get(r));
		IFieldValueGetter getterFirstName2 = FieldValueGetters.LAST_NAME;
		assertEquals("Èima", getterFirstName2.get(r));
		IFieldValueGetter getterFirstName3 = FieldValueGetters.JMBAG;
		assertEquals("0000000007", getterFirstName3.get(r));
	}
	
	@Test
	public void conditionalExpressionTest() {
		IFieldValueGetter getterFirstName = FieldValueGetters.FIRST_NAME;
		String literal = "B*";
		IComparisonOperator operator = ComparisonOperators.LIKE;
		ConditionalExpression exp = new ConditionalExpression(getterFirstName, literal, operator);
		assertEquals(FieldValueGetters.FIRST_NAME, exp.getFieldGetter());
		assertEquals("B*", exp.getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, exp.getComparisonOperator());
	}
	
	@Test
	public void parserTest() {
		QueryParser qp1;
		try {
			qp1 = new QueryParser(" jmbag =\"0123456789\" ");
			assertTrue(qp1.isDirectQuery());
			assertEquals("0123456789", qp1.getQueriedJMBAG());
			assertEquals(1, qp1.getQuery().size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void filterTest() {
		StudentRecord r = new StudentRecord("0000000007", "Èima", "Sanjin", 4);
		IFieldValueGetter getterFirstName = FieldValueGetters.FIRST_NAME;
		String literal = "S*";
		IComparisonOperator operator = ComparisonOperators.LIKE;
		ConditionalExpression exp = new ConditionalExpression(getterFirstName, literal, operator);
		List<ConditionalExpression> l = new ArrayList<>();
		l.add(exp);
		QueryFilter filter = new QueryFilter(l);
		try {
			assertTrue(filter.accepts(r));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
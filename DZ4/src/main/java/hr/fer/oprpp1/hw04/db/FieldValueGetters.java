package hr.fer.oprpp1.hw04.db;

public class FieldValueGetters implements IFieldValueGetter{

	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
	
	public String get(StudentRecord record) {
		return null;
	}
}
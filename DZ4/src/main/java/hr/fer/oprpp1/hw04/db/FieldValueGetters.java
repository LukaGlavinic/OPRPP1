package hr.fer.oprpp1.hw04.db;

public class FieldValueGetters implements IFieldValueGetter{

	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
	
	public String get(StudentRecord record) {
		return null;
	}
}
package hr.fer.oprpp1.hw04.db;

@FunctionalInterface
public interface IFieldValueGetter {

	public String get(StudentRecord record);
}
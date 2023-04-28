package hr.fer.oprpp1.hw04.db;

@FunctionalInterface
public interface IFilter {

	public boolean accepts(StudentRecord record) throws Exception;
}
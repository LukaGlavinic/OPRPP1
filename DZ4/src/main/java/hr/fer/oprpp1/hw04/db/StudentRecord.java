package hr.fer.oprpp1.hw04.db;

public class StudentRecord {

	private String jmbag;
	private String firstName;
	private String lastName;
	private int grade;
	
	public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
		super();
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.grade = grade;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getGrade() {
		return grade;
	}

	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj instanceof StudentRecord) {
        	StudentRecord el = (StudentRecord)obj;
            return jmbag.equals(el.jmbag);
        }
        return false;
	}
}
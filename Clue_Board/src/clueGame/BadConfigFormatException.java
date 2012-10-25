package clueGame;

public class BadConfigFormatException extends Exception {
	private String name;
	
	public BadConfigFormatException(){}
	public BadConfigFormatException(String message) {
		super(message);
		name = message;
	}
	
	public String toString() {
		return "Invalid file: " + name;
	}

}

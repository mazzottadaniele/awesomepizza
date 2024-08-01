package it.adesso.awesomepizza.exceptions;

public class DataNotFoundException extends ServiceException{
	private static final long serialVersionUID = 5906053079230262354L;
	
	public DataNotFoundException(Exception e) {
		super(e);
	}
	
	public DataNotFoundException(String message) {
		super(message);
	}
}

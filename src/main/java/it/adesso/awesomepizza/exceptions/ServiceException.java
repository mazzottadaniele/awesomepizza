package it.adesso.awesomepizza.exceptions;

public class ServiceException extends RuntimeException{
	private static final long serialVersionUID = 3369426291108352972L;
	
	public ServiceException(Exception e) {
		super(e);
	}
	
	public ServiceException(String message) {
		super(message);
	}
}

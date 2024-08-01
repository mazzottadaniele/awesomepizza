package it.adesso.awesomepizza.data.dto;

public class EsitoDto {
	public EsitoDto(){
		
	}
	
	public EsitoDto(boolean success) {
		this.success = success;
	}
	
	public EsitoDto(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}
	
	private boolean success;
	private String errorMessage;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EsitoDto other = (EsitoDto) obj;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (success != other.success)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "EsitoDTOResponse [success=" + success + ", errorMessage=" + errorMessage + "]";
	}
}

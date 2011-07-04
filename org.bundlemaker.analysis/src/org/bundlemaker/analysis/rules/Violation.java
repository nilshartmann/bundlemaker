package org.bundlemaker.analysis.rules;

public class Violation {
	
	private final Severity severity;
	private final String message;
	
	
	public Violation(Severity severity, String message){
		this.severity = severity;
		this.message = message;
	}


	public Severity getSeverity() {
		return severity;
	}


	public String getMessage() {
		return message;
	}
	
	public String toString(){
		return "Severity: " + severity + ", Message: " + message;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((severity == null) ? 0 : severity.hashCode());
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
		Violation other = (Violation) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (severity != other.severity)
			return false;
		return true;
	}
	
	
	

}

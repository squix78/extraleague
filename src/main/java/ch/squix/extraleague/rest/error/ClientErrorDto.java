package ch.squix.extraleague.rest.error;

import lombok.Data;

@Data
public class ClientErrorDto {
	
	private String url;
	private String cause;
	private String message;
	private String userAgent;
	private String [] stackTrace;

}

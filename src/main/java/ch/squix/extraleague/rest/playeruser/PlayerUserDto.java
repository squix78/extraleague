package ch.squix.extraleague.rest.playeruser;

import lombok.Data;

@Data
public class PlayerUserDto {
	
	private String player;
	private String email;
	private String imageUrl;
	private Boolean emailNotification;
	private Boolean meetingPointNotification;
	private String appUserEmail;
	

}

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
	private String pushBulletApiKey;
	private Long secondsPlayedToday;
	private String loginUrl;
	private String logoutUrl;
	private boolean isLoggedIn;
	private String nickname;
	private String federatedIdentity;
	

}

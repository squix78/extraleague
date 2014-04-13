package ch.squix.extraleague.rest.playeruser;

public class PlayerUserDto {
	
	private String player;
	private String email;
	private String imageUrl;
	
	public String getPlayer() {
		return player;
	}
	public String getEmail() {
		return email;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}

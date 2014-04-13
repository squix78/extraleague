package ch.squix.extraleague.rest.playeruser;

import java.util.ArrayList;
import java.util.List;

public class PlayerUserListDto {
	
	private List<PlayerUserDto> playerUsers = new ArrayList<>();

	/**
	 * @return the playerUsers
	 */
	public List<PlayerUserDto> getPlayerUsers() {
		return playerUsers;
	}

	/**
	 * @param playerUsers the playerUsers to set
	 */
	public void setPlayerUsers(List<PlayerUserDto> playerUsers) {
		this.playerUsers = playerUsers;
	}

}

package ch.squix.extraleague.rest.challenger;

import ch.squix.extraleague.model.challenger.WinnerTeam;

public class WinnerTeamDtoMapper {
	
	public static WinnerTeamDto mapToDto(WinnerTeam team) {
		WinnerTeamDto dto = new WinnerTeamDto();
		dto.setGameMode(team.getGameMode());
		dto.setWinners(team.getWinners());
		dto.setTable(team.getTable());
		dto.setCreatedDate(team.getCreatedDate());
		return dto;
	}

}

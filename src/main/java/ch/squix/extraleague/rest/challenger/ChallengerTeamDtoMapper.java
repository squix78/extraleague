package ch.squix.extraleague.rest.challenger;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.challenger.ChallengerTeam;

public class ChallengerTeamDtoMapper {

	public static List<ChallengerTeamDto> mapToDtos(List<ChallengerTeam> teams) {
		List<ChallengerTeamDto> dtos = new ArrayList<>();
		for (ChallengerTeam team : teams) {
			dtos.add(mapToDto(team));
		}
		return dtos;
	}

	private static ChallengerTeamDto mapToDto(ChallengerTeam team) {
		ChallengerTeamDto dto = new ChallengerTeamDto();
		dto.setId(team.getId());
		dto.setCreatedDate(team.getCreatedDate());
		dto.setPlayers(team.getPlayers());
		dto.setTable(team.getTable());
		return dto;
	}

}

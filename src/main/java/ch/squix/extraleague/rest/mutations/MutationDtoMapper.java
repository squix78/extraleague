package ch.squix.extraleague.rest.mutations;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.mutations.PlayerMutation;

public class MutationDtoMapper {
	
	public static List<PlayerMutationDto> mapToDtos(List<PlayerMutation> mutations) {
		List<PlayerMutationDto> dtos = new ArrayList<>();
		for (PlayerMutation mutation : mutations) {
				dtos.add(mapToDto(mutation));
		}
		return dtos;
	}

	private static PlayerMutationDto mapToDto(PlayerMutation mutation) {
		PlayerMutationDto dto = new PlayerMutationDto();
		dto.setPlayers(mutation.getPlayers());
		dto.getDescriptions().addAll(mutation.getDescriptions());
		dto.setCreatedDate(mutation.getCreatedDate());
		dto.setPlayerScores(mutation.getPlayerScores());
		return dto;
	}

}

package ch.squix.extraleague.rest.mutations;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.mutations.Mutations;
import ch.squix.extraleague.model.mutations.PlayerMutation;

public class MutationDtoMapper {
	
	public static List<PlayerMutationDto> mapToDtos(Mutations mutations) {
		List<PlayerMutationDto> dtos = new ArrayList<>();
		for (PlayerMutation mutation : mutations.getPlayerMutations()) {
			dtos.add(mapToDto(mutation));
		}
		return dtos;
	}

	private static PlayerMutationDto mapToDto(PlayerMutation mutation) {
		PlayerMutationDto dto = new PlayerMutationDto();
		dto.setPlayer(mutation.getPlayer());
		dto.setValue(mutation.getValue());
		dto.setText(mutation.getDescription());
		dto.setCreatedDate(mutation.getCreatedDate());
		return dto;
	}

}

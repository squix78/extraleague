package ch.squix.extraleague.rest.mutations;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.mutations.BadgeMutation;
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
		dto.setWonBadges(mapBadgeMutations(mutation.getWonBadges()));
		dto.setLostBadges(mapBadgeMutations(mutation.getLostBadges()));
		return dto;
	}

	private static List<BadgeMutationDto> mapBadgeMutations(List<BadgeMutation> mutations) {
		if (mutations == null) {
			return new ArrayList<>();
		}
		List<BadgeMutationDto> dtos = new ArrayList<>();
		for (BadgeMutation badgeMutation : mutations) {
			BadgeMutationDto badgeDto = new BadgeMutationDto();
			badgeDto.setPlayer(badgeMutation.getPlayer());
			badgeDto.setBadges(badgeMutation.getBadges());
			dtos.add(badgeDto);
		}
		return dtos;
	}

}

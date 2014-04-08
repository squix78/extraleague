package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.match.PlayerCombo;

public class PlayerComboDtoMapper {
	
	public static List<PlayerComboDto> mapToDtos(List<PlayerCombo> combos) {
		List<PlayerComboDto> dtos = new ArrayList<>();
		if (combos != null) {
			for (PlayerCombo combo : combos) {
				dtos.add(mapToDto(combo));
			}
		}
		return dtos;
	}
	
	public static PlayerComboDto mapToDto(PlayerCombo combo) {
		PlayerComboDto dto = new PlayerComboDto();
		dto.setPlayer(combo.getPlayer());
		dto.setCombo(combo.getCombo());
		dto.setGamesWon(combo.getGamesWon());
		dto.setGamesLost(combo.getGamesLost());
		return dto;
	}

}

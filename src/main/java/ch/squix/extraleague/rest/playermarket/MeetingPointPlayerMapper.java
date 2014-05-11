package ch.squix.extraleague.rest.playermarket;

import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.playermarket.MeetingPointPlayer;

public class MeetingPointPlayerMapper {
	
	public static MeetingPointPlayerDto mapToDto(MeetingPointPlayer player) {
		MeetingPointPlayerDto dto = new MeetingPointPlayerDto();
		dto.setId(player.getId());
		dto.setPlayer(player.getPlayer());
		dto.setTable(player.getTable());
		dto.setAvailableUntil(player.getAvailableUntil());
		return dto;
	}
	
	public static List<MeetingPointPlayerDto> mapToDtos(List<MeetingPointPlayer> players) {
		List<MeetingPointPlayerDto> playerDtos = new ArrayList<>();
		for (MeetingPointPlayer player : players) {
			playerDtos.add(mapToDto(player));
		}
		return playerDtos;
	}
	
	public static MeetingPointPlayer mapFromDto(MeetingPointPlayerDto playerDto) {
		MeetingPointPlayer player = new MeetingPointPlayer();
		player.setPlayer(playerDto.getPlayer());
		player.setTable(playerDto.getTable());
		player.setAvailableUntil(playerDto.getAvailableUntil());
		return player;
	}

}

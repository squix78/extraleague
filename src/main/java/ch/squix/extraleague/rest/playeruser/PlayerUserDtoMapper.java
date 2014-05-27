package ch.squix.extraleague.rest.playeruser;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.match.player.PlayerUser;

public class PlayerUserDtoMapper {
	
	public static PlayerUserDto mapToDto(PlayerUser playerUser) {
		PlayerUserDto dto = new PlayerUserDto();
		dto.setPlayer(playerUser.getPlayer());
		dto.setEmail(playerUser.getEmail());
		dto.setAppUserEmail(playerUser.getAppUserEmail());
		dto.setImageUrl(playerUser.getImageUrl());
		dto.setEmailNotification(playerUser.getEmailNotification());
		dto.setMeetingPointNotification(playerUser.getMeetingPointNotification());

		return dto;
	}
	
	public static PlayerUserDto mapToAdminDto(PlayerUser playerUser) {
		PlayerUserDto dto = mapToDto(playerUser);
		dto.setEmail(playerUser.getEmail());

		dto.setEmailNotification(playerUser.getEmailNotification());
		return dto;
	}
	
	public static List<PlayerUserDto> mapToDtos(List<PlayerUser> playerUsers) {
		List<PlayerUserDto> dtos = new ArrayList<>();
		for (PlayerUser playerUser : playerUsers) {
			dtos.add(mapToDto(playerUser));
		}
		return dtos;
	}
	
	public static List<PlayerUserDto> mapToAdminDtos(List<PlayerUser> playerUsers) {
		List<PlayerUserDto> dtos = new ArrayList<>();
		for (PlayerUser playerUser : playerUsers) {
			dtos.add(mapToAdminDto(playerUser));
		}
		return dtos;
	}
	
	public static PlayerUser mapFromAdminDto(PlayerUserDto dto) {
		PlayerUser playerUser = new PlayerUser();
		playerUser.setPlayer(dto.getPlayer());
		playerUser.setEmail(dto.getEmail());
		playerUser.setImageUrl(dto.getImageUrl());
		playerUser.setEmailNotification(dto.getEmailNotification());
		return playerUser;
	}
	
	public static void savePlayerUser(PlayerUserDto playerUserDto, String player) {
		PlayerUser playerUser = ofy().load().type(PlayerUser.class).filter("player == ", player).first().now();
		if (playerUser == null) {
			playerUser = new PlayerUser();
			playerUser.setPlayer(player);
		}
		playerUser.setEmail(playerUserDto.getEmail());
		playerUser.setImageUrl(playerUserDto.getImageUrl());
		playerUser.setEmailNotification(playerUserDto.getEmailNotification());
		playerUser.setMeetingPointNotification(playerUserDto.getMeetingPointNotification());
		ofy().save().entities(playerUser).now();
	}


}

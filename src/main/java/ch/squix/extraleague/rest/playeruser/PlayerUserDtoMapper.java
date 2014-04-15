package ch.squix.extraleague.rest.playeruser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.match.player.PlayerUser;

public class PlayerUserDtoMapper {
	
	private final static String MAPPING_SERVICE_URL = "/playerImage?url=";
	
	public static PlayerUserDto mapToDto(PlayerUser playerUser) {
		PlayerUserDto dto = new PlayerUserDto();
		dto.setPlayer(playerUser.getPlayer());
		dto.setEmail(playerUser.getEmail());
		String imageUrl = playerUser.getImageUrl();
		if (imageUrl != null) {
			try {
				String imageUrlEncoded = URLEncoder.encode(imageUrl, "utf-8");
				dto.setImageUrl(MAPPING_SERVICE_URL + imageUrlEncoded);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
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


}

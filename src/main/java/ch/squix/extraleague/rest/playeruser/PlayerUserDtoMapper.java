package ch.squix.extraleague.rest.playeruser;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.rest.user.CurrentUserResource;

import com.google.appengine.api.users.User;

public class PlayerUserDtoMapper {
	
	private static final Logger log = Logger.getLogger(PlayerUserDtoMapper.class.getName());
	
	public static PlayerUserDto mapToDto(PlayerUser playerUser) {
		PlayerUserDto dto = new PlayerUserDto();
		dto.setPlayer(playerUser.getPlayer());
		dto.setEmail(playerUser.getEmail());
		dto.setAppUserEmail(playerUser.getAppUserEmail());
		dto.setImageUrl(playerUser.getImageUrl());
		dto.setEmailNotification(playerUser.getEmailNotification());
		dto.setMeetingPointNotification(playerUser.getMeetingPointNotification());
		dto.setPushBulletApiKey(playerUser.getPushBulletApiKey());
		dto.setPlayerClaimed(dto.getPlayer() != null);
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
		dto.setMeetingPointNotification(playerUser.getMeetingPointNotification());
		dto.setPushBulletApiKey(playerUser.getPushBulletApiKey());
		return playerUser;
	}
	
	public static PlayerUserDto savePlayerUser(PlayerUserDto playerUserDto, User currentUser) {
		// Cases:
		// 1. Save existing user: no user loaded with currentUser.id
		// 2. Claiming new user: nobody else claimed this player
		PlayerUser playerUser = ofy().load().type(PlayerUser.class).filter("appUserId = ", currentUser.getUserId()).first().now();
		if (playerUser == null) {
			playerUser = ofy().load().type(PlayerUser.class).filter("player == ", playerUserDto.getPlayer()).first().now();
			if (playerUser != null) {
				log.info("User already claimed!");
				return null;
			}
			playerUser = new PlayerUser();
			playerUser.setPlayer(playerUserDto.getPlayer());
		}
		playerUser.setAppUserId(currentUser.getUserId());
		playerUser.setAppUserEmail(currentUser.getEmail());
		playerUser.setEmail(playerUserDto.getEmail());
		playerUser.setImageUrl(playerUserDto.getImageUrl());
		playerUser.setEmailNotification(playerUserDto.getEmailNotification());
		playerUser.setMeetingPointNotification(playerUserDto.getMeetingPointNotification());
		playerUser.setPushBulletApiKey(playerUserDto.getPushBulletApiKey());
		ofy().save().entities(playerUser).now();
		return mapToDto(playerUser);
	}
	
	public static void savePlayerUserByAdmin(PlayerUserDto playerUserDto) {
		// Cases:
		// 1. Save existing user: no user loaded with currentUser.id
		// 2. Claiming new user: nobody else claimed this player
		PlayerUser playerUser = ofy().load().type(PlayerUser.class).filter("player = ", playerUserDto.getPlayer()).first().now();
		if (playerUser == null) {
			playerUser = new PlayerUser();
			playerUser.setPlayer(playerUserDto.getPlayer());
		}
		playerUser.setEmail(playerUserDto.getEmail());
		playerUser.setImageUrl(playerUserDto.getImageUrl());
		playerUser.setEmailNotification(playerUserDto.getEmailNotification());
		playerUser.setMeetingPointNotification(playerUserDto.getMeetingPointNotification());
		playerUser.setPushBulletApiKey(playerUserDto.getPushBulletApiKey());
		ofy().save().entities(playerUser).now();
	}


}

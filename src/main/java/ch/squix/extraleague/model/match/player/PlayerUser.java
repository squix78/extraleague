package ch.squix.extraleague.model.match.player;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
@Data
public class PlayerUser {

		@Id 
		private Long id;

		@Index
		private String player;
		
		@Index
		private String email;
		
		private String imageUrl;
		
		private Boolean emailNotification = true;

}

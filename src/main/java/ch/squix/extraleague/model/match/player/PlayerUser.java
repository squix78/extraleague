package ch.squix.extraleague.model.match.player;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class PlayerUser {

		@Id 
		private Long id;

		@Index
		private String player;
		
		@Index
		private String email;
		
		private String imageUrl;
		
		private Boolean emailNotification = true;

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * @return the player
		 */
		public String getPlayer() {
			return player;
		}

		/**
		 * @param player the player to set
		 */
		public void setPlayer(String player) {
			this.player = player;
		}

		/**
		 * @return the imageUrl
		 */
		public String getImageUrl() {
			return imageUrl;
		}

		/**
		 * @param imageUrl the imageUrl to set
		 */
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		public Boolean getEmailNotification() {
			return emailNotification;
		}

		public void setEmailNotification(Boolean emailNotification) {
			this.emailNotification = emailNotification;
		}
}

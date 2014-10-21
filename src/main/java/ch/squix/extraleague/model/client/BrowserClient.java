package ch.squix.extraleague.model.client;

import java.util.Date;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;

@Entity
@Cache
@Data
public class BrowserClient {
	

	@Id
	private Long id;

	@Index
	private String token;
	
	@Index
	private Long clientId;
	
	@Index
	private Date createdDate;
	
	@OnSave
	private void setCreatedDate() {
		if (createdDate == null) {
			createdDate = new Date();
		}
	}



}

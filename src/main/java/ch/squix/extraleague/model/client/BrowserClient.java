package ch.squix.extraleague.model.client;

import java.util.Date;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;

@Entity
@Cache
public class BrowserClient {
	

	@Id
	private Long id;

	private String token;
	
	@Index
	private Date createdDate;
	
	@OnSave
	private void setCreatedDate() {
		if (createdDate == null) {
			createdDate = new Date();
		}
	}
	
	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


}

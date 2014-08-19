package ch.squix.extraleague.model.league;

import java.util.Map;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.EmbedMap;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Data
@Cache
@Entity
public class League {
	
	@Id
	private Long id;
	
	@Index
	private String name;
	
	@Index
	private String domain;
	
	private String webhookUrl;
	
	@EmbedMap
	private Map<String, String> requestHeaders;

}

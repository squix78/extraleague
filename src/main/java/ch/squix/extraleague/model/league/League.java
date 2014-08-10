package ch.squix.extraleague.model.league;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
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

}

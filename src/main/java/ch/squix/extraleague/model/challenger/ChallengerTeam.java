package ch.squix.extraleague.model.challenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
@Data
public class ChallengerTeam {
	
	@Id
	private Long id;
	
	@Index
	private Date createdDate;
	
	private List<String> challengers = new ArrayList<>();
	
	@Index
	private String table;
	

}

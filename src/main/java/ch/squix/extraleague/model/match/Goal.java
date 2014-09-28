package ch.squix.extraleague.model.match;

import java.util.Date;

import com.googlecode.objectify.annotation.Embed;

import lombok.Data;

@Data
@Embed
public class Goal {
	
	private String scorer;
	private Date time;

}

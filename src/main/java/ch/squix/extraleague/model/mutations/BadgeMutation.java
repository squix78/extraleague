package ch.squix.extraleague.model.mutations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Embed;

import lombok.Data;

@Data
public class BadgeMutation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String player;
	private List<String> badges = new ArrayList<>();

}

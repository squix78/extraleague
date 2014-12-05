package ch.squix.extraleague.rest.mutations;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BadgeMutationDto {
	
	private String player;
	private List<String> badges = new ArrayList<>();

}

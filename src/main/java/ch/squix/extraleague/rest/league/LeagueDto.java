package ch.squix.extraleague.rest.league;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LeagueDto {

	private String name;

	private String domain;
	
	private String webhookUrl;

	private String requestHeaders;
	
	private List<String> tables = new ArrayList<>();

}

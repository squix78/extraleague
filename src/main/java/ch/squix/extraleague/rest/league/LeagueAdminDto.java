package ch.squix.extraleague.rest.league;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LeagueAdminDto {

	private String name;

	private String domain;
	
	private String webhookUrl;

	private String requestHeaders;
	
	private String leagueCss;
	
	private String logoUrl;
	
	private List<String> tables = new ArrayList<>();

}

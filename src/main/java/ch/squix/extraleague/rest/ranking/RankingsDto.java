package ch.squix.extraleague.rest.ranking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RankingsDto {
	
	private List<RankingDto> rankings = new ArrayList<>();
	private Date createdDate;
	
	public RankingsDto() {
		
	}
	
	public RankingsDto(List<RankingDto> rankings, Date createdDate) {
		this.rankings = rankings;
		this.createdDate = createdDate;
	}


}

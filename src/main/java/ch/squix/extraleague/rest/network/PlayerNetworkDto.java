package ch.squix.extraleague.rest.network;

import java.util.ArrayList;
import java.util.List;

public class PlayerNetworkDto {
	
	private String name;
	private List<PartnerDto> partners = new ArrayList<>();
	private Integer ranking;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PartnerDto> getPartners() {
		return partners;
	}
	public void setPartners(List<PartnerDto> partners) {
		this.partners = partners;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	
	

}

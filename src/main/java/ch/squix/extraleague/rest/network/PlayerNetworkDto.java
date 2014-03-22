package ch.squix.extraleague.rest.network;

import java.util.HashSet;
import java.util.Set;

public class PlayerNetworkDto {
	
	private String name;
	private Set<String> partners = new HashSet<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<String> getPartners() {
		return partners;
	}
	public void setPartners(Set<String> partners) {
		this.partners = partners;
	}
	
	

}

package ch.squix.extraleague.rest.specialevents;

import lombok.Data;

@Data
public class EventGroupDto {
	private String name;
	private String buttonClass;
	
	public EventGroupDto(String name, String buttonClass) {
		this.name = name;
		this.buttonClass = buttonClass;
	}
}

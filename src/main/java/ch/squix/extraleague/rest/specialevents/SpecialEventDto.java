package ch.squix.extraleague.rest.specialevents;

import lombok.Data;

@Data
public class SpecialEventDto {
	private String name;
	private String eventGroup;
	private String description;
	private String iconClass;
	
	public SpecialEventDto(String name, String eventGroup, String description, String iconClass) {
		this.name = name;
		this.eventGroup = eventGroup;
		this.description = description;
		this.iconClass = iconClass;
	}

}

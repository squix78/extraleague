package ch.squix.extraleague.rest.badges;

import lombok.Data;

@Data
public class BadgeEnumDto {
	
	private String name;
	private String badgeType;
	private String faClass;
	private Integer index;
	private String description;
	private Integer badgeCount;
	private String jsRegex;
	private Integer achievementPoints;

	

}

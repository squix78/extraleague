package ch.squix.extraleague.rest.badges;

public class BadgeDto {
	
	private String name;
	private String badgeType;
	private String faClass;
	private Integer index;
	private String description;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the badgeType
	 */
	public String getBadgeType() {
		return badgeType;
	}
	/**
	 * @param badgeType the badgeType to set
	 */
	public void setBadgeType(String badgeType) {
		this.badgeType = badgeType;
	}
	/**
	 * @return the faClass
	 */
	public String getFaClass() {
		return faClass;
	}
	/**
	 * @param faClass the faClass to set
	 */
	public void setFaClass(String faClass) {
		this.faClass = faClass;
	}
	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	

}

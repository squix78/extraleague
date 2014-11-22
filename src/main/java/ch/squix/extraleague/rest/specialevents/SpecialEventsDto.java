package ch.squix.extraleague.rest.specialevents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SpecialEventsDto {
	
	private List<EventGroupDto> eventGroups = new ArrayList<>();
	private Map<String, List<SpecialEventDto>> groupEventMap = new HashMap<>();
	private Map<String, SpecialEventDto> eventMap = new HashMap<>();
}

package ch.squix.extraleague.rest.specialevents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;



public class SpecialEventResource extends ServerResource {
	
	//private static final Logger log = Logger.getLogger(SpecialEventResource.class.getName());
	
	@Get(value = "json")
	public SpecialEventsDto execute() {
		SpecialEventsDto dto = new SpecialEventsDto();
		Map<String, List<SpecialEventDto>> map = new HashMap<>();
		for (EventGroup group : EventGroup.values()) {
			dto.getEventGroups().add(new EventGroupDto(group.name(), group.getButtonClass()));
			map.put(group.name(), new ArrayList<SpecialEventDto>());
		}
		Map<String, SpecialEventDto> eventMap = new HashMap<>();
		for (SpecialEvent event : SpecialEvent.values()) {
			List<SpecialEventDto> eventDtos = map.get(event.getEventGroup().name());
			SpecialEventDto eventDto = new SpecialEventDto(event.name(), event.getEventGroup().name(), event.getDescription(), event.getIconClass());
			eventDtos.add(eventDto);
			eventMap.put(event.name(), eventDto);
		}
		dto.setGroupEventMap(map);
		dto.setEventMap(eventMap);
		return dto;
	}



}

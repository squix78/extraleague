package ch.squix.extraleague.rest.ranking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ch.squix.extraleague.model.ranking.badge.Badge;

public class BadgeDtoMapper {
	
	public static List<BadgeDto> mapToDto(List<Badge> badges) {
		List<BadgeDto> dtos = new ArrayList<>();
		Long id = 0L;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (badges != null) {
			for (Badge badge : badges) {
				BadgeDto dto = new BadgeDto();
				dto.setContent(badge.getName());
				dto.setId(id);
				dto.setStart(format.format(badge.getEarnedDate()));
				dtos.add(dto);
				id++;
			}
		}
		return dtos;
		
	}

}

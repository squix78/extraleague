package ch.squix.extraleague.model.mutations;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
@Cache
@Data
public class Mutations {

	@Id
	private Long id;
	
	@Serialize(zip=true)
	private List<PlayerMutation> playerMutations = new ArrayList<>();

}

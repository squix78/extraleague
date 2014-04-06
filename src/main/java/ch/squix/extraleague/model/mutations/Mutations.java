package ch.squix.extraleague.model.mutations;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
@Cache
public class Mutations {

	@Id
	private Long id;
	
	@Serialize(zip=true)
	private List<PlayerMutation> playerMutations = new ArrayList<>();

	public List<PlayerMutation> getPlayerMutations() {
		return playerMutations;
	}

	public void setPlayerMutations(List<PlayerMutation> playerMutations) {
		this.playerMutations = playerMutations;
	}

}

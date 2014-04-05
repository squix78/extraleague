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
	private List<MutationTuple> mutationTuples = new ArrayList<>();

	public List<MutationTuple> getMutationTuples() {
		return mutationTuples;
	}

	public void setMutationTuples(List<MutationTuple> mutationTuples) {
		this.mutationTuples = mutationTuples;
	}

}

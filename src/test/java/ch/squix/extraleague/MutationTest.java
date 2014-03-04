package ch.squix.extraleague;

import org.junit.Test;


public class MutationTest {
    
    @Test
    public void testMutations() {
        Integer [][] mutations = {{0,1,2,3}, {3,0,1,2}, {2,3,1,0}, {0,2,3,1}};
        String [] players = {"a", "b", "c", "d"};
        for (int i=0; i < 4; i++) {
            Integer [] mutation = mutations[i];
            for (int j = 0; j < 4; j++) {
                System.out.print(players[mutation[j]] + ", ");
            }
            System.out.println();
        }
    }

}

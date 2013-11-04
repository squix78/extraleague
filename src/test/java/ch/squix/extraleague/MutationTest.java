/*
 * Copyright (C) 2013 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of Netcetera AG, Switzerland.
 * The program(s) may be used and/or copied only with the written permission of Netcetera AG or
 * in accordance with the terms and conditions stipulated in the agreement/contract under which 
 * the program(s) have been supplied.
 */
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

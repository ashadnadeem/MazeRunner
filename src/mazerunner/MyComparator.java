/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazerunner;

import java.util.Comparator;

/**
 *
 * @author Dell
 */
class MyComparator implements Comparator<player> {

    public MyComparator() {
    }

    @Override
    public int compare(player o1, player o2) {
        return o2.successRate.intValue() - o1.successRate.intValue();
    }
    
}

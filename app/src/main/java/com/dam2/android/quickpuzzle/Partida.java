
package com.dam2.android.quickpuzzle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by newdarkworld on 30/01/17.
 */

public final class Partida {
    private static Map<Integer, Integer> positions;

    public static Partida getInstance(int columns){
        Partida partida = new Partida(columns);
        return partida;
    }

    private Partida(){}

    private Partida(int columns){
       // positions = new Map<Integer, Integer>();
        for(int i=0;i<columns*columns;i++){

            positions.put(i, null);
        }
    }

    public static boolean setPosition(int idCasella,int posNova ){
        //positions.replace(idCasella,positions.)
       return true;
    }
}

package com.dam2.android.quickpuzzle;

/**
 * Created by newdarkworld on 30/01/17.
 */

public final class Partida {
    private Integer[] positions;

    public static Partida getInstance(int columns){
        Partida partida = new Partida(columns);
        return partida;
    }

    private Partida(){}

    private Partida(int columns){
        positions = new Integer[columns*columns]; // Nombre de posicions creades
    }

}

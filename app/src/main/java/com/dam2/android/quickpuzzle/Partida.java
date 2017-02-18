
package com.dam2.android.quickpuzzle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by newdarkworld on 30/01/17.
 */

public final class Partida {
   private static int numintents=0;
    private static int puntuacio=0;
    public static int getPuntuacio() {
        return puntuacio;
    }

    public static int getNumintents() {
        return numintents;
    }

    public static void setNumintents(int numintents) {
        Partida.numintents = numintents;
    }
    public static void incrementarIntents(){numintents++;}
    public static void setPuntuacio(int puntuacio) {
        Partida.puntuacio = puntuacio;
    }

    public static void sumarpuntuacio(int increment){

        puntuacio=puntuacio+(increment/numintents)+(increment/4);
        GameFragment.SetTextViewPuntuacio( puntuacio );
    }
}

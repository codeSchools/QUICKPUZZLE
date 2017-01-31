package com.dam2.android.quickpuzzle;

import android.graphics.Bitmap;

/**
 * Created by oriol on 31/01/17.
 */

public class ImatgeBitmapPosition {
    private Bitmap imatge;
    private int posisioCorrecte;

    public ImatgeBitmapPosition(Bitmap imatge, int posisioCorrecte) {
        this.imatge = imatge;
        this.posisioCorrecte = posisioCorrecte;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }

    public int getPosisioCorrecte() {
        return posisioCorrecte;
    }

    public void setPosisioCorrecte(int posisioCorrecte) {
        this.posisioCorrecte = posisioCorrecte;
    }
}

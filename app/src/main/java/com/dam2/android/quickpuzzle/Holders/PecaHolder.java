package com.dam2.android.quickpuzzle.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by oriol on 28/01/17.
 */

public abstract class PecaHolder extends RecyclerView.ViewHolder{

    public ImageView mImage;
    public RelativeLayout rel;
    public int numItem;
    public PecaHolder(LayoutInflater inflater, int resource, ViewGroup container){
        super(inflater.inflate(resource, container,false));}

    public ImageView getmImage() {
        return mImage;
    }

    public void setmImage(ImageView mImage) {
        this.mImage = mImage;
    }

    public RelativeLayout getRel() {
        return rel;
    }

    public void setRel(RelativeLayout rel) {
        this.rel = rel;
    }

    public int getNumItem() {
        return numItem;
    }

    public void setNumItem(int numItem) {
        this.numItem = numItem;
    }
}
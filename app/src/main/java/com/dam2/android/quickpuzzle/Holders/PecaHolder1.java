package com.dam2.android.quickpuzzle.Holders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dam2.android.quickpuzzle.Holders.Listeners.MyDragListener;
import com.dam2.android.quickpuzzle.Holders.Listeners.MyTouchListener;
import com.dam2.android.quickpuzzle.R;

/**
 * Created by oriol on 28/01/17.
 */

public class PecaHolder1 extends PecaHolder {
    public boolean possibledrop =true;
    public ImageView mImage;
    public RelativeLayout rel;
    public PecaHolder1(LayoutInflater inflater, ViewGroup container){
        super(inflater, R.layout.list_item,container);
        rel = (RelativeLayout) itemView.findViewById(R.id.containerpiecepuzzle);
        mImage=(ImageView) rel.findViewById( R.id.list_item_image);
        mImage.setOnTouchListener(new MyTouchListener());
    }
}
package com.dam2.android.quickpuzzle.Holders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dam2.android.quickpuzzle.Holders.Listeners.MyDragListener;
import com.dam2.android.quickpuzzle.Holders.Listeners.MyTouchListener;
import com.dam2.android.quickpuzzle.R;

/**
 * Created by oriol on 28/01/17.
 */
public class PecaHolder2 extends PecaHolder {

    public boolean possibledrop =true;
     public RelativeLayout rel;

    public PecaHolder2(LayoutInflater inflater, ViewGroup container) {

        super(inflater, R.layout.list_item2,container);
        rel = (RelativeLayout) itemView.findViewById( R.id.containerpiecepuzzle2 );
        rel.setOnDragListener( new MyDragListener() );

    }
}
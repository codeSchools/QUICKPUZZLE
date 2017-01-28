package com.dam2.android.quickpuzzle.Holders.Listeners;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by oriol on 28/01/17.
 */

public class MyDragListener implements View.OnDragListener {
    Context context;
    //Drawable enterShape = context.getResources().getDrawable(R.drawable.shape2);
    //  Drawable normalShape = context.getResources().getDrawable(R.drawable.shape);
//MyDragListener(Context context){this.context=context;}
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //  v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                //  v.setBackgroundDrawable(normalShape);
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                //v.setX(event.getX());
                // v.setY(event.getY());

                View view = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();

                owner.removeView(view);
                View view2=new View(view.getContext());;

                owner.addView( view2 );
                RelativeLayout container = (RelativeLayout) v;
                container.getId();
                container.addView(view);
                view.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:

                //  v.setBackgroundDrawable(normalShape);
            default:
                break;
        }
        return true;
    }
}
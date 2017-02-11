package com.dam2.android.quickpuzzle.Holders.Listeners;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import  com.dam2.android.quickpuzzle.R;

import com.dam2.android.quickpuzzle.Holders.PecaHolder;

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
               // v.invalidate();
                break;
            case DragEvent.ACTION_DROP:

                // Dropped, reassign View to ViewGroup
                //v.setX(event.getX());
                ClipData.Item item = event.getClipData().getItemAt(0);

                View view = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                RecyclerView recycler =((RecyclerView) owner.getParent());
                int Columns =recycler.getLayoutManager().getColumnCountForAccessibility(  null,null);
                PecaHolder holderOwner=(PecaHolder)recycler.getChildViewHolder( owner );
               PecaHolder holderDestination= (PecaHolder)recycler.getChildViewHolder( v );
                int positionOwner = holderOwner.getNumItem();
                int positionDestination = holderDestination.getNumItem();
               int columnOwner=positionOwner%Columns;
                int columnDestination=positionDestination%Columns;
                int rowOwner =positionOwner/Columns;
                int rowDestination=positionDestination/Columns;
                if (((positionDestination==positionOwner+1||positionDestination==positionOwner-1)&&(rowOwner==rowDestination)
                ||((positionDestination==positionOwner+Columns)||((positionDestination== positionOwner-Columns))))) {


                    owner.removeView( view );
                    View view2 = new View( view.getContext() );
                    // Aqui va el pas de la posicio nova cap a Partida

                    owner.addView( view2 );
                    owner.setOnDragListener( new MyDragListener() );
                    RelativeLayout container = (RelativeLayout) v;
                    container.setOnDragListener( null );
                    int posComprovacio = container.getId();

                    container.addView( view );
                    view.setVisibility( View.VISIBLE );
                    Log.v("container",String.valueOf( container.getId()));
                    Log.v("view",String.valueOf( view.getId()));
                    if(container.getId()==view.getId()){
                        Log.v("Posicio","Posicio correcte!");
                    }

                    MediaPlayer  mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.pop_drip);
                    mediaPlayer.start();
                }else{
                    view.setVisibility( View.VISIBLE );
                    MediaPlayer  mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.pop_drip);
                    mediaPlayer.start();
                    return false;
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
               view = (View) event.getLocalState();

                    view.setVisibility( View.VISIBLE );
              MediaPlayer  mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.pop_drip);
                mediaPlayer.start();

                //  v.setBackgroundDrawable(normalShape);
            default:
                break;
        }
        return true;
    }
}
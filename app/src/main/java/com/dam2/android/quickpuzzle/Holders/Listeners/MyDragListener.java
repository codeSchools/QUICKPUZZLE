package com.dam2.android.quickpuzzle.Holders.Listeners;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dam2.android.quickpuzzle.Holders.PecaHolder1;
import com.dam2.android.quickpuzzle.Holders.PecaHolder2;
import com.dam2.android.quickpuzzle.Partida;
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
                Partida.incrementarIntents();
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

                    //view=holderOwner.getRel().getChildAt( 0 )
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
                        Log.v("Posicio","Posicio correcte!+10");
                        Partida.sumarpuntuacio( 200);
                    }
                   int pecescorrectes=0;
                    for (int i = 0, size = recycler.getChildCount(); i < size; i++) {
                        RecyclerView.ViewHolder holder = recycler.getChildViewHolder(recycler.getChildAt(i));
                        if (holder != null) {

                            if(((PecaHolder) holder).getRel()!=null) {
                                int idRel = ((PecaHolder) holder).getRel().getId();
                                int numIMG = ((PecaHolder) holder).getRel().getChildCount();
                                if(((PecaHolder) holder).getRel().getChildAt( ((PecaHolder) holder).getRel().getChildCount()-1 )instanceof ImageView){
                                 int idImg=  ( ((PecaHolder) holder).getRel().getChildAt((( PecaHolder) holder).getRel().getChildCount()-1)).getId();
                                    if(idRel==idImg){
                                        Log.v("Revisio peces:","+12¡!");
                                        Partida.sumarpuntuacio( 50 );
                                        pecescorrectes++;
                                    }
                                    else {
                                        Log.v( "Revisio peces:", "-10!" );


                                    }

                                };

                            }

                        }
                    }
                    Log.v( "peces correctes:",String.valueOf( pecescorrectes) );
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
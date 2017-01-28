package com.dam2.android.quickpuzzle;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.dam2.android.quickpuzzle.Holders.Listeners.MyDragListener;
import com.dam2.android.quickpuzzle.Holders.Listeners.MyTouchListener;
import com.dam2.android.quickpuzzle.Holders.PecaHolder1;
import com.dam2.android.quickpuzzle.Holders.PecaHolder2;

public class GameFragment extends Fragment {
    public Integer[] mThumbIds = {
            R.drawable.peca, R.drawable.peca,
            R.drawable.peca, R.drawable.peca,
            R.drawable.peca,  R.drawable.peca,
            R.drawable.peca,  R.drawable.peca,
            R.drawable.peca

    };
//modificacio
    public static GameFragment newInstance(){
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quickpuzzle,container,false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragment_quickpuzzle);

        // Instance of ImageAdapter Class
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter( new ImageAdapter2(mThumbIds,getContext()) );
       // recyclerView.setOnDragListener(new MyDragListener());
        return v;
    }

    }

class ImageAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
            private Integer[] mThumbIds;
            private  Context mContext;

           @Override
           public int getItemViewType(int position) {
              if(position==mThumbIds.length-1)
               return 2;
               else return 1;
           }
            public ImageAdapter2(Integer[] mThumbIds, Context context) {
                this.mThumbIds=mThumbIds;
                this.mContext= context;
                //this.context = context;
            }
            @Override
            public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                switch (viewType) {
                    case 2:
                        return new PecaHolder2( inflater, parent );

                    default:
                        return new PecaHolder1( inflater, parent );
                }
            }

             @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
                switch (holder.getItemViewType()) {
                    case 1:
                        PecaHolder1 viewHolder = (PecaHolder1) holder;
                        viewHolder.mImage.setImageResource( mThumbIds[position] );
                        viewHolder.setNumItem( position );
                        break;

                    case 2:

                        PecaHolder2 viewHolder2 = (PecaHolder2) holder;
                        viewHolder2.setNumItem( position );
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return mThumbIds.length;
            }


}


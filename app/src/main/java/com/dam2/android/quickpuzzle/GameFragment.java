package com.dam2.android.quickpuzzle;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dam2.android.quickpuzzle.Holders.PecaHolder1;
import com.dam2.android.quickpuzzle.Holders.PecaHolder2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameFragment extends Fragment {
    public Integer[] mThumbIds = {
            R.drawable.peca, R.drawable.peca,
            R.drawable.peca, R.drawable.peca,
            R.drawable.peca,  R.drawable.peca,
            R.drawable.peca,  R.drawable.peca,
            R.drawable.peca

    };
    private Bitmap imatgeGran;
    private final int countColumns=3;
    public List< ImatgeBitmapPosition> imatges;
//modificacio
    public static GameFragment newInstance(){
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quickpuzzle,container,false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragment_quickpuzzle);

        imatgeGran=BitmapFactory.decodeResource(getResources(), R.drawable.homer);

        imatges=puzzleGeneratorImages(imatgeGran, countColumns);

        // Instance of ImageAdapter Class
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),countColumns));
        recyclerView.setAdapter( new ImageAdapter2(imatges,getContext()) );
       // recyclerView.setOnDragListener(new MyDragListener());
        return v;
    }
    private List< ImatgeBitmapPosition> puzzleGeneratorImages(Bitmap imatgeGran, int countColumns){
        List< ImatgeBitmapPosition> imatges = new ArrayList<ImatgeBitmapPosition>();
 //       if(imatgeGran.getWidth()<imatgeGran.getHeight()) imatgeGran.setHeight(imatgeGran.getWidth());
//        else imatgeGran.setWidth(imatgeGran.getHeight());
          int k=0,width = imatgeGran.getWidth(), height = imatgeGran.getHeight();
        Bitmap imatge=Bitmap.createBitmap( imatgeGran,(width*(countColumns-1))/countColumns,(height*(countColumns-1))/countColumns, width/countColumns,
                height/countColumns);
        ImatgeBitmapPosition parella = new ImatgeBitmapPosition( imatge,countColumns*countColumns-1 );
        for(int i =0;i<countColumns; i++){
            for(int j=0; j<countColumns;j++,k++){
                imatge=Bitmap.createBitmap( imatgeGran,(width*j)/countColumns,(height*i)/countColumns, width/countColumns,
                        height/countColumns);
                parella = new ImatgeBitmapPosition( imatge,k );
                if(k<countColumns*countColumns-1){
                    imatges.add( parella );
                }
            }
        }
        Collections.shuffle(imatges);
        Log.v(" imatges num", String.valueOf(  imatges.size()));
        imatges.add( parella );
Log.v(" imatges num", String.valueOf(  imatges.size()));
          return imatges;

    }
}

class ImageAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
            private  Context mContext;
            private List<ImatgeBitmapPosition>  imatges;

           @Override
           public int getItemViewType(int position) {
              if(position==imatges.size()-1)
               return 2;
               else return 1;
           }
            public ImageAdapter2(List< ImatgeBitmapPosition> imatges, Context context) {
                this.imatges= imatges;
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
                        viewHolder.mImage.setImageBitmap( imatges.get( position ).getImatge() );
                        viewHolder.mImage.setId( imatges.get( position ).getPosisioCorrecte() );
                        viewHolder.rel.setId( position );
                        Log.v("position", String.valueOf( position));
                        viewHolder.setNumItem( position );
                        break;

                    case 2:

                        PecaHolder2 viewHolder2 = (PecaHolder2) holder;
                        viewHolder2.rel.setId( position );
                        viewHolder2.setNumItem( position );
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return imatges.size();
            }


}


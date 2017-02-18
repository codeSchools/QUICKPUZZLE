package com.dam2.android.quickpuzzle;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.Service;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam2.android.quickpuzzle.Holders.Listeners.MyDragListener;
import com.dam2.android.quickpuzzle.Holders.PecaHolder1;
import com.dam2.android.quickpuzzle.Holders.PecaHolder2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameFragment extends Fragment  {

    private Bitmap imatgeGranfromAndroid;
    private final int countColumns=3;
    public List< ImatgeBitmapPosition> imatges;
    private RecyclerView mRecyclerView;
    private Intent intent;
    private boolean mBound=false;
    private SoundService mService;
    private static TextView textViewPunutuacio;
    public static GameFragment newInstance(){
        return new GameFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate( R.layout.fragment_quickpuzzle, container, false );
            Partida.setNumintents( 0 );
            Partida.setPuntuacio( 0 );
            mRecyclerView = (RecyclerView) v.findViewById( R.id.fragment_quickpuzzle );
            imatgeGranfromAndroid = SelectImage();
            imatges = puzzleGeneratorImages( imatgeGranfromAndroid, countColumns );
            textViewPunutuacio=(TextView) v.findViewById( R.id.textViewPuntuaucio );
            textViewPunutuacio.setText( "Puntuacio total: 0" );
            mRecyclerView.setLayoutManager( new GridLayoutManager( getActivity(), countColumns ) );
            mRecyclerView.setAdapter( new ImageAdapter2( imatges, getContext() ) );
        intent = new Intent(getActivity().getApplicationContext(),SoundService.class);

           return v;
    }

    private Bitmap SelectImage() {
        Bitmap imatge;
        Intent intent = new Intent( Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                Uri seleccio = intent.getData();
            String[] columna = {MediaStore.Images.Media.DATA};
        Random rn = new Random();
        Cursor cursor = getContext().getContentResolver().query(seleccio, columna, null, null, null);;//getContext().getContentResolver().query( seleccio, columna, null, null, null );
                if( cursor != null &&cursor.getCount()>0 ){
                    cursor.moveToPosition( rn.nextInt(cursor.getCount()));
                int indexColumna = cursor.getColumnIndex(columna[0]);
                    Log.v("index",String.valueOf(columna.length ));
                String rutaFitxer = cursor.getString( indexColumna );
                    Log.v("ruta : ",String.valueOf(rutaFitxer));
                cursor.close();

                    imatge = BitmapFactory.decodeFile( rutaFitxer );}
                else imatge =BitmapFactory.decodeResource(getResources(), R.drawable.homer);

            return imatge;


    }
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            SoundService.LocalBinder binder = (SoundService.LocalBinder) service;
            mService = binder.getService();
            SoundService.setmBound(true );
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            SoundService.setmBound(false );
        }
    };
    private List<ImatgeBitmapPosition> puzzleGeneratorImages(Bitmap imatgeGran, int countColumns){
        List< ImatgeBitmapPosition> imatges = new ArrayList<ImatgeBitmapPosition>();
        Bitmap imatgeRetocada;
        if(imatgeGran.getWidth()>imatgeGran.getHeight()) imatgeGran= Bitmap.createScaledBitmap(imatgeGran,imatgeGran.getWidth(),imatgeGran.getWidth(),true);
        else imatgeGran= Bitmap.createScaledBitmap(imatgeGran,imatgeGran.getHeight(),imatgeGran.getHeight(),true);
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
    public static void SetTextViewPuntuacio(int puntuacio){
        textViewPunutuacio.setText( "Puntuacio total: "+puntuacio );
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
                        float scale = mContext.getResources().getDisplayMetrics().density;
                        //Aquest calcul es perque quedi be si afegim mes columnes
                        viewHolder.mImage.getLayoutParams().height=(int)(((int) (360/Math.sqrt((double) getItemCount()) ))*scale);
                        viewHolder.rel.setId( position );
                        Log.v("position", String.valueOf( position));
                        viewHolder.setNumItem( position );
                        Animation a = AnimationUtils.loadAnimation(mContext, R.anim.rotation);
                        a.setDuration(1000);
                       viewHolder.mImage.startAnimation(a);
                        break;

                    case 2:

                        PecaHolder2 viewHolder2 = (PecaHolder2) holder;
                        viewHolder2.rel.setId( position );
                        viewHolder2.setNumItem( position );
                         a = AnimationUtils.loadAnimation(mContext, R.anim.rotation);
                        a.setDuration(1000);
                        viewHolder2.rel.startAnimation(a);
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return imatges.size();
            }


}


package com.dam2.android.quickpuzzle;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class GalleryFragment extends Fragment {

    LinearLayout l;
    int pos;
    ImageView imageView;
    public static Fragment newInstance(GalleryActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);

        return Fragment.instantiate(context, GalleryFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        l = (LinearLayout)
                inflater.inflate(R.layout.mf, container, false);

        pos = this.getArguments().getInt("pos");
        TextView tv = (TextView) l.findViewById(R.id.text);
        tv.setText("Position = " + pos);

        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return l;
    }

    @Override
    public void onResume() {
        super.onResume();
         imageView =(ImageView) l.findViewById( R.id.imgViewCarrussel );
        imageView.setImageBitmap( SelectImages( pos ));
    }

    @Override
    public void onPause() {
        super.onPause();
        imageView.invalidate();
        imageView.setImageBitmap(null);
    }

    private Bitmap SelectImages(int pos) {
        Bitmap imatge;
        Intent intent = new Intent( Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Uri seleccio = intent.getData();
        String[] columna = {MediaStore.Images.Media.DATA};
        Random rn = new Random();
        Cursor cursor = getContext().getContentResolver().query(seleccio, columna, null, null, null);;//getContext().getContentResolver().query( seleccio, columna, null, null, null );
        if( cursor != null &&cursor.getCount()>0 ){

                cursor.moveToPosition( pos%cursor.getCount() );
                int indexColumna = cursor.getColumnIndex( columna[0] );
                Log.v( "index", String.valueOf( columna.length ) );
                String rutaFitxer = cursor.getString( indexColumna );
                Log.v( "ruta : ", String.valueOf( rutaFitxer ) );


                imatge = BitmapFactory.decodeFile( rutaFitxer );

            cursor.close();
        }
        else {imatge =BitmapFactory.decodeResource(getResources(), R.drawable.homer);

        }

        return imatge;


    }
}

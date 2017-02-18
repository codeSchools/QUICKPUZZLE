package com.dam2.android.quickpuzzle;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GalleryActivity extends AppCompatActivity{
    public List<Bitmap> imatges = new ArrayList<Bitmap>();
    RelativeLayout RelContainer;
    int width;
    int pos=1;
    int midatotal;
    public static int PAGES = 5;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public final static int LOOPS = 1000;
    public static int FIRST_PAGE = 0;
    private static int numimg;
    public MyPagerAdapter adapter;
    public ViewPager pager;

    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        numimg=Numberofimages();
        PAGES=numimg;
        FIRST_PAGE = PAGES * LOOPS / 2;;
        pager = (ViewPager) findViewById(R.id.myviewpager);

        adapter = new MyPagerAdapter(this, this.getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setPageTransformer(false, adapter);

        pager.setCurrentItem(FIRST_PAGE);

        pager.setOffscreenPageLimit(3);
        pager.setPageMargin(-200);
       SwitchPage( 1 );
    }

    Timer timer = new Timer();

    public void SwitchPage(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.schedule(new SwitchPageTask(), 6000, seconds * 1000); // delay in milliseconds
    }
    class SwitchPageTask extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                public void run() {
                    if(pager.getCurrentItem()==PAGES-1)
                    {
                        pager.setCurrentItem(0);

                    }else
                    {pager.setCurrentItem(pager.getCurrentItem() + 1);}
                }
            });
        }
    }

    private int Numberofimages() {
        Bitmap imatge;
        Intent intent = new Intent( Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI );

        Uri seleccio = intent.getData();
        String[] columna = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query( seleccio, columna, null, null, null );
        ;//getContext().getContentResolver().query( seleccio, columna, null, null, null );

        return cursor.getCount();
    }
}

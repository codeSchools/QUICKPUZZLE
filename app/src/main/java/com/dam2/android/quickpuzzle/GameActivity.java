package com.dam2.android.quickpuzzle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends SingleFragmentActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE= 1;
    @Override
    protected Fragment createFragment() {




        return GameFragment.newInstance();
    }
}
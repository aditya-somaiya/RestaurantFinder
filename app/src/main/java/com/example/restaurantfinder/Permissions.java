package com.example.restaurantfinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

public class Permissions extends AppCompatActivity {

    private CardView PermissionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        final Intent intent = getIntent();
        final Bitmap bitmap = (Bitmap) intent.getParcelableExtra("Bitmap");

        if (ContextCompat.checkSelfPermission(Permissions.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(Permissions.this, MainActivity1.class));
            finish();
            return;
        }

        PermissionBtn = findViewById(R.id.btn_grant);
        PermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(Permissions.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                Intent toMains = new Intent(Permissions.this, MainActivity1.class);
                                startActivity(toMains);
                                finish();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Permissions.this);
                                    builder.setTitle("Permission Denied")
                                            .setMessage("Permission to access device location is permanently denied. you need to go to setting to allow the permission.")
                                            .setNegativeButton("Cancel", null)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent();
                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                                                }
                                            })
                                            .show();
                                } else {
                                    Toast.makeText(Permissions.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        })
                        .check();
            }
        });
    }
}

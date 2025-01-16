package com.example.imagebitmaptest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView = findViewById(R.id.image_test);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = imageView.getDrawable();
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    Log.d("zjq", "bitmap density = " + bitmap.getDensity() +
                            ", device density = " + getResources().getDisplayMetrics().density +
                            ", device densityDpi = " + getResources().getDisplayMetrics().densityDpi +
                            ", device scaleDensity = " + getResources().getDisplayMetrics().scaledDensity);
                    Log.d("zjq", "bitmap width = " + bitmap.getWidth() + ", bitmap height = " + bitmap.getHeight());
                    Log.d("zjq", "view width = " + imageView.getWidth() + ", view height = " + imageView.getHeight());
                }
            }
        });
    }
}
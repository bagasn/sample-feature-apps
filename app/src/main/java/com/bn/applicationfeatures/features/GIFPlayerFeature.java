package com.bn.applicationfeatures.features;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bn.applicationfeatures.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class GIFPlayerFeature extends AppCompatActivity {

    private static final String TAG = "GIFPlayerFeature";

    private WebView webView;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_gif_player);

        imageView = findViewById(R.id.imageView);

        Glide.with(this)
                .load("http://66.media.tumblr.com/tumblr_lvi2mgJ2mi1r2dpibo1_500.gif")
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.loader)
                .into(imageView);

        webView = findViewById(R.id.webView);
//        webView.loadData("file:///android_asset/google.gif", "image/gif", "UTF-8");
        webView.loadUrl("file:///android_asset/google.gif");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        try {
            InputStream inputStream = getAssets().open("google.gif");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }
}

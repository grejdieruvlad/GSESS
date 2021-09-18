package fiesc.licenta.gsess;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import pl.droidsonroids.gif.GifImageView;

public class wlcScreen extends AppCompatActivity {
    GifImageView gfcart;
    private static int SPLASH_TIME_OUT;
    private boolean networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SPLASH_TIME_OUT = 1000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlc_screen);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        gfcart = (GifImageView) findViewById(R.id.gfCart);
        image.setImageResource(R.drawable.wlcimage);
        gfcart.setImageResource(R.drawable.cart);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(wlcScreen.this, Home.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    public void onStop() {
        super.onStop();
        finish();
    }


}
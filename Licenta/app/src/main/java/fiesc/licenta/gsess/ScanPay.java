package fiesc.licenta.gsess;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fiesc.licenta.gsess.ui.scan.ScanFragment;

public class ScanPay extends AppCompatActivity implements NavigationView.OnCreateContextMenuListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView currentCustomerEmail, currentCustomerName;
    ImageView currentStoreImage;
    Context context;
    DialogInterface.OnClickListener dialogClickListener;

    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_pay);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View headerView = navigationView.getHeaderView(0);
//        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        currentCustomerEmail = (TextView) headerView.findViewById(R.id.currentCustomer);
        currentCustomerName = (TextView) headerView.findViewById(R.id.txtCustomerFullName);

        currentStoreImage = (ImageView) headerView.findViewById(R.id.currentStoreImage);

        b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("email")) {
                currentCustomerEmail.setText("");
                currentCustomerName.setText("");
                currentCustomerEmail.setText((b.getString("email")));
                currentCustomerName.setText((b.getString("firstName")) + " " + (b.getString("lastName")));
            }
            if (b.containsKey("image")) {
//                Bitmap bitmap = getIntent().getParcelableExtra("imageStore");
                int a = b.getInt("image");
                currentStoreImage.setImageResource(a);
            }
        } else {
            currentCustomerEmail.setText("android.studio@android.com");
            currentCustomerName.setText("Welcome dear customer!");
            currentStoreImage.setImageResource(R.drawable.logopng);
        }
        currentCustomerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ScanPay.this, SignUp.class);
                homeIntent.putExtra("email", currentCustomerEmail.getText().toString().trim());
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();

            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_scan, R.id.nav_history, R.id.nav_sales, R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scan_pay, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Closing Application");
        builder.setMessage("Are you sure you want to close Application?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

    }
}
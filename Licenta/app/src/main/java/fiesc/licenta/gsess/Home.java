package fiesc.licenta.gsess;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static fiesc.licenta.gsess.R.id.chkRemember;
import static fiesc.licenta.gsess.R.id.spnStores;

public class Home extends AppCompatActivity  {
    public static String globalWlcMsg="Welcome to our store!";
    CheckBox checkRem;
    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    TextView txtStories,txtUser,txtPass;
    private SignInButton signInButton;
    private String TAG="Home";
    Button btnLogin, btnSign,btnStore;
    private ConstraintLayout mConstraintLayout;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    ImageView logoImage;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    DataBaseHelperCustomers db;
    SQLiteDatabase database;
    List<Address> addresses;
    int image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db=new DataBaseHelperCustomers(this);
        checkRem=(CheckBox) findViewById(R.id.chkRemember);
        txtStories = (TextView) findViewById(R.id.lblStore);
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtPass = (TextView) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSign = (Button) findViewById(R.id.btnSign);
        btnStore = (Button) findViewById(R.id.btnStore);
        logoImage=(ImageView) findViewById(R.id.idLogo) ;
        mConstraintLayout = findViewById(R.id.constraint_layout);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Spinner stores=(Spinner)findViewById(spnStores);
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(Home.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.stores));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stores.setAdapter(myAdapter);
        stores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose a Store")) {
                txtStories.setVisibility(view.INVISIBLE);
                logoImage.setImageResource(R.drawable.logopng);
                image=R.drawable.logopng;
                }
                else{

                    //on selected Store
                    fetchLocation();
                    String item=parent.getItemAtPosition(position).toString();
                    txtStories.setVisibility(view.VISIBLE);
                    txtStories.setText("");
                    txtStories.setText("Welcome to our store "+item+" located in ");
                    btnLogin.setEnabled(true);
                    btnSign.setEnabled(true);
                    if (parent.getItemAtPosition(position).equals("Auchan")){
                        image=R.drawable.auchan;
                        logoImage.setImageResource(image);

                    }
                    else if(parent.getItemAtPosition(position).equals("Carrefour")){
                        image=R.drawable.carrefour;
                        logoImage.setImageResource(image);

                    }
                    else if(parent.getItemAtPosition(position).equals("Kaufland")){
                        image=R.drawable.kaufland;
                        logoImage.setImageResource(image);

                    }
                    else if(parent.getItemAtPosition(position).equals("Mega Image")){
                        image=R.drawable.mega;
                        logoImage.setImageResource(image);
                    }
                    else if(parent.getItemAtPosition(position).equals("Profi")){
                        image=R.drawable.profi;
                        logoImage.setImageResource(image);
                    }
                    else if(parent.getItemAtPosition(position).equals("Penny")){
                        image=R.drawable.penny;
                        logoImage.setImageResource(image);
                    }
                    else{
                        image=R.drawable.logopng;
                        logoImage.setImageResource(image);

                    }
                    //Show  selected spinner item


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO Auto-generated method stub
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if(txtUser.getVisibility() == View.INVISIBLE && txtPass.getVisibility() == View.INVISIBLE) {
                    txtPass.setVisibility(View.VISIBLE);
                    txtUser.setVisibility(View.VISIBLE);
                    mConstraintSet.clone(mConstraintLayout);
                    mConstraintSet.connect(R.id.btnLogin, ConstraintSet.TOP,
                            chkRemember, ConstraintSet.BOTTOM);
                    mConstraintSet.connect(R.id.btnSign, ConstraintSet.TOP,
                            R.id.btnLogin, ConstraintSet.BOTTOM);
                    mConstraintSet.connect(R.id.idLogo, ConstraintSet.TOP,
                            R.id.lblStore, ConstraintSet.BOTTOM);
                    mConstraintSet.applyTo(mConstraintLayout);
                    checkRem.setVisibility(View.VISIBLE);

                }
                else{
                    //verifing  email and password
                    String email=txtUser.getText().toString().trim();
                    String pass=txtPass.getText().toString().trim();
                    if(db.isLoginValid(email,pass)){
                        Toast.makeText(Home.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent homeIntent=new Intent(Home.this,ScanPay.class);
                        homeIntent.putExtra("email",email);
                        homeIntent.putExtra("image",image);
                        Cursor c=db.searchCustomerFulName(email);
                        String firstName="";
                        String lastName="";
                        if (c.moveToFirst()){
                            do {
                                 firstName = c.getString(0);
                                 lastName= c.getString(1);
                            } while(c.moveToNext());
                        }
                        c.close();
                        db.close();
                        homeIntent.putExtra("firstName",firstName);
                        homeIntent.putExtra("lastName",lastName);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                         finish();
                    }

                    else{
                        txtUser.setError("Enter valid email");
                        txtPass.setError("Enter valid password");
                        Toast.makeText(Home.this, "Wrong email or password!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUser.setError(null);
                txtPass.setError(null);
                txtPass.setVisibility(View.INVISIBLE);
                txtUser.setVisibility(View.INVISIBLE);
                checkRem.setVisibility(View.INVISIBLE);
                mConstraintSet.clone(mConstraintLayout);
                mConstraintSet.connect(R.id.btnLogin, ConstraintSet.TOP,
                        R.id.txtUser, ConstraintSet.BOTTOM);
                mConstraintSet.connect(R.id.btnSign, ConstraintSet.TOP,
                        R.id.btnLogin, ConstraintSet.BOTTOM);
                mConstraintSet.applyTo(mConstraintLayout);
                checkRem.setVisibility(View.INVISIBLE);
                Intent homeIntent=new Intent(Home.this,SignUp.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                 finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent homeIntent=new Intent(Home.this,Home.class);
        startActivity(homeIntent);
        finish();
    }
    private void fetchLocation() {


        if (ContextCompat.checkSelfPermission(Home.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to acess this feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Home.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(Home.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Double latittude = location.getLatitude();
                                Double longitude = location.getLongitude();


                                try {
                                    addresses=geocoder.getFromLocation(latittude,longitude,1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String address=addresses.get(0).getAddressLine(0);
                                String area=addresses.get(0).getLocality();
                                String city=addresses.get(0).getAdminArea();
                                globalWlcMsg=txtStories.getText()+"\n"+city+", "+address;
                                txtStories.setText(globalWlcMsg);

                            }
                            else{
                                Toast.makeText(Home.this, "Can't geet Location!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }
    public static byte[] compresssImage(Bitmap b) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] compressedByteArray = stream.toByteArray();
        return compressedByteArray;
    }

}

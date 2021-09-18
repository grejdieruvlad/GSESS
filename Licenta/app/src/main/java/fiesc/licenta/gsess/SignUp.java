package fiesc.licenta.gsess;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    Spinner spnGender;
    EditText bornDate;
    DatePickerDialog.OnDateSetListener setListener;
    ImageButton cancel, signup;
    EditText firstname, lastname, email, phoneNumber, password, city, street, passwordConf, streetNr, postaleCode;
    DataBaseHelperCustomers db;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_up);
        super.onCreate(savedInstanceState);
        db = new DataBaseHelperCustomers(this);
        firstname = findViewById(R.id.txtNum);
        lastname = findViewById(R.id.txtPren);
        email = findViewById(R.id.txtEmail);
        phoneNumber = findViewById(R.id.txtNumber);
        spnGender = findViewById(R.id.txtGen);
        bornDate = findViewById(R.id.txtBorn);
        password = findViewById(R.id.txtPass);
        passwordConf = findViewById(R.id.txtPassConf);
        city = findViewById(R.id.txtCity);
        street = findViewById(R.id.txtStreet);
        streetNr = findViewById(R.id.txtStreetNum);
        postaleCode = findViewById(R.id.txtPost);
        cancel = (ImageButton) findViewById(R.id.btnCancel);
        signup = (ImageButton) findViewById(R.id.btnSignUp);
        String[] genders = getResources().getStringArray(R.array.genders);
        final ArrayAdapter<String> genders_adapter = new ArrayAdapter<String>(SignUp.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.genders));
        genders_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(genders_adapter);
//        String gen = genders_adapter.getItem(0);
        bundle=(Bundle) getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("email")) {
                Cursor c = db.getCustomerInfo(bundle.getString("email"));
                String gen="";
                if (c.moveToFirst()) {
                    do {
                        firstname.setText(c.getString(0));
                        lastname.setText(c.getString(1));
                        email.setText(c.getString(2));
                        phoneNumber.setText(c.getString(3));
                       gen=c.getString(4);
                        bornDate.setText(c.getString(5));
                        city.setText(c.getString(7));
                        street.setText(c.getString(8));
                        streetNr.setText(c.getString(9));
                        postaleCode.setText(c.getString(10));
                    } while (c.moveToNext());
                }
                c.close();
                db.close();
                int pos=genders_adapter.getPosition(gen);
                spnGender.setSelection(pos);
                password.setError("Enter last password to update your account!");
                passwordConf.setError("Confirm your password");
                signup.setImageResource(android.R.drawable.ic_menu_edit);
            }
        }
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int zi = calendar.get(Calendar.DAY_OF_MONTH);
        bornDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, setListener, year, month, zi);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis() - 1000));
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int zi) {
                bornDate.setError(null);
                month=month+1;
                String date = zi + "/" + month + "/" + year;
                if ((calendar.get(Calendar.YEAR) - 10) <= year) {
                    Toast.makeText(getApplicationContext(), "YOU ARE TOO YOUNG TO SHOOP BY YOURSELF!", Toast.LENGTH_LONG).show();
                } else {
                    bornDate.setText(date);
                }
//                bornDate.setText(date);
            }
        };
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customers customer = new Customers();
                String nume = firstname.getText().toString().trim();
                String prenume = lastname.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String telefon = phoneNumber.getText().toString().trim();
                String data = bornDate.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String passC = passwordConf.getText().toString().trim();
                String oras = city.getText().toString().trim();
                String strada = street.getText().toString().trim();
                String numarstrada = streetNr.getText().toString().trim();
                String codPostal = postaleCode.getText().toString().trim();
                if (TextUtils.isEmpty(nume) || TextUtils.isEmpty(prenume) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(telefon) || TextUtils.isEmpty(data) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(oras) || ((TextUtils.isEmpty(passC)) || (!passC.equals(pass)))) {
                    if (TextUtils.isEmpty(nume)) {
                        firstname.setError("First name is required!");
                        return;
                    } else {
                        customer.setFirstname(nume);
                    }
                    if (TextUtils.isEmpty(prenume)) {
                        lastname.setError("Last name is required!");
                        return;
                    } else {
                        customer.setLastname(prenume);
                    }
                    if (TextUtils.isEmpty(mail)) {
                        email.setError("Email is required!");
                        return;
                    } else {
                        customer.setEmail(mail);
                    }
                    if (TextUtils.isEmpty(telefon)) {
                        phoneNumber.setError("Phone number is required!");
                        return;
                    } else {
                        customer.setPhoneNumber(telefon);
                    }
                    if (TextUtils.isEmpty(data)) {
                        bornDate.setError("Born date  is required!");
                        return;
                    } else {
                        customer.setBornDate(data);
                    }
                    if (TextUtils.isEmpty(pass)) {
                        password.setError("Password is required for your account security!");
                        return;
                    } else {
                        customer.setPassword(pass);
                    }
                    if (pass.length() < 6) {
                        password.setError("Password must have more than 6 carachters!");
                        return;
                    }
                    if ((TextUtils.isEmpty(passC)) || !passC.equals(pass)) {
                        passwordConf.setError("Passwords doesn't match!");
                        return;
                    }
                    if (TextUtils.isEmpty(oras)) {
                        city.setError("City is required !");
                        return;
                    }
                }
                    customer.setFirstname(nume);
                    customer.setLastname(prenume);
                    customer.setEmail(mail);
                    customer.setPhoneNumber(telefon);
                    customer.setGender(spnGender.getSelectedItem().toString().trim());
                    customer.setBornDate(data);
                    customer.setPassword(pass);
                    customer.setCity(oras);
                    customer.setStreet(strada);
                    customer.setStreetNumber(numarstrada);
                    customer.setPostalCode(codPostal);
//                   Customers customer1 = new Customers(nume, prenume, mail, telefon, spnGender.getSelectedItem().toString().trim(), data, pass, oras, strada, numarstrada, codPostal);
                if (bundle != null) {
                    if (bundle.containsKey("email")) {
                        String email= bundle.getString("email");
                        Cursor cursor=db.getCustomerPassword(email);
                        String checkPassword="test";
                        if (cursor.moveToFirst()) {
                            do {
                                checkPassword=cursor.getString(0);
                            } while (cursor.moveToNext());


                        }
                        cursor.close();
                        if(password.getText().toString().trim().equals(checkPassword.trim())) {
                            if (db.updateCustomer(customer, email))
//                            new Customers(nume, prenume, mail, telefon, spnGender.getSelectedItem().toString().trim(), data, pass, oras, strada, numarstrada, codPostal)
                            {
                                Toast.makeText(SignUp.this, "Your account was updated successful!", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(SignUp.this, ScanPay.class);
                                homeIntent.putExtra("email", email);
                                homeIntent.putExtra("firstName",firstname.getText().toString().trim());
                                homeIntent.putExtra("lastName", lastname.getText().toString().trim());
//                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(homeIntent);
                                finish();
                            }
                            else{
                                Toast.makeText(SignUp.this, "Your account wasn't updated! Try one more time!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignUp.this, "Incorect password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    db.insertCustomer(customer);
                    //new Customers(nume, prenume, mail, telefon, spnGender.getSelectedItem().toString().trim(), data, pass, oras, strada, numarstrada, codPostal)
                    Toast.makeText(SignUp.this, "Your account was created successful!", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(SignUp.this, Home.class);
//                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
//
                  finish();
                }

        }

    });
}

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (bundle != null) {
            if(bundle.containsKey("email")) {
                Intent homeIntent = new Intent(SignUp.this, ScanPay.class);
                homeIntent.putExtra("email", email.getText().toString().trim());
                homeIntent.putExtra("firstName",firstname.getText().toString().trim() );
                homeIntent.putExtra("lastName", lastname.getText().toString().trim());
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
            else {
                Intent homeIntent = new Intent(SignUp.this, Home.class);
//                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);}
        finish();
        } else {
            Intent homeIntent = new Intent(SignUp.this, Home.class);
//            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            finish();
        }
    }
}
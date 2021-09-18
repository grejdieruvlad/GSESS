package fiesc.licenta.gsess;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataBaseHelperCustomers extends  SQLiteOpenHelper {
    public static final String customerId = "customerId"; // eash user has unique id
//    public static final String firstName = "firstName";
//    public static final String lastName = "lastName";
//    public static final String email = "email";
//    public static final String phoneNumber = "phoneNumber";
//    public static final String gender ="gender";
//    public static final String bornDate ="bornDate";
//    public static final String password ="password";
//    public static final String city ="city";
//    public static final String street ="street";
//    public static final String streetNumber ="streetNumber";
//    public static final String postalCode ="postalCode";
    public static final String TABLE_NAME ="Customers";
    public static final String DATABASE_NAME ="Store";

    public DataBaseHelperCustomers(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NEWUSER_TABLE = "CREATE TABLE if not exists " + TABLE_NAME + "( firstName TEXT,lastName TEXT,email TEXT PRIMARY KEY,phoneNumber TEXT,gender TEXT,bornDate TEXT,password TEXT,city TEXT,street TEXT,streetNumber TEXT,postalCode TEXT)";
      db.execSQL(CREATE_NEWUSER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        onCreate(db);
    }
    //inserting in database
    Customers customer=new Customers();
    public boolean insertCustomer(Customers customer){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("firstName",customer.getFirstname().toString().trim());
        contentValues.put("lastName",customer.getLastname().toString().trim());
        contentValues.put("email",customer.getEmail().toString().trim());
        contentValues.put("phoneNumber",customer.getPhoneNumber().toString().trim());
        contentValues.put("gender",customer.getGender().toString().trim());
        contentValues.put("bornDate",customer.getBornDate().toString().trim());
        contentValues.put("password",customer.getPassword().toString().trim());
        contentValues.put("city", customer.getCity().toString().trim());
        contentValues.put("street", customer.getStreet().toString().trim());
        contentValues.put("streetNumber", customer.getStreetNumber().toString().trim());
        contentValues.put("postalCode", customer.getPostalCode().toString().trim());
        long ins=db.insert("Customers",null,contentValues);
        if(ins==1)  return  false;
        else return true;
    }
    public Boolean isLoginValid(String email,String password){
    String sql="Select count(*)from Customers where email='"+email+"'and password='"+password+"'";
        SQLiteStatement statement=getReadableDatabase().compileStatement(sql);
        long l=statement.simpleQueryForLong();
        statement.close();
        if(l==1){
            return  true;
        }
        else{
            return  false;
        }
    }
    public Cursor searchCustomerFulName(String email) {
        Cursor cursor = null;
        SQLiteDatabase s = this.getWritableDatabase();
        cursor= s.rawQuery("select firstName,lastName from Customers where email=?",new String[]{email});
        return cursor;
    }
    public Cursor getCustomerInfo(String email) {
        Cursor cursor = null;
        SQLiteDatabase s = this.getWritableDatabase();
        cursor= s.rawQuery("select firstName TEXT,lastName TEXT,email TEXT,phoneNumber TEXT,gender TEXT,bornDate TEXT,password TEXT,city TEXT,street TEXT,streetNumber TEXT,postalCode TEXT from Customers where email=?",new String[]{email});
        return cursor;
    }
    public Cursor getCustomerPassword(String email) {
        Cursor cursor = null;
        SQLiteDatabase s = this.getWritableDatabase();
        cursor= s.rawQuery("select password TEXT from Customers where email=?",new String[]{email});
//        String password=cursor.getString(0);
        return cursor;
    }
    public boolean updateCustomer(Customers customer,String email) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("firstName",customer.getFirstname().toString().trim());
        contentValues.put("lastName",customer.getLastname().toString().trim());
        contentValues.put("email",customer.getEmail().toString().trim());
        contentValues.put("phoneNumber",customer.getPhoneNumber().toString().trim());
        contentValues.put("gender",customer.getGender().toString().trim());
        contentValues.put("bornDate",customer.getBornDate().toString().trim());
        contentValues.put("password",customer.getPassword().toString().trim());
        contentValues.put("city", customer.getCity().toString().trim());
        contentValues.put("street", customer.getStreet().toString().trim());
        contentValues.put("streetNumber", customer.getStreetNumber().toString().trim());
        contentValues.put("postalCode", customer.getPostalCode().toString().trim());
        db.update(TABLE_NAME, contentValues, "email = ?",new String[] { email });
        return true;
    }

}

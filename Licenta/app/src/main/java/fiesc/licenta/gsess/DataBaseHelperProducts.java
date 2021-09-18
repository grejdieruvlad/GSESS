package fiesc.licenta.gsess;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DataBaseHelperProducts extends  SQLiteOpenHelper {
    public static final String TABLE_NAME ="Products";
    public static final String DATABASE_NAME ="Store";

    public DataBaseHelperProducts(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NEWUSER_TABLE = "CREATE TABLE if not exists " + TABLE_NAME + "( productId NUMBER NOT NULL PRIMARY KEY,productName TEXT,productDescription TEXT,unitsOnOrder TEXT,productPictureUrl TEXT, discount REAL)";
        db.execSQL(CREATE_NEWUSER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Products");
        onCreate(db);
    }
    //inserting in database
    Products product=new Products();
    public boolean insertProduct(Products product){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("productId",product.getProductId());
        contentValues.put("productName",product.getProductName().toString().trim());
        contentValues.put("productDescription",product.getProductDescription().toString().trim());
        contentValues.put("productStore",product.getProductStore().toString().trim());
        contentValues.put("unitsOnOrder",product.getUnitsOnOrder().toString().trim());
        contentValues.put("discount",product.getDiscount().toString().trim());
        contentValues.put("productPictureUrl",product.getProductPictureUrl().toString().trim());
        long ins=db.insert("Products",null,contentValues);
        if(ins==1)  return  false;
        else return true;
    }
//    public Boolean isLoginValid(String email,String password){
//    String sql="Select count(*)from Customers where email='"+email+"'and password='"+password+"'";
//        SQLiteStatement statement=getReadableDatabase().compileStatement(sql);
//        long l=statement.simpleQueryForLong();
//        statement.close();
//        if(l==1){
//            return  true;
//        }
//        else{
//            return  false;
//        }
////    }
//    public Cursor searchCustomerFulName(String email) {
//        Cursor cursor = null;
//        SQLiteDatabase s = this.getWritableDatabase();
//        cursor= s.rawQuery("select firstName,lastName from Customers where email=?",new String[]{email});
//        return cursor;
//    }
    public Cursor getProductInfo(Long productId) {
        Cursor cursor = null;
        SQLiteDatabase s = this.getWritableDatabase();
        cursor= s.rawQuery("select productId ,productName ,productDescription ,unitsOnOrder ,productPictureUrl , discount from Products where productId=? ", new String[] {productId.toString()});
        return cursor;
    }
    public Cursor getProductStore(Long productId) {
        Cursor cursor = null;
        SQLiteDatabase s = this.getWritableDatabase();
        cursor= s.rawQuery("select password TEXT from Customers where email=?",new String[]{productId.toString()});
//        String password=cursor.getString(0);
        return cursor;
    }
    public boolean updateProduct(Products product,Long productId) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("productId",product.getProductId());
        contentValues.put("productName",product.getProductName().toString().trim());
        contentValues.put("productDescription",product.getProductDescription().toString().trim());
        contentValues.put("productStore",product.getProductStore().toString().trim());
        contentValues.put("unitsOnOrder",product.getUnitsOnOrder().toString().trim());
        contentValues.put("discount",product.getDiscount().toString().trim());
        contentValues.put("productPictureUrl",product.getProductPictureUrl().toString().trim());
        db.update(TABLE_NAME, contentValues, "email = ?",new String[] { productId.toString() });
        return true;
    }


}

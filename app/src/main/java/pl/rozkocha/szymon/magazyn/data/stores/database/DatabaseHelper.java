package pl.rozkocha.szymon.magazyn.data.stores.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pl.rozkocha.szymon.magazyn.data.beams.Category;
import pl.rozkocha.szymon.magazyn.data.stores.IStore;

/**
 * Created by Szymon on 10.06.2017 in Magazyn.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "magazyn.db";

    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_PROVIDERS = "providers";
    public static final String TABLE_ARTICLES = "articles";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PARENT_ID = "parent_id";

    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_ADDRESS = "address";

    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_PROVIDER_ID = "provider_id";

    public static final String ORDER_ASC = "ASC";
    public static final String ORDER_DESC = "DESC";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_PARENT_ID + " INTEGER NULL"
            + ");";

    private static final String CREATE_TABLE_PROVIDERS = "CREATE TABLE " + TABLE_PROVIDERS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_TELEPHONE + " TEXT, "
            + COLUMN_ADDRESS + " TEXT "
            + ");";

    private static final String CREATE_TABLE_ARTICLES = "CREATE TABLE " + TABLE_ARTICLES + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_PRICE + " FLOAT, "
            + COLUMN_CATEGORY_ID + " INTEGER, "
            + COLUMN_PROVIDER_ID + " INTEGER "
            + ");";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_PROVIDERS);
        db.execSQL(CREATE_TABLE_ARTICLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + " " + TABLE_CATEGORIES);
        db.execSQL(DROP_TABLE + " " + CREATE_TABLE_PROVIDERS);
        db.execSQL(DROP_TABLE + " " + CREATE_TABLE_ARTICLES);
    }
}

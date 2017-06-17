package pl.rozkocha.szymon.magazyn.data.stores.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.rozkocha.szymon.magazyn.data.beams.Category;
import pl.rozkocha.szymon.magazyn.data.beams.Provider;
import pl.rozkocha.szymon.magazyn.data.stores.IStore;

/**
 * Created by Szymon on 11.06.2017 in Magazyn.
 */

public class ProvidersStore implements IStore<Provider> {
    private DatabaseHelper helper;

    public ProvidersStore(DatabaseHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<Provider> get() {
        return get(DatabaseHelper.COLUMN_NAME, DatabaseHelper.ORDER_ASC);
    }

    @Override
    public List<Provider> getWithoutId(int withoutId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(DatabaseHelper.TABLE_PROVIDERS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_TELEPHONE, DatabaseHelper.COLUMN_ADDRESS},
                "id != " + withoutId, null, null, null,
                DatabaseHelper.COLUMN_NAME + " " + DatabaseHelper.ORDER_ASC,
                null);
        c.moveToFirst();

        List<Provider> list = new ArrayList<>();

        while(!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            String telephone = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_TELEPHONE));
            String address = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));

            list.add(new Provider(id, name, telephone, address));

            c.moveToNext();
        }
        db.close();
        return list;
    }

    @Override
    public List<Provider> get(String column, String order) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(DatabaseHelper.TABLE_PROVIDERS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_TELEPHONE, DatabaseHelper.COLUMN_ADDRESS},
                null, null, null, null,
                column + " " + order,
                null);
        c.moveToFirst();

        List<Provider> list = new ArrayList<>();

        while(!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            String telephone = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_TELEPHONE));
            String address = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));

            list.add(new Provider(id, name, telephone, address));

            c.moveToNext();
        }
        db.close();
        return list;
    }

    @Override
    public Provider getById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(DatabaseHelper.TABLE_PROVIDERS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_TELEPHONE, DatabaseHelper.COLUMN_ADDRESS}, "id = " + id, null, null, null, null, null);

        Provider provider = null;

        if(c.getCount() > 0) {
            c.moveToFirst();

            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            String telephone = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_TELEPHONE));
            String address = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS));

            provider = new Provider(id, name, telephone, address);
        }
        db.close();

        return provider;
    }

    @Override
    public void add(Provider provider) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, provider.getName());
        cv.put(DatabaseHelper.COLUMN_TELEPHONE, provider.getTelephone());
        cv.put(DatabaseHelper.COLUMN_ADDRESS, provider.getAddress());

        db.insert(DatabaseHelper.TABLE_PROVIDERS, null, cv);

        db.close();
    }

    @Override
    public void update(Provider provider) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, provider.getName());
        cv.put(DatabaseHelper.COLUMN_TELEPHONE, provider.getTelephone());
        cv.put(DatabaseHelper.COLUMN_ADDRESS, provider.getAddress());

        db.update(DatabaseHelper.TABLE_PROVIDERS, cv, "id = " + provider.getId(), null);

        db.close();
    }

    @Override
    public void remove(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        db.delete(DatabaseHelper.TABLE_PROVIDERS, "id = " + id, null);

        db.close();
    }
}

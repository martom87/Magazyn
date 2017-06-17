package pl.rozkocha.szymon.magazyn.data.stores.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.rozkocha.szymon.magazyn.data.beams.Article;
import pl.rozkocha.szymon.magazyn.data.beams.Category;
import pl.rozkocha.szymon.magazyn.data.stores.IStore;

/**
 * Created by Szymon on 11.06.2017 in Magazyn.
 */

public class CategoriesStore implements IStore<Category> {
    private DatabaseHelper helper;

    public CategoriesStore(DatabaseHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<Category> get() {
        return get(DatabaseHelper.COLUMN_ID, DatabaseHelper.ORDER_DESC);
    }

    @Override
    public List<Category> getWithoutId(int withoutId) {
        //otwarcie bazy danych
        SQLiteDatabase db = helper.getReadableDatabase();

        //zapytnie do tabeli kategorie o pola id, name, parent_id gdzie id != withoutId
        Cursor c = db.query(DatabaseHelper.TABLE_CATEGORIES,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PARENT_ID},
                "id != " + withoutId, null, null, null,
                DatabaseHelper.COLUMN_NAME + " " + DatabaseHelper.ORDER_ASC,
                null);
        c.moveToFirst();

        List<Category> list = new ArrayList<>();

        while(!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));

            if(c.isNull(c.getColumnIndex(DatabaseHelper.COLUMN_PARENT_ID))) {
                list.add(new Category(id, name, null));
            } else {
                int parentId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_PARENT_ID));
                list.add(new Category(id, name, parentId));
            }

            c.moveToNext();
        }
        db.close();
        return list;
    }

    @Override
    public List<Category> get(String column, String order) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(DatabaseHelper.TABLE_CATEGORIES,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PARENT_ID},
                null, null, null, null,
                column + " " + order,
                null);
        c.moveToFirst();

        List<Category> list = new ArrayList<>();

        while(!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));

            if(c.isNull(c.getColumnIndex(DatabaseHelper.COLUMN_PARENT_ID))) {
                list.add(new Category(id, name, null));
            } else {
                int parentId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_PARENT_ID));
                list.add(new Category(id, name, parentId));
            }

            c.moveToNext();
        }
        db.close();
        return list;
    }

    @Override
    public Category getById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(DatabaseHelper.TABLE_CATEGORIES,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PARENT_ID}, "id = " + id, null, null, null, null, null);

        Category category = null;

        if(c.getCount() > 0) {
            c.moveToFirst();

            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));

            if(c.isNull(c.getColumnIndex(DatabaseHelper.COLUMN_PARENT_ID))) {
                category = new Category(id, name, null);
            } else {
                int parentId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_PARENT_ID));
                category = new Category(id, name, parentId);
            }
        }
        db.close();

        return category;
    }

    @Override
    public void add(Category category) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, category.getName());

        if(category.getParentId() != null) {
            cv.put(DatabaseHelper.COLUMN_PARENT_ID, category.getParentId());
        }

        db.insert(DatabaseHelper.TABLE_CATEGORIES, null, cv);

        db.close();
    }

    @Override
    public void update(Category category) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, category.getName());

        if(category.getParentId() != null) {
            cv.put(DatabaseHelper.COLUMN_PARENT_ID, category.getParentId());
        }

        db.update(DatabaseHelper.TABLE_CATEGORIES, cv, "id = " + category.getId(), null);

        db.close();
    }

    @Override
    public void remove(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        db.delete(DatabaseHelper.TABLE_CATEGORIES, "id = " + id, null);

        db.close();
    }
}

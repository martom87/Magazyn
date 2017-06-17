package pl.rozkocha.szymon.magazyn.data.stores.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.rozkocha.szymon.magazyn.data.beams.Article;
import pl.rozkocha.szymon.magazyn.data.beams.Category;
import pl.rozkocha.szymon.magazyn.data.beams.Provider;
import pl.rozkocha.szymon.magazyn.data.stores.IStore;

/**
 * Created by Szymon on 11.06.2017 in Magazyn.
 */

public class ArticlesStore implements IStore<Article> {
    private DatabaseHelper helper;
    private IStore<Category> categoriesStore;
    private IStore<Provider> providersStore;

    public ArticlesStore(DatabaseHelper helper, IStore<Category> categoriesStore, IStore<Provider> providersStore) {
        this.helper = helper;
        this.categoriesStore = categoriesStore;
        this.providersStore = providersStore;
    }

    @Override
    public List<Article> get() {
        return get(DatabaseHelper.COLUMN_NAME, DatabaseHelper.ORDER_ASC);
    }

    @Override
    public List<Article> getWithoutId(int withoutId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(DatabaseHelper.TABLE_ARTICLES,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_CATEGORY_ID, DatabaseHelper.COLUMN_PROVIDER_ID},
                "id != " + withoutId, null, null, null,
                DatabaseHelper.COLUMN_NAME + " " + DatabaseHelper.ORDER_ASC,
                null);
        c.moveToFirst();

        List<Article> list = new ArrayList<>();

        while(!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            float price = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
            int categoryId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
            int providerId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_PROVIDER_ID));

            list.add(new Article(id, name, price, categoriesStore.getById(categoryId), providersStore.getById(providerId)));

            c.moveToNext();
        }
        db.close();
        return list;
    }

    @Override
    public List<Article> get(String column, String order) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(DatabaseHelper.TABLE_ARTICLES,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_CATEGORY_ID, DatabaseHelper.COLUMN_PROVIDER_ID},
                null, null, null, null,
                column + " " + order,
                null);
        c.moveToFirst();

        List<Article> list = new ArrayList<>();

        while(!c.isAfterLast()) {
            int id = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            float price = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
            int categoryId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
            int providerId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_PROVIDER_ID));

            list.add(new Article(id, name, price, categoriesStore.getById(categoryId), providersStore.getById(providerId)));

            c.moveToNext();
        }
        db.close();
        return list;
    }

    @Override
    public Article getById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(DatabaseHelper.TABLE_ARTICLES,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_CATEGORY_ID, DatabaseHelper.COLUMN_PROVIDER_ID}, "id = " + id, null, null, null, null, null);

        Article article = null;

        if(c.getCount() > 0) {
            c.moveToFirst();

            String name = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            float price = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
            int categoryId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
            int providerId = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_PROVIDER_ID));

            article = new Article(id, name, price, categoriesStore.getById(categoryId), providersStore.getById(providerId));
        }
        db.close();

        return article;
    }

    @Override
    public void add(Article article) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, article.getName());
        cv.put(DatabaseHelper.COLUMN_PRICE, article.getPrice());
        cv.put(DatabaseHelper.COLUMN_CATEGORY_ID, article.getCategory().getId());
        cv.put(DatabaseHelper.COLUMN_PROVIDER_ID, article.getProvider().getId());

        db.insert(DatabaseHelper.TABLE_ARTICLES, null, cv);

        db.close();
    }

    @Override
    public void update(Article article) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, article.getName());
        cv.put(DatabaseHelper.COLUMN_PRICE, article.getPrice());
        cv.put(DatabaseHelper.COLUMN_CATEGORY_ID, article.getCategory().getId());
        cv.put(DatabaseHelper.COLUMN_PROVIDER_ID, article.getProvider().getId());

        db.update(DatabaseHelper.TABLE_ARTICLES, cv, "id = " + article.getId(), null);

        db.close();
    }

    @Override
    public void remove(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        db.delete(DatabaseHelper.TABLE_ARTICLES, "id = " + id, null);

        db.close();
    }
}

package pl.rozkocha.szymon.magazyn.data.stores;

import pl.rozkocha.szymon.magazyn.Application;
import pl.rozkocha.szymon.magazyn.data.beams.Article;
import pl.rozkocha.szymon.magazyn.data.beams.Category;
import pl.rozkocha.szymon.magazyn.data.beams.Provider;
import pl.rozkocha.szymon.magazyn.data.stores.database.ArticlesStore;
import pl.rozkocha.szymon.magazyn.data.stores.database.CategoriesStore;
import pl.rozkocha.szymon.magazyn.data.stores.database.DatabaseHelper;
import pl.rozkocha.szymon.magazyn.data.stores.database.ProvidersStore;

/**
 * Created by Szymon on 11.06.2017 in Magazyn.
 */

public class StoreFactory {
    public static IStore<Category> createCategoriesStore() {
        return new CategoriesStore(new DatabaseHelper(Application.get().getApplicationContext()));
    }

    public static IStore<Provider> createProvidersStore() {
        return new ProvidersStore(new DatabaseHelper(Application.get().getApplicationContext()));
    }

    public static IStore<Article> createArticlesStore(IStore<Category> categoriesStore, IStore<Provider> providersStore) {
        return new ArticlesStore(new DatabaseHelper(Application.get().getApplicationContext()), categoriesStore, providersStore);
    }
}

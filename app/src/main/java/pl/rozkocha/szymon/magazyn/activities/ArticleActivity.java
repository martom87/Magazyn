package pl.rozkocha.szymon.magazyn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pl.rozkocha.szymon.magazyn.R;
import pl.rozkocha.szymon.magazyn.data.beams.Article;
import pl.rozkocha.szymon.magazyn.data.beams.Category;
import pl.rozkocha.szymon.magazyn.data.beams.Provider;
import pl.rozkocha.szymon.magazyn.data.stores.IStore;
import pl.rozkocha.szymon.magazyn.data.stores.StoreFactory;

public class ArticleActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        final int id = intent.getIntExtra(EXTRA_ID, 0);

        final EditText nameTextEdit = (EditText) findViewById(R.id.name);
        final EditText priceTextEdit = (EditText) findViewById(R.id.price);
        final Spinner categorySpinner = (Spinner) findViewById(R.id.category);
        final Spinner providerSpinner = (Spinner) findViewById(R.id.provider);

        IStore<Category> storeCategories = StoreFactory.createCategoriesStore();
        List<Category> categories = new ArrayList<>();
        categories.addAll(storeCategories.get());

        ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoriesAdapter);

        IStore<Provider> storeProviders = StoreFactory.createProvidersStore();
        List<Provider> providers = new ArrayList<>();
        providers.addAll(storeProviders.get());

        ArrayAdapter<Provider> providersAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, providers);
        providersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        providerSpinner.setAdapter(providersAdapter);

        if(id != 0) {
            IStore<Article> storeArticles = StoreFactory.createArticlesStore(storeCategories, storeProviders);
            Article article = storeArticles.getById(id);

            nameTextEdit.setText(article.getName());
            priceTextEdit.setText(article.getPrice() + "");

            int categoryPosition = Category.getPositionById(article.getCategory().getId(), categories);
            if(categoryPosition == -1) {
                categoryPosition = 0;
            }

            categorySpinner.setSelection(categoryPosition);

            int providerPosition = Provider.getPositionById(article.getProvider().getId(), providers);
            if(providerPosition == -1) {
                providerPosition = 0;
            }

            providerSpinner.setSelection(providerPosition);
        }

        Button button = (Button) findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTextEdit.getText().toString();
                float price = Float.parseFloat(priceTextEdit.getText().toString());

                Category category = (Category) categorySpinner.getSelectedItem();
                Provider provider = (Provider) providerSpinner.getSelectedItem();

                IStore<Article> store = StoreFactory.createArticlesStore(StoreFactory.createCategoriesStore(), StoreFactory.createProvidersStore());

                if(id != 0) {
                    store.update(new Article(id, name, price, category, provider));
                } else {
                    store.add(new Article(0, name, price, category, provider));
                }

                finish();
            }
        });
    }
}

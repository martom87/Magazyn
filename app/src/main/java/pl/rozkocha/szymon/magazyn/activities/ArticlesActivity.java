package pl.rozkocha.szymon.magazyn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import pl.rozkocha.szymon.magazyn.R;
import pl.rozkocha.szymon.magazyn.data.beams.Article;
import pl.rozkocha.szymon.magazyn.data.beams.Category;
import pl.rozkocha.szymon.magazyn.data.stores.IStore;
import pl.rozkocha.szymon.magazyn.data.stores.StoreFactory;
import pl.rozkocha.szymon.magazyn.views.adapters.ArticlesAdapter;
import pl.rozkocha.szymon.magazyn.views.adapters.CategoriesAdapter;

public class ArticlesActivity extends UpdatableActivity {
    private ListView listView;
    private ArticlesAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.list);
        listAdapter = new ArticlesAdapter(getApplicationContext(), this);
        listView.setAdapter(listAdapter);

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ArticleActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        update();
    }

    @Override
    public void update() {
        IStore<Article> store = StoreFactory.createArticlesStore(StoreFactory.createCategoriesStore(), StoreFactory.createProvidersStore());
        List<Article> articles = store.get();

        listAdapter.update(articles);
    }
}

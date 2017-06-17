package pl.rozkocha.szymon.magazyn.activities.SettingsActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pl.rozkocha.szymon.magazyn.R;
import pl.rozkocha.szymon.magazyn.data.beams.Article;
import pl.rozkocha.szymon.magazyn.data.stores.IStore;
import pl.rozkocha.szymon.magazyn.data.stores.StoreFactory;

/**
 * Created by katar on 16.06.2017.
 */

public class SettingsActivity extends AppCompatActivity {
    ArrayAdapter<Article> articleAdapter;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_settings);

        Button changeBackGroundColor = (Button) findViewById(R.id.changeBackGroundColor);
        changeBackGroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ColorsActivity.class));
            }
        });

        Button changeButtonsColors = (Button) findViewById(R.id.changeButtonsColors);
        changeButtonsColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final Spinner favouriteItem = (Spinner) findViewById(R.id.favouriteItem);
        articleAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        articleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favouriteItem.setAdapter(articleAdapter);

        favouriteItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Article article = articleAdapter.getItem(i);
                SharedPreferences sharedPreferences = getSharedPreferences("settings", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("favouriteItem_id", article.getId());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void onStart() {
        super.onStart();
        articleAdapter.clear();
        IStore<Article> store = StoreFactory.createArticlesStore(StoreFactory.createCategoriesStore(),
                StoreFactory.createProvidersStore());
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(0, "Brak", 0.0f, null, null));
        articles.addAll(store.get());
        articleAdapter.addAll(articles);

    }

}

package pl.rozkocha.szymon.magazyn.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import pl.rozkocha.szymon.magazyn.activities.UpdatableActivity;
import pl.rozkocha.szymon.magazyn.data.beams.Article;
import pl.rozkocha.szymon.magazyn.data.beams.Category;
import pl.rozkocha.szymon.magazyn.views.ArticleItem;
import pl.rozkocha.szymon.magazyn.views.CategoryItem;

/**
 * Created by Szymon on 10.06.2017 in Magazyn.
 */

public class ArticlesAdapter extends ArrayAdapter<Article> {
    private UpdatableActivity activity;

    public ArticlesAdapter(Context context, UpdatableActivity activity) {
        super(context, 0);
        this.activity = activity;
    }

    public void update(List<Article> articles) {
        clear();

        addAll(articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = getItem(position);

        return new ArticleItem(getContext(), article, this.activity);
    }
}

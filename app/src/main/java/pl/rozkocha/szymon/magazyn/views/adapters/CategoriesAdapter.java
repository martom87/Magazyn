package pl.rozkocha.szymon.magazyn.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import pl.rozkocha.szymon.magazyn.views.CategoryItem;
import pl.rozkocha.szymon.magazyn.activities.UpdatableActivity;
import pl.rozkocha.szymon.magazyn.data.beams.Category;

/**
 * Created by Szymon on 10.06.2017 in Magazyn.
 */

public class CategoriesAdapter extends ArrayAdapter<Category> {
    private UpdatableActivity activity;

    public CategoriesAdapter(Context context, UpdatableActivity activity) {
        super(context, 0);
        this.activity = activity;
    }

    public void update(List<Category> categories) {
        clear();

        addAll(categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Category category = getItem(position);

        return new CategoryItem(getContext(), category, this.activity);
    }
}

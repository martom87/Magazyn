package pl.rozkocha.szymon.magazyn.data.stores;

import java.util.List;

/**
 * Created by Szymon on 11.06.2017 in Magazyn.
 */

public interface IStore<T> {
    List<T> get();
    List<T> getWithoutId(int withoutId);
    List<T> get(String column, String order);
    T getById(int id);

    void add(T c);
    void update(T c);
    void remove(int id);
}

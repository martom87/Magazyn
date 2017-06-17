package pl.rozkocha.szymon.magazyn.data.beams;

import java.util.Collection;
import java.util.List;

/**
 * Created by Szymon on 11.06.2017 in Magazyn.
 */

public class Beam {
    private int id;
    private String name;

    public Beam(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

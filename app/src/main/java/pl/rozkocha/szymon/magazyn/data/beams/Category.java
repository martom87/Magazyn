package pl.rozkocha.szymon.magazyn.data.beams;

import java.util.List;

/**
 * Created by Szymon on 10.06.2017 in Magazyn.
 */

public class Category extends Beam{
    private Integer parentId;

    public Category(int id, String name, Integer parentId) {
        super(id, name);
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public static int getPositionById(int id, List<Category> list) {
        int position = -1;

        for(int i = 0;i < list.size();++i) {
            Category category = list.get(i);
            if(category.getId() == id) {
                position = i;
                break;
            }
        }

        return position;
    }
}

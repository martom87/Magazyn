package pl.rozkocha.szymon.magazyn.data.beams;

/**
 * Created by Szymon on 10.06.2017 in Magazyn.
 */

public class Article extends Beam{
    private float price;
    private Category category;
    private Provider provider;

    public Article(int id, String name, float price, Category category, Provider provider) {
        super(id, name);
        this.price = price;
        this.category = category;
        this.provider = provider;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}

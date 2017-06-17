package pl.rozkocha.szymon.magazyn;

/**
 * Created by Szymon on 11.06.2017 in Magazyn.
 */

public class Application extends android.app.Application {
    private static Application instance = null;

    public static Application get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }
}

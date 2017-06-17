package pl.rozkocha.szymon.magazyn.data.beams;

import java.util.List;

/**
 * Created by Szymon on 10.06.2017 in Magazyn.
 */

public class Provider extends Beam{
    private String telephone;
    private String address;

    public Provider(int id, String name, String telephone, String address) {
        super(id, name);
        this.telephone = telephone;
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static int getPositionById(int id, List<Provider> list) {
        int position = -1;

        for(int i = 0;i < list.size();++i) {
            Provider provider = list.get(i);
            if(provider.getId() == id) {
                position = i;
                break;
            }
        }

        return position;
    }
}

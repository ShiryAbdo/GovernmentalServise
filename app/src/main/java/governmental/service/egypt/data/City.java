package governmental.service.egypt.data;

import java.util.Map;

/**
 * Created by falcon on 30/10/2017.
 */

public class City {
    Map<String, Object> cityNameHash ;

    public City(Map<String, Object> cityNameHash) {
        this.cityNameHash = cityNameHash;
    }

    public Map<String, Object> getCityNameHash() {
        return cityNameHash;
    }
}

package governmental.service.egypt.data;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by falcon on 28/10/2017.
 */

public class GavernoratWithLocation {
    String governoratname;
    LatLng Locatio ;

    public GavernoratWithLocation(String governoratname, LatLng locatio) {
        this.governoratname = governoratname;
        Locatio = locatio;
    }

    public String getGovernoratname() {
        return governoratname;
    }

    public LatLng getLocatio() {
        return Locatio;
    }
}

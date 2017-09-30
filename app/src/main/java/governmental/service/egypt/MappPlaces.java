package governmental.service.egypt;

import android.content.Context;
 import android.location.Address;
import android.location.Geocoder;
 import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import governmental.service.egypt.data.Service;

public class MappPlaces extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    MultiSpinnerSearch searchMultiSpinnerUnlimited ,searchMultiSpinnerUnlimitedGavernorat ,searchMultiSpinnerUnlimitedPlaces;
    private DatabaseReference mDatabase;
    ArrayList<String> categories ;
    ArrayList<String>CatogersGaverNorate ;
    ArrayList<String>  categoriesPlaces;
    List<KeyPairBoolData> listArray0,listArrayGaverNorate,listArrayPlaces;
    TextView textView2 ;
    ArrayList<String>MltyChoseService ,MultyChoseGavernNorate,MlutyChosePlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapp_places);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        textView2=(TextView)findViewById(R.id.textView2);
        categories = new ArrayList<String>();
        CatogersGaverNorate = new ArrayList<String>();
        categoriesPlaces = new ArrayList<String>();
        categories.add("أختار خدمه");
        listArray0 = new ArrayList<>();
        listArrayGaverNorate = new ArrayList<>();
        listArrayPlaces = new ArrayList<>();
        MltyChoseService=new ArrayList<>();
        MultyChoseGavernNorate=new ArrayList<>();
        MlutyChosePlaces=new ArrayList<>();

        mDatabase.child("users").child("Service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                        Service value = dataSnapshot1.getValue(Service.class);
                         String servisename =value.getNameService();
                        categories.add(servisename);
                    }
                }else{
                }


                for (int i = 0; i < categories.size(); i++) {
                    KeyPairBoolData h = new KeyPairBoolData();
                    h.setId(i + 1);
                    h.setName(categories.get(i));
                    h.setSelected(false);
                    listArray0.add(h);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        searchMultiSpinnerUnlimited = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerUnlimited);
         searchMultiSpinnerUnlimited.setItems(listArray0, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {

                        MltyChoseService.add(items.get(i).getName());
                    }
                }


                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Toast.makeText(getApplicationContext(), i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected(), Toast.LENGTH_SHORT).show();
                        Log.i("Tag", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });
        CatogersGaverNorate.add("القاهره");
        CatogersGaverNorate.add("الجيزه");
        CatogersGaverNorate.add("الشرقية");
        CatogersGaverNorate.add("الدقهلية");
        CatogersGaverNorate.add("المنصورة");
        CatogersGaverNorate.add("قنا");
        CatogersGaverNorate.add("سهاج");
        CatogersGaverNorate.add("دمياط");
        CatogersGaverNorate.add("الغربية");
        for (int i = 0; i < CatogersGaverNorate.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(CatogersGaverNorate.get(i));
            h.setSelected(false);
            listArrayGaverNorate.add(h);
        }



        searchMultiSpinnerUnlimitedGavernorat=(MultiSpinnerSearch)findViewById(R.id.searchMultiSpinnerUnlimitedGavernorat);
        searchMultiSpinnerUnlimitedGavernorat.setItems(listArrayGaverNorate, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                searchMultiSpinnerUnlimitedGavernorat.getSelectedItems();
                StringBuilder spinnerBuffer = new StringBuilder();

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        spinnerBuffer.append(items.get(i).getName());
                        spinnerBuffer.append(", ");
                        MultyChoseGavernNorate.add(items.get(i).getName());
                    }
                }


                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Toast.makeText(getApplicationContext(), i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected(), Toast.LENGTH_SHORT).show();
                        Log.i("Tag", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });
        categoriesPlaces.add("طنطا قسم أول ");
        categoriesPlaces.add("طنطا قسم ثاني ");
        categoriesPlaces.add("المحله");
        categoriesPlaces.add("زفتي");
        categoriesPlaces.add("المحله الكبري");
        categoriesPlaces.add("محله روح");
        for (int i = 0; i < categoriesPlaces.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(categoriesPlaces.get(i));
            h.setSelected(false);
            listArrayPlaces.add(h);
        }



        searchMultiSpinnerUnlimitedPlaces=(MultiSpinnerSearch)findViewById(R.id.searchMultiSpinnerUnlimitedPlaces);
        searchMultiSpinnerUnlimitedPlaces.setItems(listArrayPlaces, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                 StringBuilder spinnerBuffer = new StringBuilder();

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        spinnerBuffer.append(items.get(i).getName());
                        spinnerBuffer.append(", ");
                        MlutyChosePlaces.add(items.get(i).getName());
                    }
                }



            }
        });


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latLng.latitude, latLng.longitude)).title(getGeocodeName(latLng.latitude, latLng.longitude));
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.addMarker(marker);

            }
        });

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(30.1229752,31.3025158);
        mMap.addMarker(new MarkerOptions().position(sydney).title("القاهره"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.0f));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.0f));
    }





     public String getGeocodeName(double latitude, double longitude) {
        Context context = getApplicationContext();

        Geocoder geocoder = new Geocoder( context);
        List<Address> addresses = null;
        String unknown ="Unknown Location";
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return unknown;
        }
        if ( addresses == null ||addresses.size() == 0) {
            return unknown;
        }
        Address address = addresses.get(0);



        String cn = address.getCountryName();

        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        String mainLocality = address.getSubAdminArea();

        return city + ", " + state+ ", " +country;
    }
}

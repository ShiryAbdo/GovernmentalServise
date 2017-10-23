package governmental.service.egypt;

import android.content.Context;
 import android.location.Address;
import android.location.Geocoder;
 import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import governmental.service.egypt.data.Service;

public class MappPlaces extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    MultiSpinnerSearch searchMultiSpinnerUnlimited ,searchMultiSpinnerUnlimitedGavernorat ,searchMultiSpinnerUnlimitedPlaces;
    private DatabaseReference mDatabase;
    ArrayList<String> categories ;
    ArrayList<String>CatogersGaverNorate ;
    ArrayList<String>  categoriesPlaces;
    List<KeyPairBoolData> listArray0,listArrayGaverNorate,listArrayPlaces;
     ArrayList<String>MltyChoseService ,MultyChoseGavernNorate,MlutyChosePlaces;
    EditText name_of_place ;
    Button CompletService ;
    Map<String, Object> PlacesOfGaverNorate;
    Map<String, Object> PlacesOfPlaces;
    ArrayList<String>CairoPlaces;
    ArrayList<String>gizaPlaces;
    ArrayList<String>garbiaPlaces;
    ArrayList<String>sharqialaces;
    ArrayList<String>mansoraPlaces;
    SingleSpinnerSearch searchSingleSpinner ;
    LatLng FinallatLng;
    Double longtuti;
    Double latutuied;
    TextView textView2 ;
    String  name ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapp_places);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        CompletService=(Button)findViewById(R.id.CompletService);
         name_of_place=(EditText)findViewById(R.id.name_of_place);
        searchSingleSpinner= (SingleSpinnerSearch) findViewById(R.id.searchSingleSpinner);
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
        PlacesOfGaverNorate = new HashMap<>();
        PlacesOfPlaces= new HashMap<>();
        CairoPlaces=new ArrayList<>();
        gizaPlaces=new ArrayList<>();
        garbiaPlaces=new ArrayList<>();
        sharqialaces=new ArrayList<>();
        mansoraPlaces=new ArrayList<>();


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

                final ArrayList<String> newData =new ArrayList<String>();
                for (int x =0 ; x<MltyChoseService.size();x++){
                    mDatabase.child("users").child("Service").child(MltyChoseService.get(x)).child("places").child("OtherplacesOfService").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                                    String value = dataSnapshot1.getKey();
                                    newData.add(value);
                                    textView2.setText(value);

                                }



                            }else{








                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }



                final ArrayList<String> Data =new ArrayList<String>();
                for (int x =0 ; x<newData.size();x++){
                    mDatabase.child("users").child("Service").child(MltyChoseService.get(x)).child("places").child("OtherplacesOfService").child(newData.get(x)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                                    String value = dataSnapshot1.getValue(String.class);
                                    Data.add(value);
                                    textView2.setText(value);

                                }
                                MultyChoseGavernNorate.add(dataSnapshot.getKey());
                                PlacesOfGaverNorate.put(dataSnapshot.getKey(),Data);


                            }else{








                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }



                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Toast.makeText(getApplicationContext(), i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected(), Toast.LENGTH_SHORT).show();
                        Log.i("Tag", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });
        CatogersGaverNorate.add("إضافة الكل");
        CatogersGaverNorate.add("القاهره");
        CatogersGaverNorate.add("الجيزه");
        CatogersGaverNorate.add("الشرقية");
        CatogersGaverNorate.add("الدقهلية");
        CatogersGaverNorate.add("المنصورة");
        CatogersGaverNorate.add("قنا");
        CatogersGaverNorate.add("سهاج");
        CatogersGaverNorate.add("دمياط");
        CatogersGaverNorate.add("الغربية");

        garbiaPlaces.add("إضافة الكل");
        garbiaPlaces.add("طنطا قسم أول ");
        garbiaPlaces.add("طنطا قسم ثاني ");
        garbiaPlaces.add("المحله");
        garbiaPlaces.add("زفتي");
        garbiaPlaces.add("المحله الكبري");
        garbiaPlaces.add("محله روح");
         CairoPlaces.add("مدينة نصر");
        CairoPlaces.add("مصر الجديدة");
        CairoPlaces.add("المعادي");
        CairoPlaces.add("حدائق الزتون");
        for (int i = 0; i < CatogersGaverNorate.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(CatogersGaverNorate.get(i));
            h.setSelected(false);
            listArrayGaverNorate.add(h);
        }




        searchSingleSpinner.setItems(listArrayGaverNorate, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(final List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    final int finalI = i;
                    if (items.get(i).isSelected()) {

                        if( MultyChoseGavernNorate.contains(items.get(finalI).getName())){
                            Toast.makeText(getApplicationContext(), "تم إضافة هذه المحافظه مسبقا", Toast.LENGTH_SHORT).show();
                        }else {
                            name=items.get(finalI).getName();
                        }



                        if(name==CatogersGaverNorate.get(1)){
                            listArrayPlaces.clear();
                            categoriesPlaces.clear();
                            categoriesPlaces.addAll(CairoPlaces);
                            for (int K = 0; K < categoriesPlaces.size(); K++) {
                                KeyPairBoolData h = new KeyPairBoolData();
                                h.setId(K+ 1);
                                h.setName(categoriesPlaces.get(K));
                                h.setSelected(false);
                                listArrayPlaces.add(h);
                            }
                        }else if (name=="الغربية"){
                            listArrayPlaces.clear();
                            categoriesPlaces.clear();
                            categoriesPlaces.addAll(garbiaPlaces);

                            for (int K = 0; K < categoriesPlaces.size(); K++) {
                                KeyPairBoolData h = new KeyPairBoolData();
                                h.setId(K+ 1);
                                h.setName(categoriesPlaces.get(K));
                                h.setSelected(false);
                                listArrayPlaces.add(h);
                            }
                        }else {
                            listArrayPlaces.clear();
                            categoriesPlaces.clear();
                        }




                    }
                }
            }
        });






        searchMultiSpinnerUnlimitedPlaces=(MultiSpinnerSearch)findViewById(R.id.searchMultiSpinnerUnlimitedPlaces);
        searchMultiSpinnerUnlimitedPlaces.setItems(listArrayPlaces, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                 StringBuilder spinnerBuffer = new StringBuilder();
                ArrayList<String> arrayList = new ArrayList<String>();

                 for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {

//                        لا بد من   عمل تشك ايه   بالظبط اللي  اتعمل  له اضافه الكل  وعلي اساسه نختار الأراي   الللي  المفروض   ينضاف

                         String  name =items.get(i).getName();
                        if(name=="إضافة الكل"){
                             for(int j = 1; j < garbiaPlaces.size(); j++){
                                PlacesOfPlaces.put(garbiaPlaces.get(j),garbiaPlaces.get(j));

                            }
                            Toast.makeText(getApplicationContext(),
                                    "تم إضافه كل المناطق", Toast.LENGTH_LONG).show();

                        }else {
                            PlacesOfPlaces.put(items.get(i).getName(),items.get(i).getName());
                            arrayList.add(items.get(i).getName());

                        }

                    }
                }
                PlacesOfGaverNorate.put(name,arrayList);





            }
        });

        CompletService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name_of_place.getText().toString()==""){
                    Toast.makeText(getApplicationContext(),
                            "أدخل إسم المكان ", Toast.LENGTH_LONG).show();
                }else if (latutuied==null&&longtuti==null){
                    Toast.makeText(getApplicationContext(),
                            "أضف موقع المكان علي الخريطة", Toast.LENGTH_LONG).show();
                }
                else if(PlacesOfPlaces.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            " أضف البيانات ", Toast.LENGTH_LONG).show();
                }
                else {
                    for (int i=0 ;i<MltyChoseService.size();i++){
//                        AddPlaceService(MltyChoseService.get(i), PlacesOfGaverNorate);
                        mDatabase.child("users").child("Service").child(MltyChoseService.get(i)).child("places").child(name_of_place.getText().toString().trim()).child("OtherplacesOfService").setValue(PlacesOfGaverNorate);
                        mDatabase.child("users").child("Service").child(MltyChoseService.get(i)).child("places").child(name_of_place.getText().toString().trim()).child(name_of_place.getText().toString().trim()).setValue(name_of_place.getText().toString().trim());
                        mDatabase.child("users").child("Service").child(MltyChoseService.get(i)).child("places").child(name_of_place.getText().toString().trim()).child("latitude").setValue(latutuied);
                        mDatabase.child("users").child("Service").child(MltyChoseService.get(i)).child("places").child(name_of_place.getText().toString().trim()).child("longitude").setValue(longtuti);
                        Toast.makeText(getApplicationContext(),
                                "تمت الإضافه بنجاح", Toast.LENGTH_LONG).show();
                        MappPlaces.this.finish();
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
                FinallatLng=latLng;
                latutuied=latLng.latitude;
                longtuti=latLng.longitude;
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


    private void AddPlaceService (String  serviceName ,Map<String, Object>  arrayOfplaces ) {

    }




     public String getGeocodeName(double latitude, double longitude) {
        Context context = getApplicationContext();

        Geocoder geocoder = new Geocoder( context);
        List<Address> addresses = null;
        String unknown ="";
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

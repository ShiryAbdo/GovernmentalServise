package governmental.service.egypt;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
 import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import governmental.service.egypt.Adaptors.PaperDataAdapter;
import governmental.service.egypt.data.Governorate;
import governmental.service.egypt.data.Service;

public class MappPlaces extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    MultiSpinnerSearch searchMultiSpinnerUnlimited ,searchMultiSpinnerUnlimitedPlacesSupArea ,searchMultiSpinnerUnlimitedPlaces;
    private DatabaseReference mDatabase;
    ArrayList<String> categories ;
    ArrayList<String>CatogersGaverNorate ;
    ArrayList<String>  categoriesPlaces ,catogersArea;
    List<KeyPairBoolData> listArray0,listArrayGaverNorate, listCytisPlaces,listArrayAreas;
     ArrayList<String>MltyChoseService ,MultyChoseGavernNorate,MlutyChosePlaces;
    EditText name_of_place ;
    Button CompletService ;
     ArrayList<String>data;
    PaperDataAdapter adapter ;
    Map<String, Object> PlacesOfGaverNorate ,HashNumber;
     ImageView addPAreaBt ;
    Map<String, Object> PlacesOfPlaces ,CiysArea;
    ArrayList<String>CairoPlaces;
    ArrayList<String>gizaPlaces;
    ArrayList<String>garbiaPlaces ,choseArray;
    ArrayList<String>sharqialaces;
    ArrayList<String>mansoraPlaces;
    SingleSpinnerSearch searchSingleSpinner ;
    LatLng FinallatLng;
    Double longtuti ,longtutinew ,latutuiednew;
    Double latutuied;
    ArrayList<String> numberOfChoices;
     String  nameGavernorate ,nameCity;
     RecyclerView recyclerView ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapp_places);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        CompletService=(Button)findViewById(R.id.CompletService);
         name_of_place=(EditText)findViewById(R.id.name_of_place);
        searchSingleSpinner= (SingleSpinnerSearch) findViewById(R.id.searchSingleSpinner);
         data= new ArrayList<>();
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new PaperDataAdapter(MappPlaces.this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        addPAreaBt=(ImageView)findViewById(R.id.addPAreaBt);
        categories = new ArrayList<String>();
         CatogersGaverNorate = new ArrayList<String>();
        categoriesPlaces = new ArrayList<String>();
        categories.add("أختار خدمه");
        listArray0 = new ArrayList<>();
        listArrayGaverNorate = new ArrayList<>();
        listCytisPlaces = new ArrayList<>();
        listArrayAreas = new ArrayList<>();
        MltyChoseService=new ArrayList<>();
        MultyChoseGavernNorate=new ArrayList<>();
        MlutyChosePlaces=new ArrayList<>();
        PlacesOfGaverNorate = new HashMap<>();
        PlacesOfPlaces= new HashMap<>();
        CiysArea=new HashMap<>();
        CairoPlaces=new ArrayList<>();
        gizaPlaces=new ArrayList<>();
        garbiaPlaces=new ArrayList<>();
        sharqialaces=new ArrayList<>();
        mansoraPlaces=new ArrayList<>();
        catogersArea= new ArrayList<>();
        numberOfChoices= new ArrayList<>();



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

                                }
                                MultyChoseGavernNorate.add(dataSnapshot.getKey());
//                                PlacesOfGaverNorate.put(dataSnapshot.getKey(),Data);


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
//        CatogersGaverNorate.add("إضافة الكل");
//        CatogersGaverNorate.add("القاهره");
//        CatogersGaverNorate.add("الجيزه");
//        CatogersGaverNorate.add("الشرقية");
//        CatogersGaverNorate.add("الدقهلية");
//        CatogersGaverNorate.add("المنصورة");
//        CatogersGaverNorate.add("قنا");
//        CatogersGaverNorate.add("سهاج");
//        CatogersGaverNorate.add("دمياط");
//        CatogersGaverNorate.add("الغربية");

//        garbiaPlaces.add("إضافة الكل");
//        garbiaPlaces.add("طنطا قسم أول ");
//        garbiaPlaces.add("طنطا قسم ثاني ");
//        garbiaPlaces.add("المحله");
//        garbiaPlaces.add("زفتي");
//        garbiaPlaces.add("المحله الكبري");
//        garbiaPlaces.add("محله روح");
//         CairoPlaces.add("مدينة نصر");
//        CairoPlaces.add("مصر الجديدة");
//        CairoPlaces.add("المعادي");
//        CairoPlaces.add("حدائق الزتون");



        mDatabase.child("users").child("Governorate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    CatogersGaverNorate.clear();
                    listArrayGaverNorate.clear();
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                        Governorate value = dataSnapshot1.getValue(Governorate.class);
                        String servisename =value.getGovernorateName();

                        CatogersGaverNorate.add(servisename);


                    }
                }else{
                }

                for (int i = 0; i < CatogersGaverNorate.size(); i++) {
                    KeyPairBoolData h = new KeyPairBoolData();
                    h.setId(i + 1);
                    h.setName(CatogersGaverNorate.get(i));
                    h.setSelected(false);
                    listArrayGaverNorate.add(h);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        searchSingleSpinner.setItems(listArrayGaverNorate, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(final List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    final int finalI = i;
                    if (items.get(i).isSelected()) {

//                        if( MultyChoseGavernNorate.contains(items.get(finalI).getName())){
//                            Toast.makeText(getApplicationContext(), "تم إضافة هذه المحافظه مسبقا", Toast.LENGTH_SHORT).show();
//                        }else {
                        nameGavernorate=items.get(finalI).getName();
//                        }
                        listCytisPlaces.clear();
                        categoriesPlaces.clear();
                        mDatabase.child("users").child("Governorate").child(nameGavernorate).child("citys").child("cityNameHash").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    listCytisPlaces.clear();
                                    categoriesPlaces.clear();
                                    categoriesPlaces.add("إضافة كل المدن");
                                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                                        StringBuilder spinnerBuffer = new StringBuilder();
                                        String servisename = dataSnapshot1.getKey();
                                        categoriesPlaces.add(servisename);
                                        if(categoriesPlaces.contains("cityNameHash"))
                                        {
                                            categoriesPlaces.remove("cityNameHash");
                                        }


                                    }
                                    for (int K = 0; K < categoriesPlaces.size(); K++) {
                                        KeyPairBoolData h = new KeyPairBoolData();
                                        h.setId(K + 1);
                                        h.setName(categoriesPlaces.get(K));
                                        h.setSelected(false);
                                        listCytisPlaces.add(h);
                                    }



                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });




                    }
                }
            }
        });
        choseArray = new ArrayList<>();
        choseArray.add("كل المناطق ");
        for (int K = 0; K < choseArray.size(); K++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(K + 1);
            h.setName(choseArray.get(K));
            h.setSelected(false);
            listArrayAreas.add(h);
        }


        searchMultiSpinnerUnlimitedPlaces=(MultiSpinnerSearch)findViewById(R.id.searchMultiSpinnerUnlimitedPlaces);
        searchMultiSpinnerUnlimitedPlaces.setItems(listCytisPlaces, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                StringBuilder spinnerBuffer = new StringBuilder();
                ArrayList<String> arrayList = new ArrayList<String>();
                numberOfChoices.clear();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {

                        nameCity =items.get(i).getName();
                        if(nameCity=="إضافة كل المدن"){
                            numberOfChoices.addAll(categoriesPlaces);
                            catogersArea.clear();
                            listArrayAreas.clear();
 //                            categoriesPlaces
                            listArrayAreas.clear();
                            if(numberOfChoices.contains("إضافة كل المدن")){
                                numberOfChoices.remove("إضافة كل المدن");
                            }
                            KeyPairBoolData h = new KeyPairBoolData();
                            h.setId(0 + 1);
                            h.setName("كل المناطق ");
                            h.setSelected(false);
                            listArrayAreas.add(h);
                        }else{

                            numberOfChoices.add(nameCity);
                            catogersArea.clear();
                            listArrayAreas.clear();
                            catogersArea.add("ضافة كل  المناطق");
                            mDatabase.child("users").child("Governorate").child(nameGavernorate).child("citys").child("cityNameHash").child(nameCity).child("areas").child("areasHs").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            String servisename = dataSnapshot1.getKey();
                                            catogersArea.add(servisename);
                                            if (catogersArea.contains("areasHs")){
                                                catogersArea.remove("areasHs");
                                            }


                                        }


                                    }

                                    if (numberOfChoices.size() == 1) {
                                        listArrayAreas.clear();
                                        for (int K = 0; K < catogersArea.size(); K++) {
                                            KeyPairBoolData h = new KeyPairBoolData();
                                            h.setId(K + 1);
                                            h.setName(catogersArea.get(K));
                                            h.setSelected(false);
                                            listArrayAreas.add(h);
                                        }
                                    }else {
                                        listArrayAreas.clear();
                                        KeyPairBoolData h = new KeyPairBoolData();
                                        h.setId(0 + 1);
                                        h.setName("كل المناطق ");
                                        h.setSelected(false);
                                        listArrayAreas.add(h);
                                    }

                                }



                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }
                }
                PlacesOfGaverNorate.put(nameGavernorate,arrayList);





            }
        });
        searchMultiSpinnerUnlimitedPlacesSupArea=(MultiSpinnerSearch)findViewById(R.id.searchMultiSpinnerUnlimitedPlacesSupArea);
        searchMultiSpinnerUnlimitedPlacesSupArea.setItems(listArrayAreas, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                StringBuilder spinnerBuffer = new StringBuilder();
                ArrayList<String> arrayList = new ArrayList<String>();

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {

//                        لا بد من   عمل تشك ايه   بالظبط اللي  اتعمل  له اضافه الكل  وعلي اساسه نختار الأراي   الللي  المفروض   ينضاف

                        String  name =items.get(i).getName();
                        if(name=="كل المناطق "){
                            if(numberOfChoices.contains("إضافة كل المدن")){
                                numberOfChoices.remove("إضافة كل المدن");
                            }
                              for (int n = 0 ; n<numberOfChoices.size(); n++ ){
                                 final int finalN = n;
                                 Toast.makeText(getApplicationContext(),numberOfChoices.get(finalN) + "   الأختيار",Toast.LENGTH_LONG).show();
                                 arrayList.add(numberOfChoices.get(finalN));
                                  final ArrayList<String> arreasCity = new ArrayList<>();

                                 mDatabase.child("users").child("Governorate").child(nameGavernorate).child("citys").child("cityNameHash").child(numberOfChoices.get(finalN)).child("areas").child("areasHs").addListenerForSingleValueEvent(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(DataSnapshot dataSnapshot) {

                                         if (dataSnapshot.exists()) {
                                             if (dataSnapshot != null) {
                                                 for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                     StringBuilder spinnerBuffer = new StringBuilder();
                                                     String servisename = dataSnapshot1.getKey();
                                                     String value = dataSnapshot1.getValue(String.class);
                                                     if(value!=null){
                                                         arreasCity.add(value);
                                                         if(arreasCity.contains("areasHs")){
                                                             arreasCity.remove("areasHs");
                                                         }
                                                         CiysArea.put(numberOfChoices.get(finalN),arreasCity);
                                                     }else{
                                                         CiysArea.put(numberOfChoices.get(finalN),numberOfChoices.get(finalN));
                                                         if(!CiysArea.isEmpty()){

                                                             PlacesOfGaverNorate.put(nameGavernorate,CiysArea);
                                                         }


                                                     }





                                                 }


                                             }else {
                                                 CiysArea.put(numberOfChoices.get(finalN),numberOfChoices.get(finalN));
                                                 if(!CiysArea.isEmpty()){

                                                     PlacesOfGaverNorate.put(nameGavernorate,CiysArea);
                                                 }


                                             }





                                         }else{

                                             CiysArea.put(numberOfChoices.get(finalN),numberOfChoices.get(finalN));




                                         }
                                         if(!CiysArea.isEmpty()){

                                             PlacesOfGaverNorate.put(nameGavernorate,CiysArea);
                                         }
//

                                     }





                                     @Override
                                     public void onCancelled(DatabaseError databaseError) {

                                     }
                                 });






                              }



                        }else {
                            PlacesOfPlaces.put(items.get(i).getName(),items.get(i).getName());
                            arrayList.add(items.get(i).getName());

                        }

                    }
                }






            }
        });


        CompletService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashNumber = new HashMap<>();
                if (!numberOfChoices.isEmpty()){
                    for(int i = 0 ; i <numberOfChoices.size();i++){
                        HashNumber.put(numberOfChoices.get(i),numberOfChoices.get(i));
                    }
//                    PlacesOfGaverNorate.put(nameGavernorate,HashNumber);

                }

                if (latutuied==null&&longtuti==null){
                    latutuied=latutuiednew;
                    longtuti=longtutinew;
                    Toast.makeText(getApplicationContext(),
                            "تمت الإضافه بنفس الموقع ", Toast.LENGTH_LONG).show();
                }


                if(name_of_place.getText().toString()==""){
                    Toast.makeText(getApplicationContext(),
                            "أدخل إسم المكان ", Toast.LENGTH_LONG).show();

                }else  if(PlacesOfGaverNorate.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "الهاش فاضي ", Toast.LENGTH_LONG).show();
                }

                else {
                    for (int i=0 ;i<MltyChoseService.size();i++){
////                        AddPlaceService(MltyChoseService.get(i), PlacesOfGaverNorate);
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
        latutuiednew = 30.1229752 ;
        longtutinew =31.3025158 ;
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

package governmental.service.egypt;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import governmental.service.egypt.Adaptors.AdatorOfareas;
import governmental.service.egypt.Adaptors.ChosePaperDataAdapter;
import governmental.service.egypt.data.GavernoratWithLocation;
import governmental.service.egypt.data.Service;
import governmental.service.egypt.data.places;

public class UpdatePlace extends AppCompatActivity {

    RecyclerView recyclerView ;
    ArrayList<String> data ;
    ChosePaperDataAdapter adapter ;
    DatabaseReference mDatabase;
    Spinner spinner_service ,spinner_typeService;
    String spinner_service_item ,spinner_typeService_item;
    ArrayList<String> categories_Spinner_one ;
    ArrayList<String> categories_Spinner_two ;
    ArrayList<GavernoratWithLocation> gavernoratWithLocations;
    ArrayList<String> AreastArarry;
    AdatorOfareas adatorOfareas ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_place);


        AreastArarry = new ArrayList<>();
        categories_Spinner_one=new ArrayList<>();
        categories_Spinner_one.add("أختار خدمه");
        categories_Spinner_two=new ArrayList<>();
        gavernoratWithLocations = new ArrayList<>();
        spinner_service=(Spinner)findViewById(R.id.spinner_service);

        spinner_typeService=(Spinner)findViewById(R.id.spinner_typeService);
        data= new ArrayList<>();
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("Service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                        Service value = dataSnapshot1.getValue(Service.class);
                        String servisename =value.getNameService();

                        categories_Spinner_one.add(servisename);


                    }
                }else{
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories_Spinner_one);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_service.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
        categories_Spinner_two.add("أختار نوع اخدمة");

        spinner_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                spinner_service_item = parent.getItemAtPosition(position).toString();
                if(spinner_service_item=="أختار خدمه"){
                    Toast.makeText(getApplicationContext(), "من فضلك إختار خدمة", Toast.LENGTH_SHORT).show();
                }else{

                    mDatabase.child("users").child("Service").child(spinner_service_item).child("places").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    String value = dataSnapshot1.getKey();
                                    places places = dataSnapshot1.getValue(places.class);
//                        Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
//                        String value=places.getName();
                                    double latitude = places.getLatitude();
                                    Location location = new Location("");//provider name is unnecessary
                                    location.setLatitude(places.getLatitude());//your coords of course
                                    location.setLongitude(places.getLongitude());
                                    LatLng sydney = new LatLng(places.getLatitude(), places.getLongitude());
//                                    distanceArray.add(sydney);
                                    GavernoratWithLocation gavernoratWithLocation = new GavernoratWithLocation(value, sydney);
                                    if (!gavernoratWithLocations.contains(gavernoratWithLocation)) {
                                        gavernoratWithLocations.add(gavernoratWithLocation);
                                    }
                                    AreastArarry.add(value);
                                    data.add(value);


                                }

                                adatorOfareas = new AdatorOfareas(gavernoratWithLocations, UpdatePlace.this );
                                recyclerView.setAdapter(adatorOfareas);
                                adatorOfareas.notifyDataSetChanged();



                            }





                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });

                }



                // Showing selected spinner item
                Toast.makeText(parent.getContext(),   spinner_service_item, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });





    }
}

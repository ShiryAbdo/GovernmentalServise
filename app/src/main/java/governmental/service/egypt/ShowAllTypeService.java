package governmental.service.egypt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import governmental.service.egypt.Adaptors.ChoseServiceDataAdapter;
import governmental.service.egypt.Adaptors.ShowAllTypeService_Adaptor;
import governmental.service.egypt.data.Service;

public class ShowAllTypeService extends AppCompatActivity {
    Spinner spinner ;
    ArrayList<String> categories ;
    private DatabaseReference mDatabase;
    Button CompletService ;
     String item ;
    ImageButton search ;
    String spinner_service_item;
    ArrayList<String> categories_Spinner_two ;

    RecyclerView recyclerView ;
    ArrayList<String> data ;
    ShowAllTypeService_Adaptor adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_type_service);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        search =(ImageButton)findViewById(R.id.search);
        categories_Spinner_two=new ArrayList<>();
        // Spinner element
        spinner= (Spinner) findViewById(R.id.spinner);
        // Spinner Drop down elements
        categories = new ArrayList<String>();
        categories.add("أختار خدمه");
        CompletService=(Button)findViewById(R.id.CompletService);

        data= new ArrayList<>();
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        mDatabase.child("users").child("Service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                        Service value = dataSnapshot1.getValue(Service.class);
                        Service fire = new Service();
                        String servisename =value.getNameService();
                        categories.add(servisename);
                    }
                }else{
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                spinner_service_item = parent.getItemAtPosition(position).toString();
                if(spinner_service_item=="أختار خدمه"){
                    Toast.makeText(getApplicationContext(), "من فضلك إختار خدمة", Toast.LENGTH_SHORT).show();
                }else{

                    mDatabase.child("users").child("Service").child(spinner_service_item).child("typeOfSerVICE").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                    String name =dataSnapshot1.getKey();

//                                TyprServis value = dataSnapshot1.getValue(TyprServis.class);
//                                String servisename =value.getTyprServis();


                                    categories_Spinner_two.add(name);


                                    adapter = new ShowAllTypeService_Adaptor(categories_Spinner_two,ShowAllTypeService.this ,spinner_service_item);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }
                            }else{
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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowAllTypeService.this,   ServiceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

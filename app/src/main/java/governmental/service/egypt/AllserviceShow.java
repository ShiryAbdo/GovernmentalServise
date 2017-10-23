package governmental.service.egypt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import governmental.service.egypt.Adaptors.ChoseServiceDataAdapter;
import governmental.service.egypt.data.Service;

public class AllserviceShow extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String item ;
    ArrayList<String> categories ;
    RecyclerView recyclerView ;
    ArrayList<String> data ;
    ChoseServiceDataAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allservice_show);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        categories = new ArrayList<String>();

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
                        data.add(servisename);
                        adapter = new ChoseServiceDataAdapter(data,AllserviceShow.this);
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
}

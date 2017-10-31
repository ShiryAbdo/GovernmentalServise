package governmental.service.egypt.dialogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import governmental.service.egypt.R;
import governmental.service.egypt.data.Service;
import governmental.service.egypt.data.TyprServis;

public class AddTypeServiceDialog extends AppCompatActivity {
    Spinner spinner ;
    ArrayList<String> categories ;
    private DatabaseReference mDatabase;
    Button CompletService ;
    EditText addTyprServiseText ;
    String item ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type_service_dialog);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        addTyprServiseText=(EditText)findViewById(R.id.addTyprServiseText);

        // Spinner element
        spinner= (Spinner) findViewById(R.id.spinner);
         // Spinner Drop down elements
        categories = new ArrayList<String>();
        categories.add("أختار خدمه");
        CompletService=(Button)findViewById(R.id.CompletService);


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
                 if( parent.getItemAtPosition(position).toString().equals("أختار خدمه")){
                    Toast.makeText(parent.getContext(), "من فضلك قوم بإختيارالخدمة", Toast.LENGTH_LONG).show();
                }else {
                    item = parent.getItemAtPosition(position).toString();
                }
                // Showing selected spinner item
                Toast.makeText(parent.getContext(),  ""+ item, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        CompletService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item==null){
                    Toast.makeText(getApplicationContext(), "من فضلك قوم بإختيارالخدمة", Toast.LENGTH_LONG).show();
                }else {

                    if (TextUtils.isEmpty(addTyprServiseText.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "من فضلك أدخل البيانات ", Toast.LENGTH_SHORT).show();
                        return;
                    }else{

                        mDatabase.child("users").child("Service").child(item).child(addTyprServiseText.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Toast.makeText(getApplicationContext(), "نوع الخدممة موجوده ", Toast.LENGTH_SHORT).show();
                                }else{
                                    AddTypeService(addTyprServiseText.getText().toString().trim(),item);
                                    Toast.makeText(getApplicationContext(), "تم الإضافه بنجاح", Toast.LENGTH_SHORT).show();
                                    AddTypeServiceDialog.this.finish();
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
    }

    private void AddTypeService (String  TyprServis ,String service ) {
        TyprServis typrServis = new TyprServis(TyprServis);

        mDatabase.child("users").child("Service").child(service).child("typeOfSerVICE").child(TyprServis).setValue(typrServis);
    }


}

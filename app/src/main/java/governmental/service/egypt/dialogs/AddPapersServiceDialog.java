package governmental.service.egypt.dialogs;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import governmental.service.egypt.Adaptors.PaperDataAdapter;
import governmental.service.egypt.R;
import governmental.service.egypt.data.Service;
import governmental.service.egypt.data.TyprServis;

public class AddPapersServiceDialog extends AppCompatActivity {
    Spinner spinner_service  ,spinner_typeService;
    TextView addTyprServiseText ;
    RecyclerView recyclerView ;
   ArrayList<String>data;
    PaperDataAdapter adapter ;
    ImageView addPaperBt ;
    private DatabaseReference mDatabase;
    String spinner_service_item  ,spinner_typeService_item;
    ArrayList<String> categories_Spinner_one ;
    ArrayList<String> categories_Spinner_two ;
    Button CompletService ;
    Map<String, Object> papers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_papers_service_dialog);
        data= new ArrayList<>();
        papers = new HashMap<>();
        categories_Spinner_one=new ArrayList<>();
        categories_Spinner_one.add("أختار خدمه");
        categories_Spinner_two=new ArrayList<>();
        addPaperBt=(ImageView)findViewById(R.id.addPaperBt);
        spinner_service=(Spinner)findViewById(R.id.spinner_service);
        spinner_typeService=(Spinner)findViewById(R.id.spinner_typeService);
        addTyprServiseText =(TextView)findViewById(R.id.addTyprServiseText);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new PaperDataAdapter(AddPapersServiceDialog.this,data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        CompletService =(Button)findViewById(R.id.CompletService);
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

                    mDatabase.child("users").child("Service").child(spinner_service_item).child("typeOfSerVICE").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                    String name =dataSnapshot1.getKey();

//                                TyprServis value = dataSnapshot1.getValue(TyprServis.class);
//                                String servisename =value.getTyprServis();


                                    categories_Spinner_two.add(name);

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

        ArrayAdapter<String> dataAdater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories_Spinner_two);

        // Drop down layout style - list view with radio button
        dataAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_typeService.setAdapter(dataAdater);
        dataAdater.notifyDataSetChanged();





        spinner_typeService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                spinner_typeService_item = parent.getItemAtPosition(position).toString();
                if(spinner_typeService_item=="أختار نوع اخدمة"){
                    Toast.makeText(getApplicationContext(), "من فضلك إختار نوع  الخدمة", Toast.LENGTH_SHORT).show();
                }

                // Showing selected spinner item
                Toast.makeText(parent.getContext(),   spinner_typeService_item, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        addPaperBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner_typeService_item=="أختار نوع اخدمة" &&spinner_service_item=="أختار خدمه"){
                    Toast.makeText(getApplicationContext(), "منفضلك حدد الخدمة و نوع الخدمة اولا", Toast.LENGTH_LONG).show();

                }else{
                    @SuppressLint("RestrictedApi") final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(AddPapersServiceDialog.this, R.style.TransparentDialog));
                    View alertView;
                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                    alertView = inflater.inflate(R.layout.custom_alert_layout, null);
                    dialogBuilder.setView(alertView);
                    dialogBuilder.setCancelable(true);
                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogBuilder.setCancelable(true);


                    final EditText addServiseText = (EditText)alertView.findViewById(R.id.addServiseText) ;
                    Button CompletService =(Button)alertView.findViewById(R.id.CompletService);

                    CompletService.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(data.contains(addServiseText.getText().toString().trim())){
                                Toast.makeText(getApplicationContext(), "تم إضافتها مسبقا", Toast.LENGTH_SHORT).show();
                            }else{
                                if(!addServiseText.getText().toString().trim().equals("")&&addServiseText.getText().toString().trim()!=null){
                                    data.add(addServiseText.getText().toString().trim());
                                    papers.put(addServiseText.getText().toString().trim(),addServiseText.getText().toString().trim());
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    addServiseText.setText("");
                                    Toast.makeText(getApplicationContext(), "تم الإضافه ", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "قم بإدخال البيانات", Toast.LENGTH_SHORT).show();

                                }

                            }


                        }
                    });

                    alertDialog.show();
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(alertDialog.getWindow().getAttributes());
                    lp.width = 600;
                    lp.height = 800;
                    alertDialog.getWindow().setAttributes(lp);
                }





            }
        });


        CompletService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner_service_item!=null&&spinner_typeService_item!=null&&data!=null){


                     if(spinner_typeService_item=="أختار نوع اخدمة"){
                        Toast.makeText(getApplicationContext(), "من فضلك إختار نوع  الخدمة", Toast.LENGTH_SHORT).show();
                    }else {
                         AddPaper(spinner_service_item,spinner_typeService_item,papers);
                         Toast.makeText(getApplicationContext(), "تم الإضافه ", Toast.LENGTH_SHORT).show();
                     }

                }else {
                    Toast.makeText(getApplicationContext(), "أضف البيانات ", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void AddPaper (String  serviceName ,String typeOservice,Map<String, Object> papers ) {

        mDatabase.child("users").child("Service").child(serviceName).child("typeOfSerVICE").child(typeOservice).child("papers").setValue(papers);
    }
}

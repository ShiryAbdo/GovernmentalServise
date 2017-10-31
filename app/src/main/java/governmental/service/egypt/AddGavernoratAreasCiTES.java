package governmental.service.egypt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import governmental.service.egypt.data.Governorate;
import governmental.service.egypt.dialogs.AreaDialog;
import governmental.service.egypt.dialogs.CityDialoge;

public class AddGavernoratAreasCiTES extends AppCompatActivity {
    Button AddGaverNorat  ,AddCites ,AddAres ;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_add_gavernorat_areas_ci_tes);
        AddGaverNorat =(Button)findViewById(R.id.AddGaverNorat);
        AddCites=(Button)findViewById(R.id.AddCites);
        AddAres=(Button)findViewById(R.id.AddAres);

        AddGaverNorat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                @SuppressLint("RestrictedApi") final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(AddGavernoratAreasCiTES.this, R.style.TransparentDialog));
                View alertView;
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                alertView = inflater.inflate(R.layout.add_gavernorate, null);
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

                        if (TextUtils.isEmpty(addServiseText.getText().toString().trim())) {
                            Toast.makeText(getApplicationContext(), "من فضلك أدخل البيانات ", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            mDatabase.child("users").child("Service").child("Governorate").child(addServiseText.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Toast.makeText(getApplicationContext(), "المحافظة موجوده مسبقا ", Toast.LENGTH_SHORT).show();


                                    }else{
                                        AddGaverNorate(addServiseText.getText().toString().trim());
                                        Toast.makeText(getApplicationContext(), "تم الإضافه بنجاح", Toast.LENGTH_SHORT).show();
                                        alertDialog.cancel();
                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
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
        });


//        ************************** add Citys **************
        AddCites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CityDialoge.class);
                startActivity(intent);

            }
        });
        AddAres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AreaDialog.class);
                startActivity(intent);
            }
        });


    }



    private void AddGaverNorate (String  GavernorateName  ) {
        Governorate governorate= new Governorate(GavernorateName);

        mDatabase.child("users").child("Governorate").child(GavernorateName).setValue(governorate);
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(AddGavernoratAreasCiTES.this,ServiceActivity.class);
        startActivity(setIntent);
        finish();
    }
}

package governmental.service.egypt.dialogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import governmental.service.egypt.R;
import governmental.service.egypt.data.Service;
import governmental.service.egypt.data.users;

public class AddServiceDialog extends AppCompatActivity {
    private DatabaseReference mDatabase;
    EditText addServiseText ;
    Button CompletService ;
    FirebaseUser user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        addServiseText =(EditText)findViewById(R.id.addServiseText);
        CompletService=(Button)findViewById(R.id.CompletService);
        CompletService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(addServiseText.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "من فضلك أدخل البيانات ", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    mDatabase.child("users").child("Service").child(addServiseText.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getApplicationContext(), "الخدممة موجوده ", Toast.LENGTH_SHORT).show();
                            }else{
                                AddService(addServiseText.getText().toString().trim());
                                Toast.makeText(getApplicationContext(), "تم الإضافه بنجاح", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }





            }
        });

    }


    private void AddService (String  serviceName  ) {
        Service  service= new Service(serviceName);

        mDatabase.child("users").child("Service").child(serviceName).setValue(service);
    }
}

package governmental.service.egypt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import governmental.service.egypt.data.TyprServis;
import governmental.service.egypt.data.users;

public class GetData extends AppCompatActivity {
      DatabaseReference mDatabase;
      TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        textView=(TextView)findViewById(R.id.textView);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        users users = new users("email","pass", "asmaa");

         mDatabase.child("users").child("Service").child("ننن").child("typeOfSerVICE").addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                     TyprServis value = dataSnapshot1.getValue(TyprServis.class);
                    String servisename =value.getTyprServis();
                    textView.setText( servisename);

                }


//               if (username.equals(mUserView.getText().toString())) {
//                   alreadyRegisteredAccount++;
//               }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package governmental.service.egypt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import governmental.service.egypt.dialogs.AddPapersServiceDialog;
import governmental.service.egypt.dialogs.AddServiceDialog;
import governmental.service.egypt.dialogs.AddTypeServiceDialog;
import governmental.service.egypt.dialogs.PlacesServiceDialog;

public class ServiceActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    Button addService ,addTypeService,addPapersService,PlacesService,setting ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        addService=(Button)findViewById(R.id.addService);
        addTypeService=(Button)findViewById(R.id.addTypeService);
        addPapersService=(Button)findViewById(R.id.addPapersService);
        PlacesService=(Button)findViewById(R.id.PlacesService);
        setting=(Button)findViewById(R.id.setting);

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),AddServiceDialog.class);
                startActivity(intent);

            }
        });
        addTypeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),AddTypeServiceDialog.class);
                startActivity(intent);

            }
        });
        addPapersService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),AddPapersServiceDialog.class);
                startActivity(intent);

            }
        });
        PlacesService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(getApplicationContext(),PlacesServiceDialog.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }
}

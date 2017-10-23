package governmental.service.egypt.Adaptors;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import governmental.service.egypt.R;
import governmental.service.egypt.data.Service;

public class ChoseServiceDataAdapter  extends RecyclerView.Adapter<ChoseServiceDataAdapter.ViewHolder> {
    private ArrayList<String> androidList;
    private Context context;
    private int lastPosition=-1;
      DatabaseReference mDatabase;
    public ChoseServiceDataAdapter(ArrayList<String> android,Context c) {
        this.androidList = android;
        this.context=c;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public ChoseServiceDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout. all_servise_row
                , viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChoseServiceDataAdapter.ViewHolder viewHolder, final int i) {


        viewHolder.cintery_name.setText(androidList.get(i));
        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,androidList.get(i),Toast.LENGTH_LONG).show();

//                Intent intent = new Intent(context,ChoseTypeService.class);

//                intent.putExtra("serviceName",androidList.get(i));
//                context.startActivity(intent);
            }
        });
        setAnimation(viewHolder.card, i);

        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                mDatabase = FirebaseDatabase.getInstance().getReference();
                final Dialog dialog = new Dialog(context, R.style.custom_dialog_theme);
                dialog.setContentView(R.layout.update_data_servise);
                final EditText UpdateServiseText =(EditText)dialog.findViewById(R.id.UpdateServiseText);
                UpdateServiseText.setText(androidList.get(i));
                Button UpdateService = (Button) dialog.findViewById(R.id.UpdateService);
                Button DeletService =(Button)dialog.findViewById(R.id.DeletService);
                // if button is clicked, close the custom dialog
                UpdateService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UpdateServiseText.getText().toString().trim().equals(androidList.get(i))){
                            Toast.makeText(context, "لم تقم بأي تعديل ", Toast.LENGTH_SHORT).show();

                        }else{
                            if(!UpdateServiseText.getText().toString().trim().equals("")&&UpdateServiseText.getText().toString().trim()!=null){
                                mDatabase.child("users").child("Service").child(androidList.get(i)).removeValue();
                                AddService(UpdateServiseText.getText().toString().trim());
                                androidList.remove(i);
                                notifyItemRemoved(i);
                                androidList.add(UpdateServiseText.getText().toString().trim());
                                notifyItemRangeChanged(i,androidList.size());
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }else{
                                Toast.makeText(context, "من فضلك أدخل إسم الخدمه", Toast.LENGTH_SHORT).show();
                            }


                        }

                    }
                });
                DeletService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child("users").child("Service").child(androidList.get(i)).removeValue();
                        androidList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i,androidList.size());
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

    }


    private void AddService (String  serviceName  ) {
        Service service= new Service(serviceName);

        mDatabase.child("users").child("Service").child(serviceName).setValue(service);
    }


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return androidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView factory_name,cintery_name,email_factory;
        private LinearLayout card;
        private ImageButton  update ;
        public ViewHolder(View view) {
            super(view);
            card=(LinearLayout)view.findViewById(R.id.cardView);
            cintery_name = (TextView)view.findViewById(R.id.title);
            update =(ImageButton)view.findViewById(R.id.update);

        }
    }

}
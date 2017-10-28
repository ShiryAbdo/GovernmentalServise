package governmental.service.egypt.Adaptors;

import android.app.Dialog;
import android.content.Context;
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


/**
 * Created by falcon on 02/10/2017.
 */

public class ChosePaperDataAdapter extends RecyclerView.Adapter<ChosePaperDataAdapter.ViewHolder> {
    private ArrayList<String> androidList;
    private Context context;
    private int lastPosition=-1;
    DatabaseReference mDatabase;
    String serviceName , TypeServiceNmae ;

    public ChosePaperDataAdapter(ArrayList<String> android, Context c ,String service_Name,String Type_ServiceNmae) {
        this.androidList = android;
        this.context=c;
        this.serviceName=service_Name;
        this.TypeServiceNmae =Type_ServiceNmae;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public ChosePaperDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_paper, viewGroup, false);


        return new ChosePaperDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChosePaperDataAdapter.ViewHolder viewHolder, final int i) {

        notifyDataSetChanged();
        viewHolder.cintery_name.setText(androidList.get(i));
        viewHolder.position.setText((i+1)+"");
        notifyDataSetChanged();
         viewHolder.update.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 mDatabase = FirebaseDatabase.getInstance().getReference();
                 final Dialog dialog = new Dialog(context, R.style.custom_dialog_theme);
                 dialog.setContentView(R.layout.update_data_servise);
                 final EditText UpdateServiseText =(EditText)dialog.findViewById(R.id.UpdateServiseText);
                 UpdateServiseText.setText(androidList.get(i));
                 Button UpdateService = (Button) dialog.findViewById(R.id.UpdateService);
                 Button DeletService =(Button)dialog.findViewById(R.id.DeletService);
                 Button CANCEL = (Button)dialog.findViewById(R.id.CANCEL);
                 CANCEL.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialog.dismiss();
                     }
                 });
                 // if button is clicked, close the custom dialog
                 UpdateService.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if(UpdateServiseText.getText().toString().trim().equals(androidList.get(i))){
                             Toast.makeText(context, "لم تقم بأي تعديل ", Toast.LENGTH_SHORT).show();

                         }else{
                             if(!UpdateServiseText.getText().toString().trim().equals("")&&UpdateServiseText.getText().toString().trim()!=null){
                                 mDatabase.child("users").child("Service").child(serviceName).child("typeOfSerVICE").child(TypeServiceNmae).child("papers").child(androidList.get(i)).removeValue();
                                 mDatabase.child("users").child("Service").child(serviceName).child("typeOfSerVICE").child(TypeServiceNmae).child("papers").child(UpdateServiseText.getText().toString().trim()).setValue(UpdateServiseText.getText().toString().trim());
//                                 AddTypeService(UpdateServiseText.getText().toString().trim(),ServiceName);

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
                         mDatabase.child("users").child("Service").child(serviceName).child("typeOfSerVICE").child(TypeServiceNmae).child("papers").child(androidList.get(i)).removeValue();
                         androidList.remove(i);
                         notifyItemRemoved(i);
                         notifyItemRangeChanged(i,androidList.size());
                         dialog.dismiss();

                     }
                 });

                 dialog.show();

             }
         });



        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setAnimation(viewHolder.card, i);

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
        private TextView position,cintery_name,email_factory;
        private LinearLayout card;
        private ImageButton update ;
        public ViewHolder(View view) {
            super(view);
            card=(LinearLayout)view.findViewById(R.id.cardView);
            cintery_name = (TextView)view.findViewById(R.id.title);
            position = (TextView)view.findViewById(R.id.position);
            update =(ImageButton)view.findViewById(R.id.update);

        }
    }

}
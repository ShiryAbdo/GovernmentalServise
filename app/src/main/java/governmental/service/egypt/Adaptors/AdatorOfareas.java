package governmental.service.egypt.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import governmental.service.egypt.ContunioUpdatingPlaces;
import governmental.service.egypt.R;
import governmental.service.egypt.UpdatePlace;
import governmental.service.egypt.data.GavernoratWithLocation;
import governmental.service.egypt.data.places;

/**
 * Created by falcon on 28/10/2017.
 */

public class AdatorOfareas extends RecyclerView.Adapter<AdatorOfareas.ViewHolder> {
    private ArrayList<GavernoratWithLocation> androidList;
    private Context context;
    private int lastPosition=-1;
    LatLng location;
    String  service_item ;
    DatabaseReference mDatabase;
    String value;
 Intent intent ;
    public AdatorOfareas(ArrayList<GavernoratWithLocation> android,Context c  ,String service  ) {
        this.androidList = android;
        this.context=c;
        this.service_item=service;
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public AdatorOfareas.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout. row_paper, viewGroup, false);


        return new AdatorOfareas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdatorOfareas.ViewHolder viewHolder, final int i) {


        viewHolder.cintery_name.setText(androidList.get(i).getGovernoratname());
        viewHolder.position.setText("أعرض المسافه");

//        if(!distance_nm.isEmpty()){
//            viewHolder.position.setText(distance_nm.get(i));
//        }


        viewHolder.cintery_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+androidList.get(i).getLocatio().latitude+","+androidList.get(i).getLocatio().longitude);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri);
                context.startActivity(intent);

            }
        });
        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child("users").child("Service").child(service_item).child("places").child(androidList.get(i).getGovernoratname()).child("OtherplacesOfService").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                             for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                value = dataSnapshot1.getKey();
                                 intent = new Intent(context, ContunioUpdatingPlaces.class);
                                 intent.putExtra("latitude",androidList.get(i).getLocatio().latitude);
                                 intent.putExtra("longitude",androidList.get(i).getLocatio().longitude);
                                 intent.putExtra("service",service_item);
                                 intent.putExtra("nameLocation",androidList.get(i).getGovernoratname());
                                 intent.putExtra("gavernorate",value);
                                 context.startActivity(intent);


                            }


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });


            }
        });

        viewHolder.position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+androidList.get(i).getLocatio().latitude+","+androidList.get(i).getLocatio().longitude);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri);
                context.startActivity(intent);


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

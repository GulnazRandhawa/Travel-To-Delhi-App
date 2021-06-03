package example.com.tourismapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.tourismapp2.classpack.places_details;


public class ViewAllPlaces extends Fragment {

        RecyclerView rcv_managecategory_showcategory;
    ArrayList<places_details> al;
MyRecyclerAdapter myad;
DatabaseReference mainref;
    public ViewAllPlaces() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_all_places, container, false);

        al=new ArrayList<places_details>();

        rcv_managecategory_showcategory=v.findViewById(R.id.view_all_places);

        myad=new MyRecyclerAdapter();

        rcv_managecategory_showcategory.setAdapter(myad);

        //Specifying Layout Manager to RecyclerView is Compulsary for Proper Rendering

        LinearLayoutManager simpleverticallayout= new LinearLayoutManager(getContext());
        rcv_managecategory_showcategory.setLayoutManager(simpleverticallayout);
        mainref = FirebaseDatabase.getInstance().getReference("Places");
        mainref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                al.clear();
                if(snapshot.exists()){
                    for (DataSnapshot sin : snapshot.getChildren()){
                        places_details obj = sin.getValue(places_details.class);
                        al.add(obj);
                    }
                    myad.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;
    }

        class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>
    {

        // Define ur own View Holder (Refers to Single Row)
        class MyViewHolder extends RecyclerView.ViewHolder
        {
            CardView singlecardview;

            // We have Changed View (which represent single row) to CardView in whole code

            public MyViewHolder(CardView itemView) {

                super(itemView);
                singlecardview = (itemView);
            }

        }


        // Inflate ur Single Row / CardView from XML here
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater  = LayoutInflater.from(parent.getContext());

            View viewthatcontainscardview = inflater.inflate(R.layout.cardviewlocations,parent,false);
            CardView cardView = (CardView) (viewthatcontainscardview.findViewById(R.id.cardview_category));
            return new MyViewHolder(cardView);
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            CardView localcardview = holder.singlecardview;
            ImageView imv101;
            TextView tv_catname,tv_catdesc;
            imv101=(ImageView)(localcardview.findViewById(R.id.imvcardview_catphoto));
            tv_catname=(TextView)(localcardview.findViewById(R.id.tvcardview_catname));

//
            places_details cat=al.get(position);
            tv_catname.setText(cat.getPlace_name());

            Picasso.get().load(cat.getImages()).resize(500,500).centerInside().into(imv101);
            localcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getContext(),View_Place_detail.class);
                    in.putExtra("obj",cat);
                    startActivity(in);
                }
            });
        }
        @Override
        public int getItemCount() {
            return al.size();
        }
    }
}
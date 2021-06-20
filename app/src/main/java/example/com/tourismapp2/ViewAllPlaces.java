package example.com.tourismapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
EditText searchEt;
    public ViewAllPlaces() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.logout5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreference=getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreference.edit();
                editor.clear();
                editor.apply();
                getActivity().finish();
                Intent intent=new Intent(getActivity(),Login_Signup.class);
                startActivity(intent);



            }
        });
        searchEt=getActivity().findViewById(R.id.searchEt);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });
    }

    void filter(String text){
        ArrayList<places_details> temp = new ArrayList();
        for(places_details d: al){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getPlace_name().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        myad.updateList(temp);
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
                    myad.setArrayList(al);
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

        ArrayList<places_details> arrayList=new ArrayList<>();

        // Define ur own View Holder (Refers to Single Row)
        class MyViewHolder extends RecyclerView.ViewHolder
        {
            CardView singlecardview;

            // We have Changed View (which represent single row) to CardView in whole code

            public MyViewHolder(View itemView) {

                super(itemView);
                singlecardview = (itemView.findViewById(R.id.card));
            }

        }





        // Inflate ur Single Row / CardView from XML here
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater  = LayoutInflater.from(parent.getContext());

            View viewthatcontainscardview = inflater.inflate(R.layout.cardviewlocations,parent,false);
//            LinearLayout cardView = (CardView) (viewthatcontainscardview.findViewById(R.id.cardview_category));
            return new MyViewHolder(viewthatcontainscardview);
        }

        public void setArrayList(ArrayList<places_details> arrayList) {
            this.arrayList = arrayList;
        }

        public void updateList(ArrayList<places_details> places_details)
        {
            arrayList=places_details;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            CardView localcardview = holder.singlecardview;
            ImageView imv101;
            TextView tv_place_name,tv_catdesc;
            imv101=(ImageView)(localcardview.findViewById(R.id.imvcardview_catphoto));
            tv_place_name=(TextView)(holder.itemView.findViewById(R.id.tvcardview_catname));
            TextView tvcardview_cat=holder.itemView.findViewById(R.id.tvcardview_cat);


//
            places_details places_details_obj=arrayList.get(position);

            tv_place_name.setText(places_details_obj.getPlace_name());
            tvcardview_cat.setText(places_details_obj.getRating());

            Picasso.get().load(places_details_obj.getImages()).resize(500,500).centerInside().into(imv101);
            localcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getContext(),View_Place_detail.class);
                    in.putExtra("obj",places_details_obj);
                    startActivity(in);

                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}
package example.com.tourismapp2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.tourismapp2.classpack.rating_details;

public class Manage_Reviews extends AppCompatActivity {

    String places_key;
    DatabaseReference mainref;
    ArrayList<rating_details> al = new ArrayList<>();
    RecyclerView rcv_view_all_reviews;
    MyRecyclerAdapter myad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__reviews);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        rcv_view_all_reviews =  findViewById(R.id.view_all_reviews);

        myad=new MyRecyclerAdapter();

        rcv_view_all_reviews.setAdapter(myad);

        //Specifying Layout Manager to RecyclerView is Compulsary for Proper Rendering

        LinearLayoutManager simpleverticallayout= new LinearLayoutManager(getApplicationContext());
        rcv_view_all_reviews.setLayoutManager(simpleverticallayout);
        //

        Intent in = getIntent();
        places_key = in.getStringExtra("places_key");

        mainref = FirebaseDatabase.getInstance().getReference("Ratings").child(places_key);

        mainref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                al.clear();
                if(snapshot.exists()){
                    
                    for (DataSnapshot sin  : snapshot.getChildren()){
                        rating_details obj = sin.getValue(rating_details.class);
                        al.add(obj);
                    }
                    myad.notifyDataSetChanged();
                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Post_Review.class);
                in.putExtra("places_key",places_key);
                startActivity(in);
            }
        });
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
        public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater  = LayoutInflater.from(parent.getContext());

            View viewthatcontainscardview = inflater.inflate(R.layout.rating_single_row_design,parent,false);
            CardView cardView = (CardView) (viewthatcontainscardview.findViewById(R.id.cardview_category));
            return new MyRecyclerAdapter.MyViewHolder(cardView);
        }


        @Override
        public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, final int position) {

            CardView localcardview = holder.singlecardview;

            TextView review_name,review_mail;
            review_name=(TextView)(localcardview.findViewById(R.id.review_name));
            review_mail=(TextView)(localcardview.findViewById(R.id.review_mail2));

//
            rating_details rating_details_obj=al.get(position);
            review_name.setText(rating_details_obj.getEmailid());
review_mail.setText(rating_details_obj.getComment());


        }
        @Override
        public int getItemCount() {
            return al.size();
        }
    }

}
package example.com.tourismapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import example.com.tourismapp2.classpack.HotelDetail;
import example.com.tourismapp2.classpack.Utils;
import example.com.tourismapp2.classpack.places_details;
import example.com.tourismapp2.classpack.rating_details;
import example.com.tourismapp2.classpack.user_added_fav_places_details;

public class View_Place_detail extends AppCompatActivity {

    TextView placename,rating2;
    TextView placedesc,all_reviews;

    ImageView img_add_calaneder,img_add_fav;
    DatabaseReference fav_mainref;
    boolean flag = false;
    RecyclerView popularDesRv,ratingRv;
    MyRecyclerViewAdapter adapter;
    ImageSlider imageSlider;
    MyRecyclerViewAdapter1 adapter1;
    ArrayList<SlideModel>imagesList=new ArrayList<>();
    ArrayList<rating_details>ratingArrayList=new ArrayList<>();
   ArrayList<HotelDetail>hotelList=new ArrayList<>();
    places_details obj;
    boolean task1,task2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__place_detail);
        Intent in = getIntent();
      obj   = (places_details) in.getSerializableExtra("obj");

        //
        popularDesRv = findViewById(R.id.popularDesRv);

        adapter = new MyRecyclerViewAdapter();
        adapter1=new MyRecyclerViewAdapter1();
        popularDesRv.setAdapter(adapter);
        popularDesRv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        //
        fav_mainref = FirebaseDatabase.getInstance().getReference("User_Added_Fav_Places");
        placename = findViewById(R.id.placename);
        placedesc = findViewById(R.id.placedesc);
        all_reviews = findViewById(R.id.all_reviews);
        rating2 = findViewById(R.id.rating2);

        //
        rating2.setText(obj.getRating());
        //
        img_add_calaneder = findViewById(R.id.img_add_calaneder);
        img_add_fav = findViewById(R.id.img_add_fav);
        imageSlider=findViewById(R.id.image_slider);
        ratingRv=findViewById(R.id.ratingsRv);
        ratingRv.setAdapter(adapter1);
        ratingRv.setLayoutManager(new LinearLayoutManager(this));


        FirebaseDatabase.getInstance().getReference("Ratings").child(obj.getPush_key()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ratingArrayList.clear();
               if(snapshot.exists()){
                   for(DataSnapshot sin :snapshot.getChildren())
                   {
                       rating_details rating_details=sin.getValue(example.com.tourismapp2.classpack.rating_details.class);
                       ratingArrayList.add(rating_details);

                   }

                   ArrayList<rating_details>details=new ArrayList<>();
                   details.add(ratingArrayList.get(0));
                   adapter1.setRatingList(details);
               }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("hotels").child(obj.getPush_key()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                hotelList.clear();
                for(DataSnapshot sin:snapshot.getChildren())
                {
                    HotelDetail hotelDetail=sin.getValue(HotelDetail.class);
                    hotelList.add(hotelDetail);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        findViewById(R.id.addNewReview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(View_Place_detail.this,Post_Review.class);
                intent.putExtra("places_key",obj.getPush_key());
                startActivity(intent);
            }
        });
        all_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter1.setRatingList(ratingArrayList);
                adapter1.notifyDataSetChanged();
            }
        });

        FirebaseDatabase.getInstance().getReference("Images_Gallery").child(obj.getPush_key()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               if(snapshot.exists())
               {
                   imagesList.clear();
                   for(DataSnapshot sin:snapshot.getChildren())
                   {
                       String image=sin.getValue(String.class);
                       imagesList.add(new SlideModel(image, ScaleTypes.FIT));
                   }

                   imageSlider.setImageList(imagesList);
               }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        findViewById(R.id.back3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent=new Intent(View_Place_detail.this,HomeScreen.class);
                Intent.putExtra("VALUE",1);
                finish();
                startActivity(Intent);
            }
        });
       findViewById(R.id.logout8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Utils().logOut(View_Place_detail.this);
            }
        });


        placename.setText(obj.getPlace_name());
        placedesc.setText(obj.getDescription());
        SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
        String email = sharedPreference.getString("email","");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        Intent backIntent=new Intent(View_Place_detail.this,HomeScreen.class);

        //load View all places
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        //home

                        return true;
                    case R.id.navigation_dashboard:
                        //calender
                        backIntent.putExtra("VALUE",2);
                        finish();
                        startActivity(backIntent);
                        return true;
                    case R.id.navigation_notifications:
                        //fav
                        backIntent.putExtra("VALUE",3);
                        finish();
                        startActivity(backIntent);
                        return true;
                    case R.id.navigation_profile:
                        //profile
                        backIntent.putExtra("VALUE",4);
                        finish();
                        startActivity(backIntent);

                        return true;






                }


                return false;
            }
        });



//        all_reviews.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in = new Intent(getApplicationContext(),Manage_Reviews.class);
//                in.putExtra("places_key",obj.getPush_key()); // Sends that selected place's key to Manage reviews page
//                startActivity(in);
//
//            }
//        });
        //Working on 'Calendar' icon on home screen
        img_add_calaneder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Calendar.class);
                in.putExtra("place_id",obj.getPush_key()); // Sends that selected place's key to Calendpage
                startActivity(in);
            }
        });

        img_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check that same place is already added to fav list or not
                fav_mainref.orderByChild("user_email_id").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot sin : snapshot.getChildren()){
                                 user_added_fav_places_details temp_obj = sin.getValue(user_added_fav_places_details.class); //creating object of this class.

                                 if(temp_obj.getPlaces_id().equals(obj.getPush_key())){ // matching ID of place from places_details class with the ID from user_addded place .
                                     flag = true;
                                     break;
                                 }


                            }

                            if(flag){
                                Toast.makeText(View_Place_detail.this, "Already Exists !!", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                String key = fav_mainref.push().getKey();
                                user_added_fav_places_details fav_obj = new user_added_fav_places_details(key,email,obj.getPush_key()); // creating new object of this class to push to firebase.
                                fav_mainref.child(key).setValue(fav_obj);
                                backIntent.putExtra("VALUE",3);
                                finish();
                                startActivity(backIntent);

                                Toast.makeText(View_Place_detail.this, "Saved for Later", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreference.edit();
                                editor.putBoolean("TASK1",true);
                                editor.commit();



                            }

                        }
                        else { //checking for a New user who has not previously added any fav place.
                            String key = fav_mainref.push().getKey();
                            user_added_fav_places_details fav_obj = new user_added_fav_places_details(key,email,obj.getPush_key());
                            fav_mainref.child(key).setValue(fav_obj);
                            backIntent.putExtra("VALUE",3);
                            finish();
                            startActivity(backIntent);

                            Toast.makeText(View_Place_detail.this, "Saved for Later", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreference.edit();
                            editor.putBoolean("TASK1",true);
                            editor.commit();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });



    }
    //popular destinations adapter to display the objects from arraylist into recycler.
    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


        @NonNull
        @Override
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.popular_des_recycler_design, parent, false); // single row design
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
            View localcardview = holder.itemView;

            ImageView image_slider = localcardview.findViewById(R.id.image_slider);
            TextView gastown_van = localcardview.findViewById(R.id.gastown_van);

            Picasso.get().load(hotelList.get(position).getPic()).into(image_slider);
            gastown_van.setText(hotelList.get(position).getName());



        }

        @Override
        public int getItemCount() {
            return hotelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public class MyRecyclerViewAdapter1 extends RecyclerView.Adapter<MyRecyclerViewAdapter1.ViewHolder> {
    ArrayList<rating_details>ratingList=new ArrayList<>();

        @NonNull
        @Override
        public MyRecyclerViewAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.rating_design_rv, parent, false); // single row design
            return new ViewHolder(view);
        }

        public void setRatingList(ArrayList<rating_details>list)
        {
            ratingList=list;
        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter1.ViewHolder holder, int position) {
            View localcardview = holder.itemView;
          TextView lastReview=  holder.itemView.findViewById(R.id.lastreview_text);

          if (ratingList.get(position).getRating()==1)
          {
            holder.itemView.findViewById(R.id.star1).setVisibility(View.VISIBLE);
          }
          else if(ratingList.get(position).getRating()==2)
          {
              holder.itemView.findViewById(R.id.star1).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star2).setVisibility(View.VISIBLE);

          }
          else if(ratingList.get(position).getRating()==3)
          {
              holder.itemView.findViewById(R.id.star1).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star2).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star3).setVisibility(View.VISIBLE);


          }
          else if(ratingList.get(position).getRating()==4)
          {
              holder.itemView.findViewById(R.id.star1).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star2).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star3).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star4).setVisibility(View.VISIBLE);


          }
          else
          {

              holder.itemView.findViewById(R.id.star1).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star2).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star3).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star4).setVisibility(View.VISIBLE);
              holder.itemView.findViewById(R.id.star5).setVisibility(View.VISIBLE);

          }


            lastReview.setText(ratingList.get(position).getComment());
          ImageView imv_user_profile  = holder.itemView.findViewById(R.id.imv_user_profile);
          TextView lastreview_name  = holder.itemView.findViewById(R.id.lastreview_name);


          Picasso.get().load(ratingList.get(position).getPic()).into(imv_user_profile);
          lastreview_name.setText(ratingList.get(position).getName());



        }

        @Override
        public int getItemCount() {
            return ratingList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
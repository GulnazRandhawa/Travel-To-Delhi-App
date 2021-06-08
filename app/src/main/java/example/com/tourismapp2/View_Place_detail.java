package example.com.tourismapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import example.com.tourismapp2.classpack.places_details;
import example.com.tourismapp2.classpack.user_added_fav_places_details;

public class View_Place_detail extends AppCompatActivity {
    ImageView place_image;
    TextView placename;
    TextView placedesc,all_reviews;

    ImageView img_add_calaneder,img_add_fav;
    DatabaseReference fav_mainref;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__place_detail);
        Intent in = getIntent();
        places_details obj = (places_details) in.getSerializableExtra("obj");

        fav_mainref = FirebaseDatabase.getInstance().getReference("User_Added_Fav_Places");
        place_image = findViewById(R.id.place_image);
        placename = findViewById(R.id.placename);
        placedesc = findViewById(R.id.placedesc);
        all_reviews = findViewById(R.id.all_reviews);
        img_add_calaneder = findViewById(R.id.img_add_calaneder);
        img_add_fav = findViewById(R.id.img_add_fav);

        Picasso.get().load(obj.getImages()).into(place_image);
        placename.setText(obj.getPlace_name());
        placedesc.setText(obj.getDescription());
        SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
        String email = sharedPreference.getString("email","");


        all_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Manage_Reviews.class);
                in.putExtra("places_key",obj.getPush_key()); // Sends that selected place's key to Manage reviews page
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
                                 user_added_fav_places_details temp_obj = sin.getValue(user_added_fav_places_details.class);

                                 if(temp_obj.getPlaces_id().equals(obj.getPush_key())){
                                     flag = true;
                                     break;
                                 }


                            }

                            if(flag){
                                Toast.makeText(View_Place_detail.this, "This place s already exists into Fav List", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                String key = fav_mainref.push().getKey();
                                user_added_fav_places_details fav_obj = new user_added_fav_places_details(key,email,obj.getPush_key());
                                fav_mainref.child(key).setValue(fav_obj);
                                finish();
                                Toast.makeText(View_Place_detail.this, "Place Added TO fav List.", Toast.LENGTH_SHORT).show();

                            }

                        }
                        else {
                            String key = fav_mainref.push().getKey();
                            user_added_fav_places_details fav_obj = new user_added_fav_places_details(key,email,obj.getPush_key());
                            fav_mainref.child(key).setValue(fav_obj);
                            finish();
                            Toast.makeText(View_Place_detail.this, "Place Added TO fav List.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

    }
}
package example.com.tourismapp2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.tourismapp2.classpack.places_details;
import example.com.tourismapp2.classpack.rating_details;

public class Manage_Reviews extends AppCompatActivity {

    String places_key;
    DatabaseReference mainref;
    ArrayList<rating_details> al = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__reviews);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

}
package example.com.tourismapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import example.com.tourismapp2.classpack.places_details;

public class View_Place_detail extends AppCompatActivity {
    ImageView place_image;
    TextView placename;
    TextView placedesc,all_reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__place_detail);
        Intent in = getIntent();
        places_details obj = (places_details) in.getSerializableExtra("obj");

        place_image = findViewById(R.id.place_image);
        placename = findViewById(R.id.placename);
        placedesc = findViewById(R.id.placedesc);
        all_reviews = findViewById(R.id.all_reviews);

        Picasso.get().load(obj.getImages()).into(place_image);
        placename.setText(obj.getPlace_name());
        placedesc.setText(obj.getDescription());

        all_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Manage_Reviews.class);
                in.putExtra("places_key",obj.getPush_key()); // Sends that selected place's key to Manage reviews page
                startActivity(in);

            }
        });


    }
}
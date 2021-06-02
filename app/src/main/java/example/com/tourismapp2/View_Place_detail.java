package example.com.tourismapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import example.com.tourismapp2.classpack.places_details;

public class View_Place_detail extends AppCompatActivity {
    ImageView place_image;
    TextView placename;
    TextView placedesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__place_detail);
        Intent in = getIntent();
        places_details obj = (places_details) in.getSerializableExtra("obj");

        place_image = findViewById(R.id.place_image);
        placename = findViewById(R.id.placename);
        placedesc = findViewById(R.id.placedesc);

    }
}
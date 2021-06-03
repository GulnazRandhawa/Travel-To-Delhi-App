package example.com.tourismapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import example.com.tourismapp2.classpack.rating_details;

public class Post_Review extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView tvRateCount,tvRateMessage;
    String id;
    String email,places_key;
    private float ratedValue;
    EditText edcomment;
    String comment;
    DatabaseReference mainref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__review);
        Intent intent= getIntent();
        places_key = intent.getStringExtra("places_key");
        SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
        email = sharedPreference.getString("email","");
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvRateCount = (TextView) findViewById(R.id.tvRateCount);
        tvRateMessage = (TextView) findViewById(R.id.tvRateMessage);
        edcomment=findViewById(R.id.addComment);
        mainref = FirebaseDatabase.getInstance().getReference("Ratings");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override

            public void onRatingChanged(RatingBar ratingBar, float rating,

                                        boolean fromUser) {

                ratedValue = ratingBar.getRating();

                tvRateCount.setText("Your Rating : "

                        + ratedValue + "/5.");


                if(ratedValue<1){

                    tvRateMessage.setText("ohh ho...");

                }else if(ratedValue<2){

                    tvRateMessage.setText("Ok.");

                }else if(ratedValue<3){

                    tvRateMessage.setText("Not bad.");

                }else if(ratedValue<4){

                    tvRateMessage.setText("Nice");

                }else if(ratedValue<5){

                    tvRateMessage.setText("Very Nice");

                }else if(ratedValue==5){

                    tvRateMessage.setText("Thank you..!!!");

                }

            }

        });
    }
    public void doctorRate(View view) {
        Date d = new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
        String d1 = sdf.format(sdf.format(d));

        comment=edcomment.getText().toString();
        String key = mainref.push().getKey();
        rating_details obj = new rating_details(comment,email,places_key,key,d1,d.getTime(),ratedValue);
        mainref.child(places_key).child(key).setValue(obj);
        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
        finish();

    }
}
package example.com.tourismapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private int ratedValue;
    EditText edcomment;
    String comment,firstname;
    DatabaseReference mainref;
    Date dNow;
    String d1="";
    Button btnrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__review);
        dNow= new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat ("dd/MM/yyyy");

        System.out.println("Current Date: " + ft.format(dNow));
d1 = ft.format(dNow);

        Intent intent= getIntent();
        places_key = intent.getStringExtra("places_key");

        SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
        email = sharedPreference.getString("email","");
        firstname = sharedPreference.getString("firstname","");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvRateCount = (TextView) findViewById(R.id.tvRateCount);
        tvRateMessage = (TextView) findViewById(R.id.tvRateMessage);
        edcomment=findViewById(R.id.addComment);
        mainref = FirebaseDatabase.getInstance().getReference("Ratings");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override

            public void onRatingChanged(RatingBar ratingBar, float rating,

                                        boolean fromUser) {

                ratedValue = Math.round(ratingBar.getRating());

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
        btnrate = findViewById(R.id.btnrate);
        btnrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment=edcomment.getText().toString();
                String key = mainref.push().getKey();
                String d_pic = "https://firebasestorage.googleapis.com/v0/b/ui-design---vancouver.appspot.com/o/userimages%2FBlue.png?alt=media&token=89aa6d18-331e-4a4e-830c-223f45f0f5f6";

                rating_details obj = new rating_details(comment,email,places_key,key,d1, firstname,d_pic,dNow.getTime(),ratedValue);
                mainref.child(places_key).child(key).setValue(obj);
                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
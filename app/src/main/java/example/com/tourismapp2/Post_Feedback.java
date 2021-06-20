package example.com.tourismapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Post_Feedback extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView tvRateCount,tvRateMessage;
    String id;
    String email;
    private int ratedValue;
    EditText edcomment;
    String comment;
    DatabaseReference mainref;
    Date dNow;
    String d1="";
    Button btnrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__feedback);
        dNow= new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat ("dd/MM/yyyy");

        System.out.println("Current Date: " + ft.format(dNow));
        d1 = ft.format(dNow);


        SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
        email = sharedPreference.getString("email","");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvRateCount = (TextView) findViewById(R.id.tvRateCount);
        tvRateMessage = (TextView) findViewById(R.id.tvRateMessage);
        edcomment=findViewById(R.id.addComment);
        mainref = FirebaseDatabase.getInstance().getReference("Feedback");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
// star rating code in "Settings"
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
        //Setting or saving the feedback in firebase
        btnrate = findViewById(R.id.btnrate);
        btnrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment=edcomment.getText().toString();
                String key = mainref.push().getKey();
                rating_details obj = new rating_details(comment,email,"App",key,d1, dNow.getTime(),ratedValue);
                mainref.child(key).setValue(obj);
                Toast.makeText(getApplicationContext(), "Thanks For Submitted Feedback.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
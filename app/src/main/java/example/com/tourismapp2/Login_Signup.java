package example.com.tourismapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import example.com.tourismapp2.classpack.places_details;

public class Login_Signup extends AppCompatActivity {

    Button button_login;
    TextView button_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__signup);
        button_signup = findViewById(R.id.button_signup);
        button_login = findViewById(R.id.button_login);

        //auto login check
        SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
        String email = sharedPreference.getString("email","");
        Toast.makeText(Login_Signup.this, ""+email, Toast.LENGTH_SHORT).show();
        if(!email.equals("")){
            Intent in = new Intent(getApplicationContext(),HomeScreen.class);
            startActivity(in);
            finish();
        }
        else{
            Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
        }

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(getApplicationContext(),User_Signup_Screen.class);
                startActivity(intent);
            }
        });


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(getApplicationContext(),User_Login_Screen.class);
                startActivity(intent);

            }
        });


    }
}
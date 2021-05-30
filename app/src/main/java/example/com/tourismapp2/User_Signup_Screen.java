package example.com.tourismapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User_Signup_Screen extends AppCompatActivity {
 Button button_signup;
 EditText et_firstname,et_lastname,et_dob,et_gender,et_email,et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__signup__screen);

        button_signup = findViewById(R.id.button_signup);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_dob = findViewById(R.id.et_dob);
        et_gender = findViewById(R.id.et_gender);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname  = et_firstname.getText().toString();
                String lastname  = et_lastname.getText().toString();
                String dob  = et_dob.getText().toString();
                String gender  = et_gender.getText().toString();
                String email  = et_email.getText().toString();
                String password  = et_password.getText().toString();

                if(firstname.equals("") || lastname.equals("") || dob.equals("") || gender.equals("") || email.equals("") || password.equals("")){
                    Toast.makeText(User_Signup_Screen.this, "All fieldsa are required.", Toast.LENGTH_SHORT).show();
                }else {
                    // firebase submit
                }

            }
        });

    }
}
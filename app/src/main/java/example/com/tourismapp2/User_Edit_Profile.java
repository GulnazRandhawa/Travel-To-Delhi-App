package example.com.tourismapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import example.com.tourismapp2.classpack.user_details;

public class User_Edit_Profile extends AppCompatActivity {
    Button button_save,button_cancel;
    EditText et_firstname,et_lastname,et_dob,et_gender,et_email,et_password;
    DatabaseReference maianref;

    TextView button_delete;
    String saved_email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__edit__profile);

        button_save = findViewById(R.id.button_signup);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_dob = findViewById(R.id.et_dob);
        et_gender = findViewById(R.id.et_gender);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        button_delete = findViewById(R.id.button_delete);
        button_cancel = findViewById(R.id.button_cancel);

        //
        SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
         saved_email = sharedPreference.getString("email","");


        FirebaseApp.initializeApp(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        maianref = firebaseDatabase.getReference("Users");
        maianref.orderByChild("email").equalTo(saved_email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            // pre-filled details in settings
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot sin : snapshot.getChildren()){
                        user_details obj = sin.getValue(user_details.class);
                        // set values
                        et_firstname.setText(obj.getFirstname());
                        et_lastname.setText(obj.getLastname());
                        et_email.setText(obj.getEmail());
                        et_dob.setText(obj.getDob());
                        et_gender.setText(obj.getGender());
                        et_password.setText(obj.getPassword());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname  = et_firstname.getText().toString();
                String lastname  = et_lastname.getText().toString();
                String dob  = et_dob.getText().toString();
                String gender  = et_gender.getText().toString();
                String email  = et_email.getText().toString();
                String password  = et_password.getText().toString();

                if(firstname.equals("") || lastname.equals("") || dob.equals("") || gender.equals("") || email.equals("") || password.equals("")){
                    Toast.makeText(User_Edit_Profile.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                }else {


                    // firebase submit
                    user_details obj = new user_details(firstname,lastname,dob,gender,email,password);
                    maianref.child(firstname).setValue(obj);
                    Toast.makeText(User_Edit_Profile.this, "Saved Sccessfully.", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete userAccount
                maianref.orderByChild("email").equalTo(saved_email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot sin : snapshot.getChildren()){
                                user_details obj = sin.getValue(user_details.class); // retreive user objects from firebase
                             maianref.child(obj.getFirstname()).removeValue(); // remove accounts from firebase
                            }
                            // removing user account from local memory after removing from firebase.
                            SharedPreferences sharedPreference=getSharedPreferences("mypref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreference.edit();
                            editor.clear();
                            editor.apply();
                            finish();
                            Intent intent=new Intent(getApplicationContext(),Login_Signup.class);
                            startActivity(intent);
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
package example.com.tourismapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import example.com.tourismapp2.classpack.user_details;

public class User_Login_Screen extends AppCompatActivity {

    EditText et_password,et_email;
    Button button_login;
    DatabaseReference mainref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login__screen);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        button_login = findViewById(R.id.button_login);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("Users");
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String pass = et_password.getText().toString();
                if(email.equals("") || pass.equals("")){
                    Toast.makeText(User_Login_Screen.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else {

                    mainref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("Hello",snapshot.toString());
                            if(snapshot.exists()){
                                user_details obj = snapshot.getValue(user_details.class);
                                Toast.makeText(User_Login_Screen.this, ""+pass, Toast.LENGTH_SHORT).show();
                                if(obj.getPassword().equals(pass)){
                                    Toast.makeText(User_Login_Screen.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(User_Login_Screen.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(User_Login_Screen.this, "Invalid Emaial address", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });


    }
}
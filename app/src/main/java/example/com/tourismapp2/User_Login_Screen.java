package example.com.tourismapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Date;

import example.com.tourismapp2.classpack.user_details;

public class User_Login_Screen extends AppCompatActivity {

    EditText et_password,et_email;
    Button button_login;
    DatabaseReference mainref;
    String firstname ="";
    String lastname ="";
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

                    mainref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("Hello",snapshot.toString());
                            if(snapshot.exists()){

                                int flag=0;

                                for (DataSnapshot sin : snapshot.getChildren()){
                                    user_details obj = sin.getValue(user_details.class);
firstname = obj.getFirstname();
lastname = obj.getLastname();
                                    if (obj.getEmail().equals(email)&&obj.getPassword().equals(pass)){

                                        flag=1;

                                        break;

                                    }

                                }

                                if (flag==1){
                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                                    SharedPreferences sharedPreference=getSharedPreferences("mypref",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreference.edit();
                                    editor.putString("email",email);
                                    editor.putString("password",pass);
                                    editor.putString("firstname",firstname);
                                    editor.putString("lastname",lastname);

                                    Date date=new Date();
                                    long time=  date.getTime();
                                    editor.putLong("time",time);
                                    editor.commit();
                                    Intent in = new Intent(getApplicationContext(), HomeScreen.class);

                                    startActivity(in);

                                }
                                else{

                                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
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
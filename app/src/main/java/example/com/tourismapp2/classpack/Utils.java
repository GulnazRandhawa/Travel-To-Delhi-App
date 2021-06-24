package example.com.tourismapp2.classpack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import example.com.tourismapp2.Login_Signup;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
   static boolean task1,task2;
    public static final int CHECK=1;
    public static void main(String[] args) {

    }

    public static void logOut(Activity context) {
        SharedPreferences sharedPreference = context.getSharedPreferences("mypref", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreference.edit();


        task1 = task2 = false;
//
//        task1=sharedPreference.getBoolean("TASK1",false);
//        task2=sharedPreference.getBoolean("TASK2",false);
        boolean flag = false;
        String email = sharedPreference.getString("email", "");
        DatabaseReference fav_mainref = FirebaseDatabase.getInstance().getReference("User_Added_Fav_Places");

        // check that same place is already added to fav list or not
        fav_mainref.orderByChild("user_email_id").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int i = 0;
                    for (DataSnapshot sin : snapshot.getChildren()) {

                        ++i;
                        if (i >= CHECK) {
                            task1 = true;
                            exitLogout(context);
                            break;
                        }
                    }

                } else {
                    task1 = false;
                    {
                        DatabaseReference planner_ref = FirebaseDatabase.getInstance().getReference("Planners_Details");


                        planner_ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()) {
                                    int i = 0;
                                    for (DataSnapshot sin : snapshot.getChildren()) {

                                        ++i;
                                        if (i >= CHECK) {
                                            task2 = true;
                                            exitLogout(context);
                                            break;
                                        }
                                    }

                                } else {
                                   exitLogout(context);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }

                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });








    }

    public static void exitLogout( Activity context)

    {

        SharedPreferences sharedPreference = context.getSharedPreferences("mypref", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreference.edit();


        long beforeTime = sharedPreference.getLong("time", 0);
        long currenttime = new Date().getTime();
        long timeDiff = currenttime - beforeTime;
        SimpleDateFormat format = new SimpleDateFormat("hh-mm:ss");
        String value = format.format(timeDiff);
        System.out.println(value);

        boolean flag=false;
        if(task1)
        {

            flag=true;


        }
        else {
            if(!task2)
            {
                Toast.makeText(context, "No Place addded to favourites", Toast.LENGTH_SHORT).show();

                Toast.makeText(context, "No Place added in Planner", Toast.LENGTH_SHORT).show();


            }
            else
            {
                flag=true;
            }

        }


        if(flag) {
            save_time_to_firebase(beforeTime,currenttime,context);
            editor.clear();
            editor.apply();
            context.finish();
            Intent intent = new Intent(context, Login_Signup.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            //save to firebase

        }

    }

    public static void save_time_to_firebase(long before_time, long current_time, Activity context){

        SimpleDateFormat format=new SimpleDateFormat("hh-mm:ss");
        String value= format.format(before_time);
        String value2= format.format(current_time);
        //
        Date date_obj = new Date();
        SimpleDateFormat sdf1  = new SimpleDateFormat("dd-MM-yyyy");
        String  date_1 = sdf1.format(date_obj);

        //

        SharedPreferences sharedPreference=context.getSharedPreferences("mypref",MODE_PRIVATE);
        String email = sharedPreference.getString("email","");

        String  email2 = email.replaceAll("\\.", "~"); // test
        Toast.makeText(context, ""+email2, Toast.LENGTH_SHORT).show();
        DatabaseReference loginref = FirebaseDatabase.getInstance().getReference("Login_Time_Records");
        String id = loginref.push().getKey();
        Save_Login_Details obj = new Save_Login_Details(email,value,value2,date_1,id);

        loginref.child(email2).child(id).setValue(obj);

    }
}

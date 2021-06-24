package example.com.tourismapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import example.com.tourismapp2.classpack.Save_Login_Details;
import example.com.tourismapp2.classpack.Utils;

import static android.content.Context.MODE_PRIVATE;


public class user_profile_page extends Fragment {


TextView tv_setting,tv_send_feedback,tv_saved_places,tv_firstname,tv_email;

    public user_profile_page() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile_page, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.backIv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        getActivity().findViewById(R.id.logout7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Utils().logOut(getActivity());
            }





        });

        tv_setting  = getActivity().findViewById(R.id.tv_setting);
        tv_saved_places  = getActivity().findViewById(R.id.tv_saved_places);
        tv_send_feedback  = getActivity().findViewById(R.id.tv_send_feedback);
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity().getApplicationContext(),User_Edit_Profile.class);
                startActivity(in);

            }
        });

        tv_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(),Post_Feedback.class);
                startActivity(in);

            }
        });

        tv_saved_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        Intent backIntent=new Intent(getActivity(),HomeScreen.class);
        backIntent.putExtra("VALUE",3); // To open a fragment in footer, it first has to be redirected to Home screen.
        getActivity().finish();
        startActivity(backIntent);
            }
        });
        //
        SharedPreferences sharedPreference=getActivity().getSharedPreferences("mypref",MODE_PRIVATE);
        String email = sharedPreference.getString("email","");
        String firstname = sharedPreference.getString("email","");

        tv_firstname  = getActivity().findViewById(R.id.tv_firstname);
        tv_email  = getActivity().findViewById(R.id.tv_email);

        tv_firstname.setText(firstname);
        tv_email.setText(email);
        //


    }
    public void save_time_to_firebase(long before_time,long current_time){

        SimpleDateFormat format=new SimpleDateFormat("hh-mm:ss");
        String value= format.format(before_time);
        String value2= format.format(current_time);
        //
        Date date_obj = new Date();
        SimpleDateFormat sdf1  = new SimpleDateFormat("dd-MM-yyyy");
        String  date_1 = sdf1.format(date_obj);

        //

        SharedPreferences sharedPreference=getActivity().getSharedPreferences("mypref",MODE_PRIVATE);
        String email = sharedPreference.getString("email","");

        String  email2 = email.replaceAll("\\.", "~"); // test
        Toast.makeText(getActivity(), ""+email2, Toast.LENGTH_SHORT).show();
        DatabaseReference loginref = FirebaseDatabase.getInstance().getReference("Login_Time_Records");
        String id = loginref.push().getKey();
        Save_Login_Details obj = new Save_Login_Details(email,value,value2,date_1,id);

        loginref.child(email2).child(id).setValue(obj);

    }


}
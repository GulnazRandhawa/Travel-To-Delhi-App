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


public class user_profile_page extends Fragment {




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

                SharedPreferences sharedPreference=getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreference.edit();
                long beforeTime=sharedPreference.getLong("time",0);
                long currenttime=new Date().getTime();
                long timeDiff=currenttime-beforeTime;
                SimpleDateFormat format=new SimpleDateFormat("hh-mm");
                String value= format.format(timeDiff);
                System.out.println(value);

                boolean task1,task2;
                task1=sharedPreference.getBoolean("TASK1",false);
                task2=sharedPreference.getBoolean("TASK2",false);
                boolean flag=false;
                if(task1)
                {
                    if(!task2)
                    {
                        Toast.makeText(getActivity(), "No Place added in PLanner", Toast.LENGTH_SHORT).show();


                    }
                    else
                    {
                        flag=true;
                    }

                }
                else {
                    Toast.makeText(getActivity(), "No Place addded to favourites", Toast.LENGTH_SHORT).show();

                }


                if(flag) {
                    editor.clear();
                    editor.apply();
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), Login_Signup.class);
                    startActivity(intent);
                }

            }





        });
    }


}
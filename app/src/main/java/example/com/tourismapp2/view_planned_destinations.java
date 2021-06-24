package example.com.tourismapp2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import example.com.tourismapp2.classpack.Save_Login_Details;
import example.com.tourismapp2.classpack.Utils;
import example.com.tourismapp2.classpack.places_details;
import example.com.tourismapp2.classpack.planner_details;
import example.com.tourismapp2.classpack.user_added_fav_places_details;
import okhttp3.internal.Util;

import static android.content.Context.MODE_PRIVATE;

public class view_planned_destinations extends Fragment {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter5 adapter5;
    ArrayList<planner_details> temp_planner_arraylist  = new ArrayList<>();
    ArrayList<places_details> arrayList = new ArrayList<>();
    DatabaseReference planner_ref,popular_destinaation_ref;
    DatabaseReference fav_mainref;
    boolean flag = false;
    LinearLayout addNewLL;


    public view_planned_destinations() {
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
        return inflater.inflate(R.layout.fragment_view_planned_destinations, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView=getActivity().findViewById(R.id.cardRv);
        addNewLL=getActivity().findViewById(R.id.addNewLL);
        adapter5=new MyRecyclerViewAdapter5();
        recyclerView.setAdapter(adapter5);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences sharedPreference=getActivity().getSharedPreferences("mypref",MODE_PRIVATE);
        String email = sharedPreference.getString("email","");

        getActivity().findViewById(R.id.backIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
//        getActivity().findViewById(R.id.logout6).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sharedPreference=getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreference.edit();
//                editor.clear();
//                editor.apply();
//                getActivity().finish();
//                Intent intent=new Intent(getActivity(),Login_Signup.class);
//                startActivity(intent);
//
//
//
//            }
//        });

        getActivity().findViewById(R.id.logout6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Utils().logOut(getActivity());
            }
        });
        //
        planner_ref = FirebaseDatabase.getInstance().getReference("Planners_Details");
        popular_destinaation_ref = FirebaseDatabase.getInstance().getReference("Places");
        planner_ref.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp_planner_arraylist.clear();
                arrayList.clear();
                if(snapshot.exists()){
                    for (DataSnapshot sin : snapshot.getChildren()){
                        planner_details obj = sin.getValue(planner_details.class);
                        temp_planner_arraylist.add(obj);
                    }

                } else{
                    temp_planner_arraylist.clear();
                    arrayList.clear();
                    adapter5.notifyDataSetChanged();
                }
                arrayList.clear();
                fetch_places_details();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addNewLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent=new Intent(getActivity(),HomeScreen.class);
                backIntent.putExtra("VALUE",0);
                getActivity().finish();
                startActivity(backIntent);

            }
        });

    }
    public class MyRecyclerViewAdapter5 extends RecyclerView.Adapter<MyRecyclerViewAdapter5.ViewHolder1> {


        @NonNull
        @Override
        // This code renders Design (assigns memory location to the design)
        public MyRecyclerViewAdapter5.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.recycler_planner_design, parent, false); // design of single row
            return new MyRecyclerViewAdapter5.ViewHolder1(view);
        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter5.ViewHolder1 holder, int position) {

            ImageView imv101;
            TextView tv_place_name,tv_catdesc;
            imv101=(ImageView)(holder.itemView.findViewById(R.id.imvcardview_catphoto));
            tv_place_name=(TextView)(holder.itemView.findViewById(R.id.tvcardview_catname));
            LinearLayout button_save_for_later=(holder.itemView.findViewById(R.id.button_save_for_later));
            LinearLayout button_remove_fav=(holder.itemView.findViewById(R.id.button_remove_fav));
            planner_details planner_obj = temp_planner_arraylist.get(position);
            places_details places_obj = arrayList.get(position);
            TextView rating=holder.itemView.findViewById(R.id.rating1);
            rating.setText(arrayList.get(position).getRating());

            TextView date=holder.itemView.findViewById(R.id.date);
            date.setText(temp_planner_arraylist.get(position).getDate_vist()+"   "+temp_planner_arraylist.get(position).getFrom());
//

// setting values in recycler from Places_details only
            Picasso.get().load(places_obj.getImages()).into(imv101);
            tv_place_name.setText(places_obj.getPlace_name());

            button_save_for_later.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                    flag = false;
                    SharedPreferences sharedPreference=getActivity().getSharedPreferences("mypref",MODE_PRIVATE);
                    String email = sharedPreference.getString("email","");
                    fav_mainref = FirebaseDatabase.getInstance().getReference("User_Added_Fav_Places");

                    // check that same place is already added to fav list or not
                    fav_mainref.orderByChild("user_email_id").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for (DataSnapshot sin : snapshot.getChildren()){
                                    user_added_fav_places_details temp_obj = sin.getValue(user_added_fav_places_details.class); //creating object of this class.

                                    if(temp_obj.getPlaces_id().equals(places_obj.getPush_key())){ // matching ID of place from places_details class with the ID from user_addded place .
                                        flag = true;
                                        break;
                                    }


                                }

                                if(flag){
                                    Toast.makeText(getContext().getApplicationContext(), "Already Saved for Later!", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    String key = fav_mainref.push().getKey();
                                    user_added_fav_places_details fav_obj = new user_added_fav_places_details(key,email,places_obj.getPush_key()); // creating new object of this class to push to firebase.
                                    fav_mainref.child(key).setValue(fav_obj);

                                    Toast.makeText(getContext().getApplicationContext(), "Saved for Later!", Toast.LENGTH_SHORT).show();

                                    planner_ref.child(planner_obj.getId()).removeValue();
                                }

                            }
                            else { //checking for a New user who has not previously added any fav place.
                                String key = fav_mainref.push().getKey();
                                user_added_fav_places_details fav_obj = new user_added_fav_places_details(key,email,places_obj.getPush_key());
                                fav_mainref.child(key).setValue(fav_obj);

                                Toast.makeText(getContext().getApplicationContext(), "Saved for Later!", Toast.LENGTH_SHORT).show();
                                planner_ref.child(planner_obj.getId()).removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

            button_remove_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    planner_ref.child(planner_obj.getId()).removeValue();

                }
            });




        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder1 extends RecyclerView.ViewHolder {

            public ViewHolder1(View itemView) {
                super(itemView);
            }
        }
    }
    public void fetch_places_details(){
        for (int i = 0 ; i < temp_planner_arraylist.size() ; i++){

            popular_destinaation_ref.child(temp_planner_arraylist.get(i).getPlace_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("Hello-msg",snapshot.toString());
                    if(snapshot.exists()){
                        places_details obj3 = snapshot.getValue(places_details.class);
                        arrayList.add(obj3);
                        adapter5.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

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
        DatabaseReference loginref = FirebaseDatabase.getInstance().getReference("Login_Time_Records");
        String id = loginref.push().getKey();
        Save_Login_Details obj = new Save_Login_Details(email,value,value2,date_1,id);

        loginref.child(email2).child(id).setValue(obj);

    }

}
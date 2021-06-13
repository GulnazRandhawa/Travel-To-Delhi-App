package example.com.tourismapp2;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import example.com.tourismapp2.classpack.places_details;
import example.com.tourismapp2.classpack.planner_details;

import static android.content.Context.MODE_PRIVATE;

public class view_planned_destinations extends Fragment {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter5 adapter5;
    ArrayList<planner_details> temp_planner_arraylist  = new ArrayList<>();
    ArrayList<places_details> arrayList = new ArrayList<>();
    DatabaseReference planner_ref,popular_destinaation_ref;



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
        adapter5=new MyRecyclerViewAdapter5();
        recyclerView.setAdapter(adapter5);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences sharedPreference=getActivity().getSharedPreferences("mypref",MODE_PRIVATE);
        String email = sharedPreference.getString("email","");

        planner_ref = FirebaseDatabase.getInstance().getReference("Planners_Details");
        popular_destinaation_ref = FirebaseDatabase.getInstance().getReference("popular_destination_details");
        planner_ref.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp_planner_arraylist.clear();
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


    }
    public class MyRecyclerViewAdapter5 extends RecyclerView.Adapter<MyRecyclerViewAdapter5.ViewHolder1> {


        @NonNull
        @Override
        public MyRecyclerViewAdapter5.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.recycler_planner_design, parent, false); // design of single row
            return new MyRecyclerViewAdapter5.ViewHolder1(view);
        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter5.ViewHolder1 holder, int position) {
//            ImageView twoDot=holder.itemView.findViewById(R.id.twodot);
//            TextView timeDisplay=holder.itemView.findViewById(R.id.timeDisplay);
//            TextView stanley_par=holder.itemView.findViewById(R.id.stanley_par);
//            TextView street_name_desc=holder.itemView.findViewById(R.id.street_name);

//            planner_details planner_obj = temp_planner_arraylist.get(position);
//            places_details places_obj = arrayList.get(position);




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

}
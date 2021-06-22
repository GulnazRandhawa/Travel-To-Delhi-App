package example.com.tourismapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

//code for directing to Saved places screen when user clicks the option  "saved places" in Settings
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //load View all places

        Intent intent=getIntent();
        int value =intent.getIntExtra("VALUE",1);
        if(value==2)
        {
          loadFragment(new view_planned_destinations());
          navView.setSelectedItemId(R.id.navigation_dashboard);
        }
        else if(value==3)
        {
           loadFragment(new view_all_fav_places());
           navView.setSelectedItemId(R.id.navigation_notifications);
        }
        else if(value==4)

        {
            loadFragment(new user_profile_page());
            navView.setSelectedItemId(R.id.navigation_profile);
        }
        else
        {
            loadFragment(new ViewAllPlaces());
        }
// code for On clicking any option in footer
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        //home
                        fragment =  new ViewAllPlaces();
                        loadFragment(fragment);

                        return true;
                    case R.id.navigation_dashboard:
                        //calender
                        fragment = new view_planned_destinations();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_notifications:
                        //fav
                        fragment = new view_all_fav_places();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_profile:
                        //profile
                        fragment = new user_profile_page();
                        loadFragment(fragment);
                        return true;






                }


                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
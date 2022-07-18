package com.villagebanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DB2IUD;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.databinding.AppActivityBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private AppActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBUtility.CreateDB(this);
        binding = AppActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder
                (
                        R.id.nav_person_grid_view,
                        R.id.nav_group_grid_view,
                        R.id.nav_period_grid_view
                )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //resetTable();
        getDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*
        @Override
        protected void attachBaseContext(Context newBase) {
            Locale localeToSwitchTo = new Locale("ta");
            ContextWrapper localeUpdatedContext = ContextUtils.updateLocale(newBase, localeToSwitchTo);
            super.attachBaseContext(localeUpdatedContext);
        }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetTable() {
       DBUtility.DTOdelete(0, tblGroupPersonLink.Name);
//        DBUtility.DTOdelete(0, tblTransHeader.Name);

    }
    private void getDetails(){
        ArrayList<BOGroupPersonLink> boGroupPersonLinks=DBUtility.DBGetGroupPerson(tblGroupPersonLink.Name);
        ArrayList<BOTransHeader> boTransHeaders=DBUtility.DTOGetAlls(tblTransHeader.Name);
        ArrayList<BOTransDetail> boTransDetails=DBUtility.DTOGetAlls(tblTransDetail.Name);
        ArrayList<BOPeriod> boPeriods=DBUtility.DTOGetAlls(DB1Tables.PERIODS);
    }
}
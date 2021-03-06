package com.example.organiserempty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    MenuItem event,remind,setting;
    FloatingActionButton fab;
    FragmentManager fm;
    OpenHelper openHelper;

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        event=findViewById(R.id.nav_events);
        remind=findViewById(R.id.nav_reminders);
        setting=findViewById(R.id.nav_settings);
        fab=findViewById(R.id.fab);
        list=findViewById(R.id.list);


        openHelper=new OpenHelper(this);

        listItem=new ArrayList<>();

        viewData();


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm=getSupportFragmentManager();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new DialogFragment();
                dialogFragment.show(fm,"dialog");
            }
        });
    }

    private void viewData() {
        list.setAdapter(null);
        Cursor cursor=openHelper.viewData();


        if(cursor.getCount()==0){
            Toast.makeText(this,"?????? ????????????????????",Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }

            adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            list.setAdapter(adapter);
        }
    }



    // ???????????????? ?????????? ????????????????????????
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(item.getItemId()==R.id.nav_events){
            Intent intentMain=new Intent(EventActivity.this,MainActivity.class);
            startActivity(intentMain);
        }
        else if(item.getItemId()==R.id.nav_reminders){
            Intent intentRem=new Intent(EventActivity.this,RemindersActivity.class);
            startActivity(intentRem);
        }
        else if(item.getItemId()==R.id.nav_settings){
            Intent intentSet=new Intent(EventActivity.this,SettingsActivity.class);
            startActivity(intentSet);
        }

        return super.onOptionsItemSelected(item);
    }
}
package com.example.ilham.loginlogout.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.SessionManager;
import com.example.ilham.loginlogout.Users;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Users users;
    SessionManager session;
    private Toolbar toolbar;
    private TextView homeemail, homename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        users = new Users(getApplicationContext());
        String name = users.getUname();
        String email = users.getEmail();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navheader = navigationView.getHeaderView(0);
        homeemail = (TextView) navheader.findViewById(R.id.home_email);
        homename = (TextView) navheader.findViewById(R.id.home_name);
        homename.setText(name);
        homeemail.setText(email);

        displaySelectedScreen(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            exitDialog();
        }
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mobile Office App")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
        Dialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mobile Office App")
                .setMessage("Are you want to Logout?")
                .setOnCancelListener(null)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        session.logoutUser();
                        finish();
                    }
                });
        Dialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
//        Toast.makeText(getApplicationContext(), "" + item, Toast.LENGTH_SHORT).show();
        //make this method blank
        return true;
    }

    public boolean displaySelectedScreen(int item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (item) {
            case R.id.nav_home:
                fragment = new HomeActivity();
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.nav_profile:
                fragment = new ProfileActivity();
                break;
            case R.id.nav_contact:
                fragment = new ContactActivity();
                break;
            case R.id.nav_about:
                fragment = new AboutActivity();
                break;
            case R.id.nav_change_pass:
                fragment = new ChangePassActivity();
                break;
            case R.id.nav_logout:
                logoutDialog();
//                session.logoutUser();
//                finish();
                break;
            default:
                fragment = new HomeActivity();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

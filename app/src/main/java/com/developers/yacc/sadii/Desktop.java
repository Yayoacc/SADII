package com.developers.yacc.sadii;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dmax.dialog.SpotsDialog;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class Desktop extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Connection con;
    String un, passw, db, ip, value;
    RingProgressBar ringProgressBar1, ringProgressBar2;
    int porcent = 0;
    Handler handlerpb = new Handler() {
        if(msg.what ==0)

        {
            if (porcent < 100) {
                porcent = Integer.parseInt(value);
                ringProgressBar1.setProgress(porcent);
                ringProgressBar2.setProgress(porcent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ip = "208.118.63.49";
        db = "DB_A3B963_login";
        un = "DB_A3B963_login_admin";
        passw = "Thekingof02";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Select select = new Select();// this is the Asynctask, which is used to process in background to reduce load on app process
        select.execute("");
        setContentView(R.layout.content_desktop);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.desktop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.warning) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_desktop, new fragment_warnings()).commit();
        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_controles) {

        } else if (id == R.id.nav_view) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            ConnectionURL = "jdbc:jtds:sqlserver://"+server+";database=" + database + ";user=" + user + ";password=" + password + ";";
            //ConnectionURL = "jdbc:jtds:sqlserver://192.168.1.9;database=msss;instance=SQLEXPRESS;Network Protocol=NamedPipes" ;


            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

    public class Select extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        SpotsDialog waitdialog = new SpotsDialog(Desktop.this);
        @Override
        protected void onPreExecute() {
            waitdialog.show();
        }
        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(Desktop.this, r, Toast.LENGTH_SHORT).show();
            waitdialog.dismiss();
            Toast.makeText(Desktop.this, value, Toast.LENGTH_SHORT).show();
            /*if (r.equals("Login successful")){
                Intent desk = new Intent(Desktop.this, Desktop.class);
                startActivityForResult(desk, 0);
                finish();
            }
            *//*else if(r.equals("Please enter Username and Password")){
                if (usr.trim().equalsIgnoreCase("")) {
                    user.setError("Este campo no puede estar vacio");
                } else if (psw.trim().equalsIgnoreCase("")) {
                    pass.setError("Este campo no puede estar vacio");
                }
            }*//*
            else if(r.equals("Invalid Credentials!")) {
                popup.setContentView(R.layout.activity_pop_up);
                close = (TextView) popup.findViewById(R.id.close);
                cl = (Button) popup.findViewById(R.id.Ok);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();
                    }
                });
                cl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popup.dismiss();

                    }
                });
                popup.show();
            }
            if (isSuccess) {
                z = "Login successful";
                Toast.makeText(Login.this , "Login successful" , Toast.LENGTH_LONG).show();
                //finish();
            }*/
        }

        @Override
        protected String doInBackground(String... params) {
                try {
                    con = connectionclass(un, passw, db, ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {
                        String query = "select * from humedad;";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while(rs.next()){
                            value = rs.getString("humedad");
                        }
                        con.close();
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            return z;
        }
    }
}

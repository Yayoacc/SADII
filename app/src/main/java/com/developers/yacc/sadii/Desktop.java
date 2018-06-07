package com.developers.yacc.sadii;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import dmax.dialog.SpotsDialog;
import com.juang.jplot.PlotPastelito;

public class Desktop extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Connection con;
    String un, passw, db, ip, value;
    private PlotPastelito pastel;
    private LinearLayout pantalla;
    Context context;
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
        getMenuInflater().inflate(R.menu.desktop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(Desktop.this, value, Toast.LENGTH_SHORT).show();
            context = Desktop.this;
            pantalla= (LinearLayout) (findViewById(R.id.pantalla));
            pastel=new PlotPastelito(context,"Humedad");//puedes usar simplemente "this" en lugar de context
            int humedad = Integer.parseInt(value);
            int resto = 100 - humedad;
            float[] datapoints = {resto, humedad};
            String[] etiquetas={"porcentaje", "humedad"};
            pastel.SetDatos(datapoints,etiquetas);
            pastel.SetColorTitulo(0, 255, 0 );
            pastel.SetColorAcot(255, 160, 2);
            pastel.SetColorTextGrafico(255, 255, 255);
            pastel.SetColorFondo(255, 255,255 );
            pastel.SetColorDato(0, 29, 5, 126 );
            pastel.SetColorDato(1, 107, 255, 130 );
            pastel.SetColorContorno(255, 255, 255);
            pastel.SetShowPorcentajes(true);

   /*antes de mostrar el grafico en pantalla(LinearLayout) deben de ir todos los ajustes "Set" del grafico.
       Todos los metodos publicos que ayudan a personalizar el grafico se describen cada uno en la siguiente sección */

            pastel.SetHD(true); //ajustamos la calidad hd que suaviza bordes del grafico. por default esta desactivado
            pantalla.addView(pastel);
            //finish();
        }
        @Override
        protected String doInBackground(String... params) {
                try {
                    con = connectionclass(un, passw, db, ip);
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

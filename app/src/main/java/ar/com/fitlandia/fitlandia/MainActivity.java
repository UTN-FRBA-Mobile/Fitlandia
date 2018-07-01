package ar.com.fitlandia.fitlandia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;
//import ar.com.fitlandia.fitlandia.utils.Fragments.LoginFragment;


import ar.com.fitlandia.fitlandia.logrosok.HistoricoLogrosActivity;
import ar.com.fitlandia.fitlandia.models.UsuarioModel;
import ar.com.fitlandia.fitlandia.runningok.StorageOk;
import ar.com.fitlandia.fitlandia.rutinasok.RutinasActivity;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;
import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Button Rutinas;
    Button Running;
    Button Selfie;
    DrawerLayout drawer;
    NavigationView navigationView;
    View headerView;
    ApplicationGlobal applicationGlobal ;
    TextView menuTextoSuperior;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         Paper.init(getApplicationContext());
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         applicationGlobal  = ApplicationGlobal.getInstance();

         UsuarioModel usuarioModel = StorageOk.getLogin();
         if(usuarioModel!=null)
             applicationGlobal.setUsuario(usuarioModel);


         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                 this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
         toggle.syncState();
         navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);

         headerView = navigationView.getHeaderView(0);
         menuTextoSuperior =(TextView) headerView.findViewById(R.id.menuTextoSuperior);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_perfil) {
            Intent intent = new Intent(this, Perfil.class);
            startActivity(intent);
        } else if (id == R.id.nav_historial) {
            Intent intent = new Intent(this, Main_historial.class);
            startActivity(intent);
        } else if (id == R.id.nav_login) {
           // fragmentSelected =  new LoginFragment();
            //fragmentTransaction = true;
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            //finish();
        }else if(id == R.id.nav_logout){
            StorageOk.removeLogin();
            applicationGlobal.setUsuario(null);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        /*if(fragmentTransaction) {
            setFragment(fragmentSelected);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        recargarMenu();
    }

    private void recargarMenu() {
        if(menuTextoSuperior!=null && applicationGlobal.getUsuario()!=null){

            menuTextoSuperior.setText( applicationGlobal.getUsuario().getUsername());
        }
    }

    public void goToRutinas(View v){
        //Intent intent = new Intent(this, Rutinas.class);
        Intent intent = new Intent(this, RutinasActivity.class);
        startActivity(intent);
    }
    public void goToRunning(View v){
        Intent intent = new Intent(this, Running.class);
        startActivity(intent);
    }
    public void goToSelfie(View v){
        Intent intent = new Intent(this, Selfie.class);
        startActivity(intent);
    }


    public void setFragment(Fragment fragment){
        setFragment(fragment, true, null);
    }

    public void setFragment(Fragment fragment, boolean toBackStack, String name ) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        if(toBackStack) {
            ft.addToBackStack(name);
        }
        ft.commitAllowingStateLoss();
    }

}

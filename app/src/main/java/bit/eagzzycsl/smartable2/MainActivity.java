package bit.eagzzycsl.smartable2;

import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import fragment.Fragment_main_calendric;
import fragment.Fragment_main_smart;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle_smart;
    private ActionBarDrawerToggle toggle_calendric;
    private Fragment_main_smart fragment_main_smart;
    private Fragment_main_calendric fragment_main_calendric;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout_glance;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myFindView();
        myCreate();
        mySetView();
    }

    private void myFindView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout_glance = (FrameLayout) findViewById(R.id.frameLayout_glance);
        fragment_main_smart = (Fragment_main_smart) getFragmentManager().findFragmentById(R.id.fragment_main_smart);
        fragment_main_calendric = (Fragment_main_calendric) getFragmentManager().findFragmentById(R.id.fragment_main_calendric);
        navigationView = (NavigationView) findViewById(R.id.nav_main);
    }

    private void myCreate() {
        toggle_smart = new ActionBarDrawerToggle(
                this, drawerLayout, fragment_main_smart.getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle_calendric = new ActionBarDrawerToggle(
                this, drawerLayout, fragment_main_calendric.getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);


    }

    private void mySetView() {
        if (true) {
            /*默认显示*/
            getFragmentManager().beginTransaction().hide(fragment_main_calendric)
                    .commit();
            drawerLayout.setDrawerListener(toggle_smart);
            toggle_smart.syncState();
            navigationView.getMenu().findItem(R.id.nav_glance_smart).setChecked(true);
        } else if (true) {
            getFragmentManager().beginTransaction().hide(fragment_main_smart)
                    .commit();
            drawerLayout.setDrawerListener(toggle_calendric);
            toggle_calendric.syncState();
            navigationView.getMenu().findItem(R.id.nav_glance_calendric).setChecked(true);
        }


        navigationView.setNavigationItemSelectedListener(this);
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
        Log.i("menu", "main_create");
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


        switch (id) {
            case R.id.nav_glance_smart: {
                getFragmentManager().beginTransaction().hide(fragment_main_calendric)
                        .show(fragment_main_smart).commit();
                drawerLayout.setDrawerListener(toggle_smart);
                toggle_smart.syncState();
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_glance_calendric: {
                getFragmentManager().beginTransaction().hide(fragment_main_smart)
                        .show(fragment_main_calendric).commit();
                drawerLayout.setDrawerListener(toggle_calendric);
                toggle_calendric.syncState();
                invalidateOptionsMenu();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

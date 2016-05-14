package bit.eagzzycsl.smartable2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import fragment.Fragment_main_calendric;
import fragment.Fragment_main_smart;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private Fragment_main_smart fragment_main_smart;
    private Fragment_main_calendric fragment_main_calendric;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout_glance;
    private Toolbar toolbar;
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout_glance = (FrameLayout) findViewById(R.id.frameLayout_glance);
        fragment_main_smart = (Fragment_main_smart) getFragmentManager().findFragmentById(R.id.fragment_main_smart);
        fragment_main_calendric = (Fragment_main_calendric) getFragmentManager().findFragmentById(R.id.fragment_main_calendric);
        navigationView = (NavigationView) findViewById(R.id.nav_main);
    }

    private void myCreate() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    private void mySetView() {

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (false) {
            /*默认显示*/
            getFragmentManager().beginTransaction().hide(fragment_main_calendric)
                    .commit();
            navigationView.getMenu().findItem(R.id.nav_glance_smart).setChecked(true);
        } else {
            getFragmentManager().beginTransaction().hide(fragment_main_smart)
                    .commit();
            navigationView.getMenu().findItem(R.id.nav_glance_calendric).setChecked(true);
        }


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        if (fragment_main_calendric.isHidden()) {
            getMenuInflater().inflate(R.menu.menu_main_smart, menu);


        } else {
            getMenuInflater().inflate(R.menu.menu_main_calendric, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_switch: {
                item.setIcon(fragment_main_smart.switchView() == 0 ?
                        R.drawable.ic_main_menu_smart_classify :
                        R.drawable.ic_main_menu_smart_serialize);

                //换图标
                Toast.makeText(this, "switch", Toast.LENGTH_LONG).show();
                break;
            }
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
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_glance_calendric: {
                getFragmentManager().beginTransaction().hide(fragment_main_smart)
                        .show(fragment_main_calendric).commit();
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_kind_note: {
                startActivity(new Intent(this, NoteBookActivity.class));
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment_main_smart.onActivityResult(requestCode, resultCode, data);
    }
}
/*两个默认，一个默认显示日历浏览还是智能浏览，一个默认在智能浏览下显示序列化还是分类*/

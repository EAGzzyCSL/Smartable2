package bit.eagzzycsl.smartable2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import fragment.Fragment_main_calendric;
import fragment.Fragment_main_smart;
import view.EnumCalendricViewType;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private Fragment_main_smart fragment_main_smart;
    private Fragment_main_calendric fragment_main_calendric;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout_glance;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private AdapterView.OnItemSelectedListener spinner_onSelected;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private Spinner action_spinner_switch;

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
        action_spinner_switch = (Spinner) findViewById(R.id.action_spinner_switch);
    }

    private void myCreate() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        spinner_onSelected = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fragment_main_calendric.showCal(EnumCalendricViewType.getEnum(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, getResources().getStringArray(
                R.array.menu_calendric_switch));
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
        action_spinner_switch.setAdapter(spinnerArrayAdapter);
        action_spinner_switch.setOnItemSelectedListener(spinner_onSelected);
        action_spinner_switch.setSelection(3);
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
        //TODO 菜单的切换会不会造成性能问题
        if (fragment_main_calendric.isHidden()) {
            getMenuInflater().inflate(R.menu.menu_main_smart, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main_calendric, menu);
//            MenuItem item = menu.findItem(R.id.action_spinner_switch);
//            Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//            spinner.setAdapter(spinnerArrayAdapter);
//            spinner.setOnItemSelectedListener(spinner_onSelected);
//            spinner.setSelection(3);
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
                action_spinner_switch.setVisibility(View.INVISIBLE);
                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_glance_calendric: {
                getFragmentManager().beginTransaction().hide(fragment_main_smart)
                        .show(fragment_main_calendric).commit();
                action_spinner_switch.setVisibility(View.VISIBLE);

                invalidateOptionsMenu();
                break;
            }
            case R.id.nav_kind_note: {
                startActivity(new Intent(this, NoteBookActivity.class));
                break;
            }
            case R.id.nav_about_setting: {
                startActivity(new Intent(this, SettingsActivity.class));
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
        fragment_main_calendric.onActivityResult(requestCode, resultCode, data);
    }
}
/*两个默认，一个默认显示日历浏览还是智能浏览，一个默认在智能浏览下显示序列化还是分类*/

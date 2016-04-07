package bit.eagzzycsl.smartable2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class NoteBookDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_detail);
        myFindView();
        mySetView();
    }

    private void myFindView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void mySetView(){
        Intent intent = getIntent();
        String note_title = intent.getStringExtra("note_title");

        toolbar.setTitle(note_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//顶部返回按钮
    }

    //toolbar返回按钮监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(NoteBookDetailActivity.this, NoteBookActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

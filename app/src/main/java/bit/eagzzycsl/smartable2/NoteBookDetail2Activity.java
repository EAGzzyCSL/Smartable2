package bit.eagzzycsl.smartable2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import adapter.Adapter_notebookDetail;
import database.SQLMan;
import decorator.DividerItemDecoration;
import entry.EntryNotebook;
import entry.EntryNotebookDetail;

/**
 * Created by 宇 on 2016/5/26.
 */
public class NoteBookDetail2Activity extends AppCompatActivity  implements Adapter_notebookDetail.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ImageButton mAddButton;
    private Adapter_notebookDetail mAdapter;
    private Toolbar toolbar;
    private FloatingActionButton new_notebookDetail_btn;
    private EntryNotebook entryNotebook;
    private ActionBar actionbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_detail);

        myFindView();
        myInit();
        myCreate();
        mySetView();
    }

    //toolbar设置按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void myFindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        new_notebookDetail_btn = (FloatingActionButton) findViewById(R.id.new_notebookDetail_btn);
    }

    protected void myInit() {
        Bundle bundle = this.getIntent().getExtras();
        entryNotebook = (EntryNotebook) bundle.getSerializable(ExtraFiled.entryIntent);
    }

    private void myCreate() {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(entryNotebook.getTitle());
        mAdapter = new Adapter_notebookDetail(
                SQLMan.getInstance(NoteBookDetail2Activity.this).readById(EnumEntry.notebookDetail, entryNotebook.getId()), this);
    }

    private void mySetView() {

        /*layout_manager*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        new_notebookDetail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteBookDetail2Activity.this, NoteBookDetailEdit.class);
                intent.putExtra(EnumExtra.getName(), EnumExtra.addEntry);
                intent.putExtra(ExtraFiled.entryToEdit, entryNotebook);
                NoteBookDetail2Activity.this.startActivityForResult(intent, IntentCode.request_fromMainToEntryEdit);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(NoteBookDetail2Activity.this, NoteBook2Activity.class);
            intent.putExtra(EnumExtra.getName(), EnumExtra.modifyEntry);
            intent.putExtra(ExtraFiled.entryToEdit, entryNotebook);
            this.setResult(IntentCode.result_fromEntryEditToMain, intent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //toolbar返回按钮监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(NoteBookDetail2Activity.this, NoteBook2Activity.class);
            intent.putExtra(EnumExtra.getName(), EnumExtra.modifyEntry);
            intent.putExtra(ExtraFiled.entryToEdit, entryNotebook);
            this.setResult(IntentCode.result_fromEntryEditToMain, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentCode.request_fromMainToEntryEdit && resultCode == IntentCode.result_fromEntryEditToMain) {
            EnumExtra enumExtra = (EnumExtra) data.getSerializableExtra(EnumExtra.getName());
            EntryNotebookDetail entryNotebookDetail = (entry.EntryNotebookDetail) data.getSerializableExtra(ExtraFiled.entryResult);
            updateEntryListInView(enumExtra, entryNotebookDetail);
        }
    }

    public void updateEntryListInView(EnumExtra enumExtra, EntryNotebookDetail entry) {
        switch (enumExtra) {
            case entryAdded: {
                mAdapter.insertEntryNotebook(entry);
                entryNotebook.setNotedetail_num(entryNotebook.getNotedetail_num() + 1);
                SQLMan.getInstance(NoteBookDetail2Activity.this).update(entryNotebook);
                break;
            }
            case modifyEntry: {
                mAdapter.modifyEntryNotebook(entry);
                break;
            }
            case entryRemoved: {
                mAdapter.removeEntryNotebook(entry);
                entryNotebook.setNotedetail_num(entryNotebook.getNotedetail_num() - 1);
                SQLMan.getInstance(NoteBookDetail2Activity.this).update(entryNotebook);
                break;
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        mAdapter.removeEntryNotebookByPosition(position);
        entryNotebook.setNotedetail_num(entryNotebook.getNotedetail_num() - 1);
        SQLMan.getInstance(NoteBookDetail2Activity.this).update(entryNotebook);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}

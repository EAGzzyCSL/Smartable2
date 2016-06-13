package bit.eagzzycsl.smartable2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import adapter.Adapter_notebook;
import database.SQLMan;
import decorator.DividerItemDecoration;
import entry.Entry;
import entry.EntryNotebook;
import my.MyMoment;

/**
 * Created by 宇 on 2016/5/26.
 */
public class NoteBook2Activity extends AppCompatActivity implements Adapter_notebook.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ImageButton mAddButton;
    private Adapter_notebook mAdapter;
    private Entry entryToEdit;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    protected android.app.AlertDialog dialog_newNotebook;
    protected android.app.AlertDialog dialog_modifyNotebook;
    private EditText et_newNotebook = null;
    private EditText et_modifyNotebook = null;
    private TextView notedetail_num;
    private  ActionBar actionBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook2);
        myFindView();
        myCreate();
        mySetView();
    }

    //toolbar设置按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void myFindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        notedetail_num = (TextView) findViewById(R.id.item_tv_notedetail_num);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void myCreate() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//顶部返回按钮
        actionBar.setTitle("笔记本");
        mAdapter = new Adapter_notebook(SQLMan.getInstance(NoteBook2Activity.this).read(EnumEntry.notebook), this);
    }

    private void mySetView() {

        /*layout_manager*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public void onClick_newNotebook(View v) {
        dailogNewNotebook();
    }

    private void dailogNewNotebook() {
        et_newNotebook = new EditText(this);

        dialog_newNotebook = new AlertDialog.Builder(this).setTitle("新建笔记本")
//                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et_newNotebook).setPositiveButton("保存", onDialogbtnlickListener(1))
                .setNegativeButton("取消", onDialogbtnlickListener(0)).show();
    }

    private DialogInterface.OnClickListener onDialogbtnlickListener(int isSave) {
        switch (isSave) {
            case 1: {//保存
                return new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        entryToEdit = new EntryNotebook(
                                et_newNotebook.getText().toString(),
                                "",
                                new MyMoment(),
                                "1",
                                0
                                //Integer.parseInt(notedetail_num.getText().toString())
                        );
                        int id = SQLMan.getInstance(NoteBook2Activity.this).create(entryToEdit);
                        entryToEdit.setId(id);

//                        Entry entry = (Entry) data.getSerializableExtra(ExtraFiled.entryResult);
                        mAdapter.insertEntryNotebook(entryToEdit);

//                        Intent intent = new Intent();
//                        intent.putExtra(EnumExtra.getName(), EnumExtra.entryAdded);
//                        intent.putExtra(ExtraFiled.entryResult, entryToEdit);
//                        setResult(IntentCode.result_fromEntryEditToMain, intent);
//                        finish();
                    }
                };

            }

            case 0: {//取消
                return new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            }
        }
        return null;
    }

    //长按修改dialog
    private void dailogModifyNotebook(int position) {
        et_modifyNotebook = new EditText(this);

        dialog_modifyNotebook = new AlertDialog.Builder(this).setTitle("修改笔记本")
//                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et_modifyNotebook).setPositiveButton("确定", onDialogbtnlickListener1(1, position))
                .setNegativeButton("取消", onDialogbtnlickListener1(0, position)).show();
    }

    private DialogInterface.OnClickListener onDialogbtnlickListener1(int isSave, final int position) {
        switch (isSave) {
            case 1: {//确定
                return new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.modifyEntryNotebookByposition(position, et_modifyNotebook.getText().toString());
                    }
                };
            }

            case 0: {//取消
                return new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            }
        }
        return null;
    }

    //toolbar返回按钮监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentCode.request_fromMainToEntryEdit
                && resultCode == IntentCode.result_fromEntryEditToMain) {
            EnumExtra enumExtra = (EnumExtra) data.getSerializableExtra(EnumExtra.getName());

            Entry entry = (Entry) data.getSerializableExtra(ExtraFiled.entryToEdit);
            updateEntryListInView(enumExtra, entry);
        }
    }

    public void updateEntryListInView(EnumExtra enumExtra, Entry entry) {
        switch (enumExtra) {
            case entryAdded: {
                mAdapter.insertEntryNotebook(entry);
                break;
            }
            case modifyEntry: {
                mAdapter.modifyEntryNotebook(entry);
                break;
            }
            case entryRemoved: {
                break;
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        mAdapter.removeEntryNotebook(position);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        dailogModifyNotebook(position);
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}

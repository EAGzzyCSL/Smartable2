package bit.eagzzycsl.smartable2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLMan;
import entry.Entry;
import entry.EntryNotebookDetail;
import my.MyMoment;

/**
 * Created by 宇 on 2016/5/31.
 */
public class NoteBookDetailEdit extends AppCompatActivity{

    private EditText edit_activity_title;
    private Button btn_note_delete;
    private Button btn_note_save;
    private EntryNotebookDetail entryToEdit;
    private Entry entryNotebook;
    private int notebookId = -1;
    private boolean update = false;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_detail_edit);

        myFindView();
        myInit();
        myCreate();
        mySetView();
    }

    private void myFindView(){
        edit_activity_title = (EditText) findViewById(R.id.edit_activity_title);
        btn_note_delete = (Button) findViewById(R.id.btn_note_delete);
        btn_note_save = (Button) findViewById(R.id.btn_note_save);
        toolbar =(Toolbar)findViewById(R.id.toolbar);
    }

    private void myInit(){
        Bundle bundle = this.getIntent().getExtras();
        EnumExtra enumExtra = (EnumExtra) bundle.getSerializable(EnumExtra.getName());
        if(enumExtra != null){
            switch (enumExtra){
                case modifyEntry:{
                    entryToEdit = (EntryNotebookDetail) bundle.getSerializable(ExtraFiled.entryToEdit);
                    notebookId = entryToEdit.getNotebookId();
                    update = true;
                    edit_activity_title.setText(entryToEdit.getTitle());
                }
                case addEntry:{
                    entryNotebook = (Entry) bundle.getSerializable(ExtraFiled.entryToEdit);
                    notebookId = entryNotebook.getId();
                }
            }
        }
    }

    private void myCreate(){

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

    private void mySetView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_note_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(btn_note_save.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "标题未填写任何东西", Toast.LENGTH_LONG).show();
                }
                else{
                    saveEntryToDB();
                    finish();
                }
            }
        });

        btn_note_delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(entryToEdit != null){
                    SQLMan.getInstance(NoteBookDetailEdit.this).delete(entryToEdit);
                    Intent intent = new Intent();
                    intent.putExtra(EnumExtra.getName(), EnumExtra.entryRemoved);
                    intent.putExtra(ExtraFiled.entryResult, entryToEdit);
                    setResult(IntentCode.result_fromEntryEditToMain, intent);
                }
                finish();
            }
        });
    }

    protected void saveEntryToDB() {//todo 时间还是少了一个月
        //假定update的时候entryToEdit不为空

        int preId = update ? entryToEdit.getId() : 0;

        entryToEdit = new EntryNotebookDetail(
                edit_activity_title.getText().toString(),
                "",
                new MyMoment(),
                "1",
                notebookId
//                entryNotebook != null ?  entryNotebook.getId() : entryToEdit.getNotebookId() //判空
        );
        //判断是添加还是修改

        Intent intent = new Intent();
        if (update) {
            entryToEdit.setId(preId);
        }
        if (update) {
            SQLMan.getInstance(NoteBookDetailEdit.this).update(entryToEdit);
            intent.putExtra(EnumExtra.getName(), EnumExtra.modifyEntry);
        } else {
            SQLMan.getInstance(NoteBookDetailEdit.this).create(entryToEdit);
            intent.putExtra(EnumExtra.getName(), EnumExtra.entryAdded);
        }
        intent.putExtra(ExtraFiled.entryResult, entryToEdit);
        setResult(IntentCode.result_fromEntryEditToMain, intent);
    }

}

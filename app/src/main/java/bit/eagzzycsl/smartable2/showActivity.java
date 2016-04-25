package bit.eagzzycsl.smartable2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;

import database.DatabaseManager;
import entry.Entry;

/**
 * Created by 宇 on 2016/4/22.
 */
public class showActivity  extends AppCompatActivity {

    ListView listContent;

    public showActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        myFindView();
        try {
            myCreate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void myFindView() {
        listContent = (ListView) findViewById(R.id.listContent);
    }

    private void myCreate() throws ParseException {
        //在这里判别是哪种类别，然后选择哪种类别为visible,并把所有内容显示出来

        //shortHand.set_id(1);
        ArrayList<Entry> shortHand = new ArrayList<Entry>();
        shortHand = DatabaseManager.getInstance(this).getShortHand();

        ArrayList<Entry> DDL = new ArrayList<Entry>();
        DDL = DatabaseManager.getInstance(this).getDDL();

        ArrayAdapter<Entry> adapter = new ArrayAdapter<>(showActivity.this, android.R.layout.simple_list_item_1, shortHand);
        ArrayAdapter<Entry> adapter_ddl = new ArrayAdapter<>(showActivity.this, android.R.layout.simple_list_item_1, DDL);

        listContent.setAdapter(adapter_ddl);
    }
}

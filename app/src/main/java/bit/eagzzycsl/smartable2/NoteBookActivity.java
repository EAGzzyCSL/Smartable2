package bit.eagzzycsl.smartable2;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteBookActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ExpandableListView notelistview = null;
    private List<String> parent = null;
    private Map<String, List<String>> map = null;
    private ImageView arrow;
    private TextView choosed_notebook_title;
    private FloatingActionButton fab;
    private TextView tv_notebook_item;
    private TextView tv_note_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);
        myFindView();
        initData();
        mySetView();
        setTitle(null);//屏蔽掉title
    }

    //toolbar设置按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void myFindView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        notelistview = (ExpandableListView) this
                .findViewById(R.id.notebook_listview_id);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        choosed_notebook_title = (TextView)findViewById(R.id.notebook_activity_title_id);
    }

    private void mySetView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//顶部返回按钮

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        notelistview.setGroupIndicator(null);   //不显示左边展开收起箭头
        notelistview.setAdapter(new MyAdapter());

        notelistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                                 @Override
                                                 public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                                     //箭头旋转
                                                     arrow = (ImageView) v.findViewById(R.id.notebook_item_img_id);
                                                     if (notelistview.isGroupExpanded(groupPosition)) {
                                                         RotateAnimation myRotateDown = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF,
                                                                 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                                         myRotateDown.setDuration(500);//设置动画持续时间
                                                         myRotateDown.setFillAfter(true);//设置动画持续时间
                                                         arrow.setAnimation(myRotateDown);
                                                         myRotateDown.startNow();
                                                     } else {
                                                         RotateAnimation myRotateUp = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,
                                                                 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                                         myRotateUp.setDuration(500);//设置动画持续时间
                                                         myRotateUp.setFillAfter(true);//设置动画持续时间
                                                         arrow.setAnimation(myRotateUp);
                                                         myRotateUp.startNow();
                                                     }

                                                     //点击笔记本更换Activity的title
                                                     tv_notebook_item = (TextView) v.findViewById(R.id.notebook_item_tv_id);
                                                     choosed_notebook_title.setText(tv_notebook_item.getText());
                                                     return false;
                                                 }
                                             }

        );

        notelistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                tv_note_item = (TextView) v.findViewById(R.id.note_item_tv_id);

                Intent intent = new Intent(NoteBookActivity.this, NoteBookDetailActivity.class);
                intent.putExtra("note_title", tv_note_item.getText());
                startActivity(intent);

                return false;
            }
        });
        notelistview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }

    //toolbar返回按钮监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(NoteBookActivity.this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 初始化数据
    private void initData() {
        parent = new ArrayList<String>();
        parent.add("notebook1");
        parent.add("notebook2");
        parent.add("notebook3");

        map = new HashMap<String, List<String>>();

        List<String> list1 = new ArrayList<String>();
        list1.add("note1-1");
        list1.add("note1-2");
        list1.add("note1-3");
        map.put("notebook1", list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("note2-1");
        list2.add("note2-2");
        list2.add("note2-3");
        map.put("notebook2", list2);

        List<String> list3 = new ArrayList<String>();
        list3.add("note3-1");
        list3.add("note2-2");
        list3.add("note2-3");
        map.put("notebook3", list3);

    }

    class MyAdapter extends BaseExpandableListAdapter {

        //得到子item需要关联的数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = parent.get(groupPosition);
            return (map.get(key).get(childPosition));
        }

        //得到子item的ID
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //设置子item的组件
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String key = NoteBookActivity.this.parent.get(groupPosition);
            String info = map.get(key).get(childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) NoteBookActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_note, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.note_item_tv_id);
            tv.setText(info);
            return convertView;
        }

        //获取当前父item下的子item的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            String key = parent.get(groupPosition);
            int size=map.get(key).size();
            return size;
        }
        //获取当前父item的数据
        @Override
        public Object getGroup(int groupPosition) {
            return parent.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parent.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        //设置父item组件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) NoteBookActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_notebook, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.notebook_item_tv_id);
            tv.setText(NoteBookActivity.this.parent.get(groupPosition));

            arrow = (ImageView)convertView.findViewById(R.id.notebook_item_img_id);
//        判断isExpanded就可以控制是按下还是关闭，同时更换图片

//            if(isExpanded){
//                arrow.setImageResource(R.drawable.ic_arrow_down);
//            }else{
//                arrow.setImageResource(R.drawable.ic_arrow_up);
//            }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

}

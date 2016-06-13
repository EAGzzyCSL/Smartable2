package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.EnumExtra;
import bit.eagzzycsl.smartable2.ExtraFiled;
import bit.eagzzycsl.smartable2.IntentCode;
import bit.eagzzycsl.smartable2.NoteBookDetailEdit;
import bit.eagzzycsl.smartable2.R;
import database.SQLMan;
import entry.Entry;
import entry.EntryNotebookDetail;
import my.MyUtil;
import view.SlidingButtonView;

/**
 * Created by 宇 on 2016/5/26.
 */
public class Adapter_notebookDetail extends RecyclerView.Adapter<Adapter_notebookDetail.ViewHolder> implements SlidingButtonView.IonSlidingButtonListener {
    private ArrayList<? extends Entry> entries;
    private Context context;

    //为了加左划删除
    private SlidingButtonView mMenu = null;
    private OnItemClickListener mOnItemClickLitener;

    public Adapter_notebookDetail(ArrayList<? extends Entry> entries, Context context) {
        this.entries = entries;
        this.context = context;
        mOnItemClickLitener = (OnItemClickListener) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook_detail, parent, false);
        return new ViewHolder(itemView);
    }

    /*将数据与界面进行绑定的操作*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setContent(entries.get(position));
    }


    /*获取数据的数量*/
    @Override
    public int getItemCount() {
        if (entries == null) {
            return 0;
        }
        return entries.size();
    }

    /*自定义的ViewHolder，持有每个Item的的所有界面元素*/
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView item_tv_create;

        //为了左划删除
        public TextView btn_Delete;
        public ViewGroup layout_content;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_tv_notebook_title);
            item_tv_create = (TextView) itemView.findViewById(R.id.item_tv_create);

            //为添加左滑删除加的布局
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);
            ((SlidingButtonView) itemView).setSlidingButtonListener(Adapter_notebookDetail.this);
        }

        public void setContent(final Entry entry) {
            title.setText(entry.getTitle());
            MyUtil.settvTime(item_tv_create,((EntryNotebookDetail)entry).getDate_create());

            //设置内容布局的宽为屏幕宽度
            layout_content.getLayoutParams().width = MyUtil.getScreenWidth(context);

            layout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断是否有删除菜单打开
                    if (menuIsOpen()) {
                        closeMenu();//关闭菜单
                    } else {
                        int pos = getLayoutPosition();
                        mOnItemClickLitener.onItemClick(v, pos);

                        Intent intent = new Intent(context, NoteBookDetailEdit.class);
                        intent.putExtra(EnumExtra.getName(), EnumExtra.modifyEntry);
                        intent.putExtra(ExtraFiled.entryToEdit, entry);
                        ((Activity) context).startActivityForResult(intent, IntentCode.request_fromMainToEntryEdit);//todo 看看要不要改
                    }
                }
            });

            btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getLayoutPosition();
                    mOnItemClickLitener.onDeleteBtnCilck(v, pos);
                }
            });
        }
    }

    public void insertEntryNotebook(Entry entry) {
        ((ArrayList<Entry>) entries).add(entry);
        this.notifyItemInserted(entries.size());
    }

    public void removeEntryNotebook(Entry entry) {
        int i = getIndexInEntries(entry);
        entries.remove(i);
        this.notifyItemRemoved(i);
    }

    public void removeEntryNotebookByPosition(int position) {
        SQLMan.getInstance(context).delete(entries.get(position));
        entries.remove(position);
        this.notifyItemRemoved(position);
    }

    public void modifyEntryNotebook(Entry entry) {
        int i = getIndexInEntries(entry);
        ((ArrayList<Entry>) entries).set(i, entry);
        this.notifyItemChanged(i);
    }

    private int getIndexInEntries(Entry entry) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getId() == entry.getId()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        Log.i("asd", "mMenu为null");
        return false;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
        void onItemLongClick(View view , int position);
    }
}

package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.R;
import entry.Entry;
import entry.EntryDeadLine;
import entry.EntrySchedule;
import entry.EntryShortHand;
import entry.EntrySomeDay;
import entry.EntryTheseDays;


public class Adapter_main_kind_recyclerView extends RecyclerView.Adapter<Adapter_main_kind_recyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Entry> entries;

    //yu---
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }
    public interface OnItemClickListener<String>{
        void onItemClick(int position);
    }

    public Adapter_main_kind_recyclerView(ArrayList<Entry> entries, Context context) {
        this.entries = entries;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).
                inflate(R.layout.item_smart_serialize, parent, false));//yu------改样式  adapter_main_recycler_kind
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setContent(entries.get(position));

        final int pos = holder.getLayoutPosition();//待删除
        if(mListener == null) return;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_name;
        private TextView colorMyOrange;//速记
        private TextView colorMyYellow;//日程
        private TextView colorMyRed;//有朝一日
        private TextView colorMyPurple;//这两天
        private TextView colorMyBlue;//DDL

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.id_num_smart_serialize);

            colorMyOrange = (TextView) itemView.findViewById(R.id.colorMyOrange);
            colorMyYellow = (TextView) itemView.findViewById(R.id.colorMyYellow);
            colorMyRed = (TextView) itemView.findViewById(R.id.colorMyRed);
            colorMyPurple = (TextView) itemView.findViewById(R.id.colorMyPurple);
            colorMyBlue = (TextView) itemView.findViewById(R.id.colorMyBlue);
        }

        public void setContent(final Entry entry) {
            textView_name.setText(entry.getName());
            if(entry instanceof EntryShortHand){
                colorMyOrange.setVisibility(View.VISIBLE);
            }
            else if(entry instanceof EntrySchedule){
                colorMyYellow.setVisibility(View.VISIBLE);
            }
            else if(entry instanceof EntrySomeDay){
                colorMyRed.setVisibility(View.VISIBLE);
            }
            else if(entry instanceof EntryTheseDays){
                colorMyPurple.setVisibility(View.VISIBLE);
            }
            else if(entry instanceof EntryDeadLine){
                colorMyBlue.setVisibility(View.VISIBLE);
            }
        }
    }
}

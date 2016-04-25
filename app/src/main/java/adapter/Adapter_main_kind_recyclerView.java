package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    public interface OnItemClickListener<String> {
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
        if (mListener == null) return;
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
        private View view_color_indicator;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.id_num_smart_serialize);
            view_color_indicator = itemView.findViewById(R.id.view_color_indicator);

        }

        public void setContent(final Entry entry) {
            textView_name.setText(entry.getName());
            textView_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, entry.getName()+"/"+entry.getId(), Toast.LENGTH_SHORT).show();
                }
            });
            if (entry instanceof EntryShortHand) {
                view_color_indicator.setBackgroundColor(
                        context.getResources().getColor(R.color.colorMyOrange));
            } else if (entry instanceof EntrySchedule) {
                view_color_indicator.setBackgroundColor(
                        context.getResources().getColor(R.color.colorMyYellow));
            } else if (entry instanceof EntrySomeDay) {
                view_color_indicator.setBackgroundColor(
                        context.getResources().getColor(R.color.colorMyRed));
            } else if (entry instanceof EntryTheseDays) {
                view_color_indicator.setBackgroundColor(
                        context.getResources().getColor(R.color.colorMyPurple));
            } else if (entry instanceof EntryDeadLine) {
                view_color_indicator.setBackgroundColor(
                        context.getResources().getColor(R.color.colorMyBlue));
            }
        }
    }
}

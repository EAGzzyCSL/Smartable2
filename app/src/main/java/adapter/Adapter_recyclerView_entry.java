package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bit.eagzzycsl.smartable2.EnumExtra;
import bit.eagzzycsl.smartable2.ExtraFiled;
import bit.eagzzycsl.smartable2.IntentCode;
import bit.eagzzycsl.smartable2.ModifyDetailActivity;
import bit.eagzzycsl.smartable2.R;
import entry.Entry;


public class Adapter_recyclerView_entry extends RecyclerView.Adapter<Adapter_recyclerView_entry.ViewHolder> {
    private Context context;
    private ArrayList<? extends Entry> entries;

    public Adapter_recyclerView_entry(ArrayList<? extends Entry> entries, Context context) {
        this.entries = entries;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).
                inflate(R.layout.item_smart_serialize, parent, false));
        //yu------改样式  adapter_main_recycler_kind
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setContent(entries.get(position));
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
            textView_name.setText(entry.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ModifyDetailActivity.class);
                    intent.putExtra(EnumExtra.getName(), EnumExtra.modifyEntry);
                    intent.putExtra(ExtraFiled.entryToEdit, entry);
                    ((Activity) context).startActivityForResult(intent, IntentCode.request_fromMainToEntryEdit);
                }
            });
            view_color_indicator.setBackgroundColor(
                    ContextCompat.getColor(context,
                            entry.getType().getColorId())
            );
        }
    }

    @SuppressWarnings("unchecked")
    public void insertEntry(Entry entry) {
        ((ArrayList<Entry>) entries).add(entry);
        this.notifyItemInserted(entries.size());
        //TODO 泛型下的添加该如何做？
    }

    @SuppressWarnings("unchecked")
    public void updateEntry(Entry entry) {
        int i = getIndexInEntries(entry);
        ((ArrayList<Entry>) entries).set(i, entry);
        this.notifyItemChanged(i);

    }

    public void deleteEntry(Entry entry) {
        int i = getIndexInEntries(entry);
        entries.remove(i);
        this.notifyItemRemoved(i);

    }

    private int getIndexInEntries(Entry entry) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getId() == entry.getId()) {
                return i;
            }
        }
        return -1;
    }
}

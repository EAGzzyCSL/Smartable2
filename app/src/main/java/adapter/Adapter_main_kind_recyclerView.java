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


public class Adapter_main_kind_recyclerView extends RecyclerView.Adapter<Adapter_main_kind_recyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Entry> entries;

    public Adapter_main_kind_recyclerView(ArrayList<Entry> entries, Context context) {
        this.entries = entries;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).
                inflate(R.layout.adapter_main_recycler_kind, parent, false));

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

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.textView_name);
        }

        public void setContent(final Entry entry) {
            textView_name.setText(entry.getName());
        }
    }
}

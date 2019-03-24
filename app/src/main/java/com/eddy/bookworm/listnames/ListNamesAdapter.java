package com.eddy.bookworm.listnames;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eddy.bookworm.R;
import com.eddy.data.models.ListName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListNamesAdapter extends RecyclerView.Adapter<ListNamesAdapter.ListNamesViewHolder> {

    private Context context;
    private List<ListName> listNames;
    private  ListNameListener listNameListener;

    ListNamesAdapter(Context context, ListNameListener listNameListener) {
        this.context = context;
        this.listNameListener = listNameListener;
    }

    public interface ListNameListener {
        void onClick(ListName listName);
    }

    @NonNull
    @Override
    public ListNamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_name_item, parent, false);
        return new ListNamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNamesViewHolder holder, int position) {
        holder.bind(listNames.get(position));
    }

    @Override
    public int getItemCount() {
        if(listNames != null) {
            return listNames.size();
        }

        return 0;
    }

    void setListNames(List<ListName> listNames) {
        this.listNames = listNames;

        notifyDataSetChanged();
    }

    class ListNamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.list_name_tv)
        TextView listNameTextView;

        @BindView(R.id.latest_publish_date_tv)
        TextView latestPublishDate;

        ListNamesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(ListName listName) {
            listNameTextView.setText(listName.getDisplayName());
            latestPublishDate.setText(listName.getFormattedDate());
        }

        @Override
        public void onClick(View v) {
            listNameListener.onClick(listNames.get(getAdapterPosition()));
        }
    }
}

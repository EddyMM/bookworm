package com.eddy.bookworm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eddy.data.models.ListName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListNamesAdapter extends RecyclerView.Adapter<ListNamesAdapter.ListNamesViewHolder> {

    private Context context;
    private List<ListName> listNames;

    ListNamesAdapter(Context context) {
        this.context = context;
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

    class ListNamesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_name_tv)
        TextView listNameTextView;

        ListNamesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ListName listName) {
            listNameTextView.setText(listName.getDisplayName());
        }
    }
}

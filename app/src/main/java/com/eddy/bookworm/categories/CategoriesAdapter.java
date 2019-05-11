package com.eddy.bookworm.categories;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eddy.bookworm.R;
import com.eddy.data.models.entities.Category;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private Context context;
    private List<Category> categories;
    private  ListNameListener listNameListener;

    public CategoriesAdapter(Context context, ListNameListener listNameListener) {
        this.context = context;
        this.listNameListener = listNameListener;
    }

    public interface ListNameListener {
        void onClick(Category category);
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_name_item, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        if(categories != null) {
            return categories.size();
        }

        return 0;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;

        notifyDataSetChanged();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.list_name_tv)
        TextView listNameTextView;

        @BindView(R.id.latest_publish_date_tv)
        TextView latestPublishDate;

        @BindView(R.id.update_frequency_tv)
        TextView updateFrequencyTextView;

        CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Category category) {
            listNameTextView.setText(category.getDisplayName());
            latestPublishDate.setText(context.getString(
                    R.string.newest_published_on, category.getFormattedDate()));

            String updateFrequency = category.getUpdateFrequency();
            updateFrequencyTextView.setText(updateFrequency);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (updateFrequency.equals(context.getString(R.string.weekly_update))) {
                    updateFrequencyTextView.setBackground(context.getDrawable(R.drawable.weekly_update_background));
                } else if (updateFrequency.equals(context.getString(R.string.monthly_update))) {
                    updateFrequencyTextView.setBackground(context.getDrawable(R.drawable.monthly_update_background));
                } else {
                    Timber.d("NO FREQUENCY MATCH");
                }
            }
        }

        @Override
        public void onClick(View v) {
            listNameListener.onClick(categories.get(getAdapterPosition()));
        }
    }
}

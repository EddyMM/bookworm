package com.eddy.bookworm.bookdetail;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eddy.bookworm.R;
import com.eddy.bookworm.models.ParcelableBuyLink;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyingLinksAdapter extends RecyclerView.Adapter<BuyingLinksAdapter.BuyingLinksViewHolder> {

    private List<ParcelableBuyLink> buyLinks;
    private Context context;

    BuyingLinksAdapter(Context context, List<ParcelableBuyLink> buyLinkList) {
        this.context = context;
        this.buyLinks = buyLinkList;
    }

    @NonNull
    @Override
    public BuyingLinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.buying_link_item,
                        parent, false);
        return new BuyingLinksViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyingLinksViewHolder holder, int position) {
        holder.bind(buyLinks.get(position));
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (buyLinks != null) {
            size = buyLinks.size();
        }
        return size;
    }

    class BuyingLinksViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buying_link_text_view)
        TextView buyingLinkTextView;

        BuyingLinksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ParcelableBuyLink buyLink) {
            buyingLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
            Spanned html = Html.fromHtml(
                    context.getString(R.string.buying_link,
                    buyLink.getUrl(),
                    buyLink.getName())
            );
            buyingLinkTextView.setText(html);

        }
    }
}

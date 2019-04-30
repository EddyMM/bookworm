package com.eddy.bookworm.bookslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eddy.bookworm.R;
import com.eddy.bookworm.base.DynamicHeightImageView;
import com.eddy.bookworm.models.ParcelableBook;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private List<ParcelableBook> books;
    private Context context;

    private BooksListListener booksListListener;

    BooksAdapter(Context context, BooksListListener booksListListener) {
        this.context = context;
        this.booksListListener = booksListListener;
    }

    public interface BooksListListener {
        void onClick(ParcelableBook book, ImageView imageView);
    }

    void setBooks(List<ParcelableBook> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.books_list_item, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.bind(books.get(position));
    }

    @Override
    public int getItemCount() {
        if (books != null) {
            return books.size();
        }

        return 0;
    }

    class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.book_list_item_iv)
        DynamicHeightImageView bookImageView;

        @BindView(R.id.book_item_title_tv)
        TextView titleTextView;

        @BindView(R.id.book_item_author_tv)
        TextView authorTextView;

        BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(ParcelableBook book) {
            Integer width = book.getBookImageWidth(); marvel HD
            Integer height = book.getBookImageHeight();
            if (width != null && height != null) {
                float aspectRatio = width / height;
                if (aspectRatio > 0) {
                    bookImageView.setAspectRatio(aspectRatio);
                }
            }

            Picasso.get()
                    .load(book.getBookImageUrl())
                    .placeholder(R.drawable.ic_image_grey_24dp)
                    .error(R.drawable.ic_broken_image_grey_24dp)
                    .into(bookImageView);
            titleTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthor());
        }

        @Override
        public void onClick(View v) {
            ParcelableBook book = books.get(getAdapterPosition());
            booksListListener.onClick(book, bookImageView);
        }
    }

}

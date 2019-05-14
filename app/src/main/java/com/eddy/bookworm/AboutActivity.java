package com.eddy.bookworm;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.credit_books_api_text_view)
    TextView creditBooksApiTextView;

    @BindView(R.id.credit_flaticon_text_view)
    TextView creditFlatIconTextView;

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle(R.string.about_title);
        ButterKnife.bind(this);

        initUI();
    }

    private void initUI() {
        Spanned creditBooksApiHtml = Html.fromHtml(
                getString(R.string.credit_books_api));
        Spanned creditFlatIconHtml = Html.fromHtml(
                getString(R.string.credit_flat_icon));
        Spanned descriptionHtml = Html.fromHtml(
                getString(R.string.app_description));

        creditBooksApiTextView.setText(creditBooksApiHtml);
        creditFlatIconTextView.setText(creditFlatIconHtml);
        descriptionTextView.setText(descriptionHtml);
    }

}

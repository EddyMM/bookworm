package com.eddy.bookworm.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.eddy.data.AppDataManager;
import com.eddy.data.models.Book;

import java.util.List;

import androidx.annotation.Nullable;

public class GetRandomBookIntentService extends IntentService {

    public static void startService(Context context) {
        Intent intent = new Intent(context, GetRandomBookIntentService.class);
        context.startService(intent);
    }

    public GetRandomBookIntentService() {
        super("GetRandomBookIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AppDataManager appDataManager = new AppDataManager();
        String listName = appDataManager.getPreferredCategoryCode(getApplicationContext());

        getRandomBook(listName);
    }

    private void getRandomBook(String listName) {
        AppDataManager appDataManager = new AppDataManager();

        List<Book> bookList = appDataManager.getCategoriesSync(listName);

        if (bookList != null) {
            int randomBookPosition = (int) Math.floor(Math.random() * bookList.size());

            Book widgetBook = bookList.get(randomBookPosition);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(getApplicationContext(), BookwormRandomBestSellerWidget.class));

            BookwormRandomBestSellerWidget.updateAppWidgets(
                    getApplicationContext(),
                    appWidgetManager,
                    appWidgetIds,
                    widgetBook.getBookImageUrl());
        }
    }
}

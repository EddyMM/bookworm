package com.eddy.bookworm.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.eddy.bookworm.R;
import com.eddy.data.AppDataManager;
import com.squareup.picasso.Picasso;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BookwormRandomBestSellerWidgetConfigureActivity BookwormRandomBestSellerWidgetConfigureActivity}
 */
public class BookwormRandomBestSellerWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds, int appWidgetId, String bookImageUrl) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bookworm_random_best_seller_widget);
        if (!TextUtils.isEmpty(bookImageUrl)) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Picasso.get()
                    .load(bookImageUrl)
                    .into(views, R.id.widget_image_view, appWidgetIds));
        } else {
            views.setImageViewResource(R.id.widget_image_view, R.drawable.ic_broken_image_grey_24dp);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        GetRandomBookIntentService.startService(context);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String bookmarkImgUrl) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetIds, appWidgetId, bookmarkImgUrl);
        }
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        AppDataManager appDataManager = new AppDataManager();
        appDataManager.removePreferredCategoryCode(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}


package com.icebeal.gitxclone.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.icebeal.gitxclone.R

class FavoriteWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if(intent.action != null){

            if(intent.action == TOAST_ACTION){

                val viewIndex = intent.getIntExtra(ITEM, 0)

                Toast.makeText(context, "Touched View $viewIndex", Toast.LENGTH_SHORT).show()

            }

        }

    }

    companion object{

        private const val TOAST_ACTION = "Toast_Action"
        const val ITEM = "item"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val intent = Intent(context, FavWidgetService::class.java)

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favorite_widget)

            views.setRemoteAdapter(R.id.fav_stack, intent)
            views.setEmptyView(R.id.fav_stack, R.id.empty_view)

            val toastIntent = Intent(context, FavoriteWidget::class.java)

            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            views.setPendingIntentTemplate(R.id.fav_stack, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)

        }

    }

}
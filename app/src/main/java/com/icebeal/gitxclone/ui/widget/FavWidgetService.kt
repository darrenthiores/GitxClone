package com.icebeal.gitxclone.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService

class FavWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {

        return FavWidgetFactory(this.applicationContext)

    }

}
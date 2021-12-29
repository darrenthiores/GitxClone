package com.icebeal.gitxclone.ui.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.icebeal.gitxclone.R
import com.icebeal.gitxclone.data.db.UserDb
import com.icebeal.gitxclone.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavWidgetFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val imageArray = ArrayList<Bitmap>()
    private val userArray = ArrayList<User>()

    override fun onCreate() {

        GlobalScope.launch(Dispatchers.Main) {

            val dao = UserDb.getInstance(context).userDao()
            val user = async(Dispatchers.IO) {

                dao.query()

            }

            userArray.addAll(user.await())

        }

    }

    override fun onDataSetChanged() {

        for (item in userArray){

            val avatar = Glide.with(context)
                .asBitmap()
                .load(item.avatar)
                .submit(512, 512)
                .get()

            imageArray.add(avatar)

        }

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = userArray.size

    override fun getViewAt(position: Int): RemoteViews {

        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imgView, imageArray[position])

        val extras = bundleOf(FavoriteWidget.ITEM to position)

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imgView, fillInIntent)

        return rv

    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}
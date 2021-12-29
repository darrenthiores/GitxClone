package com.icebeal.gitxclone.ui.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.icebeal.gitxclone.data.db.UserDao
import com.icebeal.gitxclone.data.db.UserDb

class UserProvider : ContentProvider() {

    private lateinit var userDao: UserDao

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = throw UnsupportedOperationException()

    override fun getType(uri: Uri): String? = throw UnsupportedOperationException()

    override fun insert(uri: Uri, values: ContentValues?): Uri? = throw UnsupportedOperationException()

    override fun onCreate(): Boolean {

        userDao = UserDb.getInstance(context as Context).userDao()

        return true

    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        return when(uriMatcher.match(uri)){

            USER -> userDao.queryAllCP()
            else -> null

        }

    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }

    companion object{

        private const val AUTHORITY = "com.icebeal.favorite"
        private const val TABLE_DAO = "user_favorite"

        private const val USER = 1

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply { addURI(AUTHORITY, TABLE_DAO, USER) }

        val CONTENT_URI : Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_DAO)
            .build()

    }

}
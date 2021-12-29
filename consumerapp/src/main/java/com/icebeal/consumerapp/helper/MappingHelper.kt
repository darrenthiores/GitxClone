package com.icebeal.consumerapp.helper

import android.database.Cursor
import com.icebeal.consumerapp.MainActivity
import com.icebeal.consumerapp.model.User

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?) : ArrayList<User>{

        val list = ArrayList<User>()

        cursor?.apply {

            while (moveToNext()){

                list.add(

                    User(

                        getString(getColumnIndexOrThrow(MainActivity.USERNAME)),
                        getString(getColumnIndexOrThrow(MainActivity.AVATAR))

                    )

                )

            }

        }

        return list

    }

}
package com.abcd.pemantauankesehatananak.utils

import android.view.View
import com.abcd.pemantauankesehatananak.data.model.UserModel

interface OnClickItem {
    interface ClickUser{
        fun clickUser(
            user: UserModel
        )
    }

}
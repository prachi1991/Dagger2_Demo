/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ballchalu.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.ballchalu.shared.util.ConstantsBase.THEME_DARK_MODE
import com.ballchalu.shared.util.ConstantsBase.THEME_DEFAULT_MODE
import com.ballchalu.shared.util.ConstantsBase.THEME_LIGHT_MODE

object ThemeHelper {
    fun applyTheme(themePref: String) {
        when (themePref) {
            THEME_DARK_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            THEME_LIGHT_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

    fun getSelectedTheme(item: Int): String {
        when (item) {
            0 -> {
                applyTheme(THEME_DARK_MODE)
                return THEME_DARK_MODE
            }
            1 -> {
                applyTheme(THEME_LIGHT_MODE)
                return THEME_LIGHT_MODE
            }
            2 -> {
                applyTheme(THEME_DEFAULT_MODE)
                return THEME_DEFAULT_MODE
            }
        }
        return THEME_DEFAULT_MODE
    }
}
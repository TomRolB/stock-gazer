package com.example.stockgazer.util

import android.content.Context
import com.example.stockgazer.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

interface TimeZoneProvider {
    fun getTimeZone(): ZoneId
}

@Singleton
class ResourceZoneIdProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : TimeZoneProvider {
    override fun getTimeZone(): ZoneId {
        val zoneString = context.getString(R.string.time_zone)
        return ZoneId.of(zoneString)
    }
}
package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.remote.PredefinedRoutesData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Rota Rehberi", appName)
    }

    @Test
    fun `verify predefined routes data exists`() {
        val presets = PredefinedRoutesData.presets
        assertTrue(presets.isNotEmpty())
        val istanbulAntalya = PredefinedRoutesData.findPreset("İstanbul", "Antalya")
        assertTrue(istanbulAntalya != null)
        assertTrue(istanbulAntalya!!.stops.size >= 4)
    }
}

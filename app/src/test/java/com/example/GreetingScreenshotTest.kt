package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.example.data.remote.PredefinedRoutesData
import com.example.ui.components.WaypointStopCard
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.CalculatedStopTiming
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun waypoint_card_screenshot() {
        val sampleStop = PredefinedRoutesData.presets[0].stops[0]
        val timing = CalculatedStopTiming(
            stop = sampleStop,
            arrivalTimeFormatted = "10:30",
            departureTimeFormatted = "11:15",
            isOpenAtArrival = true,
            minutesFromDeparture = 120,
            openHoursStatusText = "Varışta Açık (08:30 - 19:00)"
        )

        composeTestRule.setContent {
            MyApplicationTheme {
                WaypointStopCard(
                    timing = timing,
                    isSpeaking = false,
                    isFavorite = false,
                    onToggleSelect = {},
                    onToggleFavorite = {},
                    onSpeakAudioGuide = {},
                    onClickDetail = {}
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
    }
}

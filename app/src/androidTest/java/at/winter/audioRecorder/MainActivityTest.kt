package at.winter.audioRecorder

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.AnnotatedString
import org.junit.Rule
import org.junit.Test

class MainActivityTest{

    @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun RecordButtonTest(){
        val recordButton = composeTestRule.onNode(SemanticsMatcher.expectValue(SemanticsProperties.ContentDescription,
            listOf("Record Button")
        ))
        val isButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button )
        val hasText = SemanticsMatcher.expectValue(SemanticsProperties.Text,
            listOf(AnnotatedString(composeTestRule.activity.getString(R.string.show_recordings)))
        )
        val showRecordingsButton = composeTestRule.onNode(isButton and hasText)

        recordButton.assertExists("Record Button does not exist")
        recordButton.performClick()
        showRecordingsButton.assertDoesNotExist()
        recordButton.performClick()
        composeTestRule.waitUntilAtLeastOneExists(isButton and hasText, 5000L)
    }

    @Test
    fun MainScreenTitleTest(){
        val mainScreenTitle =  composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.main_screen_title))
        mainScreenTitle.assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun ShowRecordingsButtonIsClickable(){
        val isButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button )
        val hasText = SemanticsMatcher.expectValue(SemanticsProperties.Text,
            listOf(AnnotatedString(composeTestRule.activity.getString(R.string.show_recordings)))
        )
        val hasTitle = SemanticsMatcher.expectValue(SemanticsProperties.Text,
            listOf(AnnotatedString(composeTestRule.activity.getString(R.string.recordings_screen_title)))
        )
        val showRecordingsButton = composeTestRule.onNode(isButton and hasText)
        showRecordingsButton.assertExists("Record Button does not exist")
        showRecordingsButton.performClick()
        composeTestRule.waitUntilAtLeastOneExists(hasTitle, 5000L)

    }
}
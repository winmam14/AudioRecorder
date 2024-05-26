package at.winter.audioRecorder

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import org.junit.Rule
import org.junit.Test

class MainActivityTest{

    @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun RecordButtonExists(){
        val recordButton = SemanticsMatcher.expectValue(SemanticsProperties.ContentDescription,
            listOf("Record Button")
        )

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.main_screen_title)).assertIsDisplayed()
        composeTestRule.onNode(recordButton).assertExists("Record Button does not exist")
    }

    @Test
    fun MainScreenTitleExists(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.main_screen_title)).assertIsDisplayed()
    }

    @Test
    fun ShowRecordingsButtonExists(){
        val isButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button )
        val hasText = SemanticsMatcher.expectValue(SemanticsProperties.Text,
            listOf(AnnotatedString(composeTestRule.activity.getString(R.string.show_recordings)))
        )
        composeTestRule.onNode(isButton and hasText).assertExists("Record Button does not exist")
    }
}
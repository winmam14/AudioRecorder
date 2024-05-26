package at.winter.audioRecorder.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import at.winter.audioRecorder.data.Recording
import at.winter.audioRecorder.data.RecordingDaoTestDouble
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class RecordingViewModelTest{

    private lateinit var viewModel: RecordingViewModel
    private lateinit var testDouble: RecordingDaoTestDouble

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        Dispatchers.setMain(UnconfinedTestDispatcher())
        testDouble = RecordingDaoTestDouble()
        viewModel = RecordingViewModel(testDouble)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSortRecordingsUodatesSortTye() = runTest{
            val initialState = viewModel.state.first()

            viewModel.onEvent(RecordingEvent.SortRecordings(SortType.FILE_SIZE))

            val updatedState = viewModel.state.first()
            assertNotEquals(initialState.sortType, updatedState.sortType)
            assertEquals(updatedState.sortType,SortType.FILE_SIZE)
    }

    @Test
    fun testStopRecording() = runTest{
        val recording = Recording(
            name = "Test Recording",
            size = 1024,
            file = ByteArray(10),
            duration = 60L,
            unixTimestamp = 1627492023L
        )

        viewModel.onEvent(RecordingEvent.StartRecording)
        var state = viewModel.state.first()
        assertEquals(state.isRecording, true)
        viewModel.onEvent(RecordingEvent.StopRecording(recording))
        state = viewModel.state.first()
        assertEquals(state.isRecording, false)
        println(testDouble.getRecordsOrderedBySize().first().size)
        assertTrue(testDouble.getRecordsOrderedBySize().first().size > 0)
    }




}
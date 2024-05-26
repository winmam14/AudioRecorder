package at.winter.audioRecorder.utils

import androidx.compose.ui.res.stringResource
import at.winter.audioRecorder.R

enum class SortType(val resourceID: Int) {
    TIMESTAMP_ASC(R.string.sort_timestamp_oldest), TIMESTAMP_DESC(R.string.sort_timestamp_newest), FILE_SIZE(R.string.sort_size)
}
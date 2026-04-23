package tmg.flashback.widgets.upnext.presentation.style.preview

import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview

const val Widget1Min = 56
const val Widget2Min = 109
const val Widget3Min = 185
const val Widget4Min = 245

const val Widget1Max = 130
const val Widget2Max = 306
const val Widget3Max = 422
const val Widget4Max = 624

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget1Min, heightDp = Widget1Min)
@Preview(widthDp = Widget1Max, heightDp = Widget1Max)
annotation class Preview1x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget1Min, heightDp = Widget2Min)
@Preview(widthDp = Widget1Max, heightDp = Widget2Max)
annotation class Preview1x2

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget1Min, heightDp = Widget3Min)
@Preview(widthDp = Widget1Max, heightDp = Widget3Max)
annotation class Preview1x3

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget2Min, heightDp = Widget1Min)
@Preview(widthDp = Widget2Max, heightDp = Widget1Max)
annotation class Preview2x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget2Min, heightDp = Widget2Min)
@Preview(widthDp = Widget2Max, heightDp = Widget2Max)
annotation class Preview2x2

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget2Min, heightDp = Widget3Min)
@Preview(widthDp = Widget2Max, heightDp = Widget3Max)
annotation class Preview2x3

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget3Min, heightDp = Widget1Min)
@Preview(widthDp = Widget3Max, heightDp = Widget1Max)
annotation class Preview3x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget3Min, heightDp = Widget2Min)
@Preview(widthDp = Widget3Max, heightDp = Widget2Max)
annotation class Preview3x2

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget3Min, heightDp = Widget3Min)
@Preview(widthDp = Widget3Max, heightDp = Widget3Max)
annotation class Preview3x3

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget4Min, heightDp = Widget1Min)
@Preview(widthDp = Widget4Max, heightDp = Widget1Max)
annotation class Preview4x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget4Min, heightDp = Widget2Min)
@Preview(widthDp = Widget4Max, heightDp = Widget2Max)
annotation class Preview4x2

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = Widget4Min, heightDp = Widget3Min)
@Preview(widthDp = Widget4Max, heightDp = Widget3Max)
annotation class Preview4x3

@Preview1x1
@Preview2x1
@Preview2x2
@Preview2x3
@Preview3x1
@Preview3x2
@Preview3x3
@Preview4x1
@Preview4x2
@Preview4x3
annotation class PreviewAllSizes



@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 64, heightDp = 106)
annotation class PreviewPixel1x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 140, heightDp = 106)
annotation class PreviewPixel2x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 140, heightDp = 229)
annotation class PreviewPixel2x2

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 216, heightDp = 106)
annotation class PreviewPixel3x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 216, heightDp = 229)
annotation class PreviewPixel3x2

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 216, heightDp = 356)
annotation class PreviewPixel3x3

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 292, heightDp = 106)
annotation class PreviewPixel4x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 292, heightDp = 229)
annotation class PreviewPixel4x2

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 292, heightDp = 352)
annotation class PreviewPixel4x3

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 368, heightDp = 106)
annotation class PreviewPixel5x1

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 368, heightDp = 229)
annotation class PreviewPixel5x2

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 368, heightDp = 352)
annotation class PreviewPixel5x3

@PreviewPixel1x1
@PreviewPixel2x1
@PreviewPixel2x2
@PreviewPixel3x1
@PreviewPixel3x2
@PreviewPixel3x3
@PreviewPixel4x1
@PreviewPixel4x2
@PreviewPixel4x3
@PreviewPixel5x1
@PreviewPixel5x2
@PreviewPixel5x3
annotation class PreviewPixel

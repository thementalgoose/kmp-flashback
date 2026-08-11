package tmg.flashback.ai.manager

import android.app.Application
import android.content.Context
import com.google.mlkit.genai.common.DownloadCallback
import com.google.mlkit.genai.common.FeatureStatus
import com.google.mlkit.genai.common.GenAiException
import com.google.mlkit.genai.summarization.Summarization
import com.google.mlkit.genai.summarization.SummarizationRequest
import com.google.mlkit.genai.summarization.Summarizer
import com.google.mlkit.genai.summarization.SummarizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class AiManagerImpl(
    private val context: Application
): AiManager {
    actual override fun isAvailable(): Boolean {
        return true
    }

    actual override suspend fun status(): String {
        val summarizerOptions = SummarizerOptions.builder(context)
            .setLanguage(SummarizerOptions.Language.ENGLISH)
            .setOutputType(SummarizerOptions.OutputType.THREE_BULLETS)
            .build()
        val summarizer = Summarization.getClient(summarizerOptions)

        val featureStatus = summarizer.checkFeatureStatus().get()

        val article = "Summarise the 2026 Australian Grand Prix"

        val result = if (featureStatus == FeatureStatus.DOWNLOADABLE) {
            // Download feature if necessary. If downloadFeature is not called,
            // the first inference request will also trigger the feature to be
            // downloaded if it's not already downloaded.
            return suspendCancellableCoroutine<String> { continuation ->
                summarizer.downloadFeature(object : DownloadCallback {
                    override fun onDownloadStarted(bytesToDownload: Long) { }

                    override fun onDownloadFailed(e: GenAiException) { }

                    override fun onDownloadProgress(totalBytesDownloaded: Long) {}

                    override fun onDownloadCompleted() {
                        continuation.resume (startSummarizationRequest(article, summarizer))
                    }
                })
            }
        } else if (featureStatus == FeatureStatus.DOWNLOADING) {
            // Inference request will automatically run once feature is
            // downloaded. If Gemini Nano is already downloaded on the device,
            // the feature-specific LoRA adapter model will be downloaded
            // quickly. However, if Gemini Nano is not already downloaded, the
            // download process may take longer.
            startSummarizationRequest(article, summarizer)
        } else if (featureStatus == FeatureStatus.AVAILABLE) {
            startSummarizationRequest(article, summarizer)
        } else {
            "Unknown: $featureStatus"
        }

        return "$featureStatus -> $result"
    }

    fun startSummarizationRequest(text: String, summarizer: Summarizer): String {
        val summarizationRequest = SummarizationRequest.builder(text).build()

        val result = summarizer.runInference(summarizationRequest).get().summary

        return result
    }
}
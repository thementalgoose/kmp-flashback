package tmg.flashback.data.repo.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tmg.flashback.data.repo.model.Response
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.flashbackapi.api.api.NotFoundException
import tmg.flashback.infrastructure.log.logException

suspend fun <T> FlashbackApi.makeRequest(
    request: suspend (FlashbackApi) -> T,
    response: suspend (T) -> Boolean
): Response {
    return withContext(Dispatchers.IO) {
        try {
            val response = request(this@makeRequest)
            val processed = response(response)
            when (processed) {
                true -> Response.Successful
                false -> Response.Unknown
            }
        } catch (e: NotFoundException) {
            logException(e)
            Response.NotFound
        } catch (e: RuntimeException) {
            logException(e)
            Response.Unknown
        } catch (e: Exception) {
            logException(e)
            Response.Unknown
        }
    }
}
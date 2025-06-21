package tmg.flashback.data.repo.utils

import tmg.flashback.data.repo.model.Response
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.flashbackapi.api.api.NotFoundException
import tmg.flashback.infrastructure.log.logException

suspend fun <T> FlashbackApi.makeRequest(
    request: suspend (FlashbackApi) -> T,
    response: suspend (T) -> Boolean
): Response {
    return try {
        val response = request(this)
        val processed = response(response)
        when (processed) {
            true -> Response.Successful
            false -> Response.Unknown
        }
    } catch (e: NotFoundException) {
        logException(e)
        return Response.NotFound
    } catch (e: RuntimeException) {
        logException(e)
        return Response.Unknown
    } catch (e: Exception) {
        logException(e)
        return Response.Unknown
    }
}
package tmg.flashback.data.repo.model

sealed class Response {
    data object Successful: Response()
    data object NotFound: Response()
    data object Unknown: Response()
}
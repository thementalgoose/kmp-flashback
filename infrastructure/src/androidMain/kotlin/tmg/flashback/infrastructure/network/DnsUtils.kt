package tmg.flashback.infrastructure.network

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress

object DnsUtils {
    fun getDns(client: OkHttpClient) = DnsOverHttps.Builder()
        .client(client)
        .url("https://1.1.1.1/dns-query".toHttpUrl())
        .includeIPv6(false)
        .bootstrapDnsHosts(
            InetAddress.getByName("1.1.1.1"),
            InetAddress.getByName("8.8.8.8")
        )
        .build()
}
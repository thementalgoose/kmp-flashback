package tmg.flashback

import kotlinx.datetime.LocalDate
import tmg.flashback.infrastructure.datetime.now

object RemoteConfigDefaults {

    private val FIRST_SEASON = 1950

    val defaults = mapOf(
        "config_url" to "https://flashback.pages.dev",
        "data_provided" to "All data sourced with ♥ by the Flashback team",
        "default_year" to generateDefaultSeason(),
        "easteregg_snow" to false,
        "easteregg_summer" to false,
        "easteregg_ukraine" to false,
        "email" to "thementalgoose@gmail.com",
        "privacy_policy_url" to "https://flashback.pages.dev/privacy-policy.html",
        "reaction_game" to false,
        "rss" to false,
        "rss_add_custom" to false,
        "rss_supported_sources" to rssSupportedSources(),
        "soft_upgrade" to false,
        "supported_seasons" to generateSeasonArray(),
    )

    private fun rssSupportedSources(): String = """{
        "sources": [
            {
              "rssLink": "https://www.formula1.com/content/fom-website/en/latest/all.xml",
              "sourceShort": "F1",
              "source": "https://www.formula1.com",
              "colour": "#E30012",
              "textColour": "#ffffff",
              "title": "Formula 1",
              "contactLink": "https://www.formula1.com/en/latest/article.faqs-and-contact-information.1TqkueKz2aRd9diRUQlq2U.html"
            },
            {
              "rssLink": "https://www.autosport.com/rss/feed/f1",
              "sourceShort": "AS",
              "source": "https://www.autosport.com",
              "colour": "#FF0000",
              "textColour": "#ffffff",
              "title": "Autosport",
              "contactLink": "https://www.autosport.com/contact/"
            },
            {
              "rssLink": "https://crash.net/rss/f1",
              "sourceShort": "C",
              "source": "https://www.crash.net",
              "colour": "#E91B1C",
              "textColour": "#ffffff",
              "title": "Crash.net",
              "contactLink": "https://www.crash.net/contact"
            },
            {
              "rssLink": "https://motorsport.com/rss/f1/news/",
              "sourceShort": "MS",
              "source": "https://www.motorsport.com",
              "colour": "#FFD806",
              "textColour": "#181818",
              "title": "Motorsport",
              "contactLink": "https://www.motorsport.com/info/contact/"
            },
            {
              "rssLink": "https://www.pitpass.com/fes_php/fes_usr_sit_newsfeed.php",
              "sourceShort": "PP",
              "source": "https://www.pitpass.com",
              "colour": "#611818",
              "textColour": "#ffffff",
              "title": "PitPass",
              "contactLink": "https://www.pitpass.com/contact-us"
            },
            {
              "rssLink": "https://www.f1-fansite.com/feed/",
              "sourceShort": "FF",
              "source": "https://www.f1-fansite.com",
              "colour": "#DF2C19",
              "textColour": "#ffffff",
              "title": "F1 Fansite",
              "contactLink": "https://www.f1-fansite.com/about-us/"
            },
            {
              "rssLink": "https://feeds.bbci.co.uk/sport/formula1/rss.xml",
              "sourceShort": "BBC",
              "source": "https://www.bbc.co.uk",
              "colour": "#FFD04C",
              "textColour": "#181818",
              "title": "BBC Sport",
              "contactLink": "https://www.bbc.co.uk/sport/15561348"
            },
            {
              "rssLink": "https://www.theguardian.com/sport/formulaone/rss",
              "sourceShort": "TG",
              "source": "https://www.theguardian.com",
              "colour": "#002B60",
              "textColour": "#ffffff",
              "title": "The Guardian",
              "contactLink": "https://www.theguardian.com/help/contact-us"
            },
            {
              "rssLink": "https://wtf1.com/feed/",
              "sourceShort": "WTF",
              "source": "https://wtf1.com",
              "colour": "#F66733",
              "textColour": "#ffffff",
              "title": "WTF1",
              "contactLink": "https://wtf1.com/about-us/"
            },
            {
              "rssLink": "https://www.grandprix247.com/feed/",
              "sourceShort": "GP",
              "source": "https://www.grandprix247.com",
              "colour": "#BD0216",
              "textColour": "#ffffff",
              "title": "GrandPrix247",
              "contactLink": "https://www.grandprix247.com/contact/"
            },
            {
              "rssLink": "https://en.f1i.com/news/feed",
              "sourceShort": "F1I",
              "source": "https://en.f1i.com",
              "colour": "#EC192D",
              "textColour": "#ffffff",
              "title": "F1i",
              "contactLink": "https://f1i.com/contact"
            },
            {
              "rssLink": "https://www.fia.com/rss/press-release",
              "sourceShort": "FIA",
              "source": "https://www.fia.com",
              "colour": "#112F60",
              "textColour": "#ffffff",
              "title": "FIA",
              "contactLink": "https://www.fia.com/contact-0"
            },
            {
              "rssLink": "https://www.f1technical.net/rss/news.xml",
              "sourceShort": "FT",
              "source": "https://www.f1technical.net/",
              "colour": "#2E6A9B",
              "textColour": "#ffffff",
              "title": "F1Technical",
              "contactLink": "https://www.f1technical.net/pr/feedback.php"
            }
        ]
    }""".trimIndent()

    private fun generateDefaultSeason(): Int = LocalDate.now().year

    private fun generateSeasonArray(): String {
        val allSeasons = (LocalDate.now().year) - FIRST_SEASON
        val seasons = List(allSeasons + 1) { FIRST_SEASON + it }
        val seasonArray = seasons.joinToString(separator = ",") { """{"s":$it}"""}
        return """{"seasons":[${seasonArray}]}"""
    }
}
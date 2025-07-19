<p align="center">
  <img width="180" src="resources/app_icon.png" />
</p>

<h1 align="center">Flashback</h1>

<p align="center">
  <a href="https://play.google.com/store/apps/details?id=tmg.flashback"><img src="https://img.shields.io/static/v1?label=Google%20Play&message=%20&logo=google-play&color=success&style=flat"/></a>
  <a href="https://apps.apple.com/us/app/flashback-formula-results/id6748612648"><img src="https://img.shields.io/static/v1?label=App%20Store&message=%20&logo=app-store&color=success&style=flat"/></a>
  <a href="https://flashback.pages.dev"><img src="https://img.shields.io/static/v1?label=App%20Store&message=%20&logo=cloudflare&color=success&style=flat"/></a>
  <a href="https://github.com/thementalgoose/kmp-flashback/actions"><img src="https://github.com/thementalgoose/kmp-flashback/workflows/Release/badge.svg"/></a>
</p>

Flashback provides race results and statistics from every race in the history of Formula 1 and the latest Formula 1 news from around the web!

### Screenshots

<details>
<summary><h5>Android</h5></summary>

| |                                                                               |                                                                               |                                                                               |                                                                               |                                                                               |                                                                               |                                                                               |
|---|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|
| <img src="resources/android/screenshots/phone/screenshot1.png" width="100" /> | <img src="resources/android/screenshots/phone/screenshot2.png" width="100" /> | <img src="resources/android/screenshots/phone/screenshot2.png" width="100" /> | <img src="resources/android/screenshots/phone/screenshot4.png" width="100" /> | <img src="resources/android/screenshots/phone/screenshot5.png" width="100" /> | <img src="resources/android/screenshots/phone/screenshot6.png" width="100" /> | <img src="resources/android/screenshots/phone/screenshot7.png" width="100" /> | <img src="resources/android/screenshots/phone/screenshot8.png" width="100" /> |

| |                                                                                  |                                                                                  |                                                                                  |                                                                                  |                                                                                  |
|---|----------------------------------------------------------------------------------|----------------------------------------------------------------------------------|----------------------------------------------------------------------------------|----------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| <img src="resources/android/screenshots/tablet_7/screenshot1.png" width="120" /> | <img src="resources/android/screenshots/tablet_7/screenshot2.png" width="120" /> | <img src="resources/android/screenshots/tablet_7/screenshot3.png" width="120" /> | <img src="resources/android/screenshots/tablet_7/screenshot4.png" width="120" /> | <img src="resources/android/screenshots/tablet_7/screenshot5.png" width="120" /> | <img src="resources/android/screenshots/tablet_7/screenshot6.png" width="120" /> |

|                                                                                   |                                                                                   |                                                                                   |                                                                                   |
|-----------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| <img src="resources/android/screenshots/tablet_10/screenshot1.png" width="180" /> | <img src="resources/android/screenshots/tablet_10/screenshot2.png" width="180" /> | <img src="resources/android/screenshots/tablet_10/screenshot3.png" width="180" /> | <img src="resources/android/screenshots/tablet_10/screenshot4.png" width="180" /> |

</details>
<details>
<summary><h5>iOS</h5></summary>

| |                                                                            |                                                                            |                                                                            |                                                                            |                                                                            |                                                                            |                                                                            |
|---|----------------------------------------------------------------------------|----------------------------------------------------------------------------|----------------------------------------------------------------------------|----------------------------------------------------------------------------|----------------------------------------------------------------------------|----------------------------------------------------------------------------|----------------------------------------------------------------------------|
| <img src="resources/ios/screenshots/iphone/screenshot1.png" width="100" /> | <img src="resources/ios/screenshots/iphone/screenshot2.png" width="100" /> | <img src="resources/ios/screenshots/iphone/screenshot3.png" width="100" /> | <img src="resources/ios/screenshots/iphone/screenshot4.png" width="100" /> | <img src="resources/ios/screenshots/iphone/screenshot5.png" width="100" /> | <img src="resources/ios/screenshots/iphone/screenshot6.png" width="100" /> | <img src="resources/ios/screenshots/iphone/screenshot7.png" width="100" /> | <img src="resources/ios/screenshots/iphone/screenshot8.png" width="100" /> |

| |                                                                          |                                                                          |                                                                          |                                                                          |
|---|--------------------------------------------------------------------------|--------------------------------------------------------------------------|--------------------------------------------------------------------------|--------------------------------------------------------------------------|
| <img src="resources/ios/screenshots/ipad/screenshot1.png" width="130" /> | <img src="resources/ios/screenshots/ipad/screenshot2.png" width="130" /> | <img src="resources/ios/screenshots/ipad/screenshot3.png" width="130" /> | <img src="resources/ios/screenshots/ipad/screenshot4.png" width="130" /> | <img src="resources/ios/screenshots/ipad/screenshot5.png" width="130" /> |

</details>

### Play Store

Flashback provides race results and statistics from every race in the history of Formula 1 and the latest Formula 1 news from around the web!

Flashback features include:
- Race results up to the latest race
- Qualifying results up to the latest race
- Sprint race results for 2021 and 2022 season
- Sprint shootout and sprint race results up to the latest race
- Driver information
- Team championship points for a race
- Driver championship standings for a season
- Team championship standings for a season
- Configurable RSS feed for Formula 1 updates
- Driver and Teams season overview

We also have an RSS hub where you can configure RSS feeds for Formula 1 news!

We plan to keep adding new features and statistics, if you have any suggestions that you would like to see please submit a suggestion from the settings in the app!

When the season is underway, the calendar for the current year might be subject to change as races are cancelled or rescheduled. As such, data may fail to appear in the app after a race has completed or the upcoming calendar event may not be accurate. We will endeavour to get this resolved as soon as possible!

Data is provided by the Flashback team. Historical data for 2023 and before was provided in part by the Ergast API, thanks to them for making this app possible! This service has been retired and is no longer available

Flashback includes quick links for Formula 1 RSS feeds from autosport.com, crash.net, motorsport.com, pitpass.com, f1-fansite.com, bbc.co.uk, theguardian.com, wtf1.com, grandprix247.com, f1i.com, and f1technical.net, but is not affiliated with them in any way

Flashback is also not affiliated in any way with any of the Formula One group of companies: FORMULA 1, FIA FORMULA ONE and related trademarks of Formula One Licensing BV

Contact Email: thementalgoose@gmail.com

### Project setup notes

```bash
# iOS
cd iosApp/
pod install
# To Release
fastlane build_upload_testflight
```
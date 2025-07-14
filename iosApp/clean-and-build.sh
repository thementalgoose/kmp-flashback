#clean and build
echo "CLEAN AND BUILD"
echo "================================================================================"
xcodebuild -workspace ./iosApp.xcworkspace/ -scheme "Production Release" -sdk iphonesimulator18.5 clean # analyze
xcodebuild -workspace ./iosApp.xcworkspace/ -scheme "Production Release" -destination generic/platform=iOS build

#archive
echo "ARCHIVE"
echo "================================================================================"
xcodebuild -workspace ./iosApp.xcworkspace/ -scheme "Production Release" -sdk iphoneos -configuration "Production Release" archive -archivePath $PWD/build/iosApp.xcarchive

# create build ipa
echo "CREATE IPA"
echo "================================================================================"
xcodebuild -exportArchive -archivePath $PWD/build/iosApp.xcarchive -exportOptionsPlist exportOptions.plist -exportPath $PWD/build

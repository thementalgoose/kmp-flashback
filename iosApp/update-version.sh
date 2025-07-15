#!/bin/bash

version_name=$1
version_code=$2

echo "Updating version name with '$version_name' and version code with '$version_code'"

sed -i -e "s/\<string\>1\<\/string\>/\<string\>$version_code\<\/string\>/g" iosApp/Info.plist
sed -i -e "s/\<string\>1.0.0\<\/string\>/\<string\>$version_name\<\/string\>/g" iosApp/Info.plist

#sed -i -e "s/\<string\>CURRENT_PROJECT_VERSION = 1;\<\/string\>/\<string\>CURRENT_PROJECT_VERSION = $version_code;\<\/string\>/g" iosApp.xcodeproj/project.pbxproj
#sed -i -e "s/\<string\>MARKETING_VERSION = 1.0.0;\<\/string\>/\<string\>MARKETING_VERSION = $version_name;\<\/string\>/g" iosApp.xcodeproj/project.pbxproj
# HealthVision


First take clone of the repository.


Second Download Java 8 and Andriod SDK for mac and windows

Third after android sdk is install add the sdk path to environment variable

Fourth Install Genymotion for andriod device simulation and X-Code for running in ios.

For Mac add the following .bash_profile 

  `export ANDROID_HOME= "/Users/vipul.pant/Library/Android/sdk"`
  `export ANDROID_TOOLS= "/Users/vipul.pant/Library/Android/sdk/tools"`
  `export ANDROID_PLATFORM_TOOLS= "/Users/vipul.pant/Library/Android/sdk/platform-tools"`
   
For Windows add these environment variable.


Now install the dependencies by running 

npm install after this run npm start 

**Note that you need to change NPM START Script to node node_modules/react-native/local-cli/cli.js start to node_modules/react-native/scripts/packager.sh**

Run 

react-native run-android for andriod but before launch your android emulator in Genymotion

react-native run-ion to run in ios device , though this application does not support ios.

I would suggest to look at https://facebook.github.io/react-native/docs/getting-started.html

**https://health-vision.herokuapp.com/ For Api Access.**

Thanks.
   
   
   
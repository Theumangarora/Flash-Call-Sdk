# Flash-Call-Sdk

Seamless Android phone number verification via missed call 📞


![flash-call-sdk](https://github.com/user-attachments/assets/00c1eccc-1b0a-4c16-9340-02c6afc475d5)




✨ Features

✅ Automatic phone number verification — no OTP entry
✅ Uses missed (flash) calls — free for the user
✅ Minimal permissions — no SMS access required
✅ Compatible with Android 6.0 (API 23) and above
✅ Lightweight & easy-to-integrate Android SDK
✅ Distributed via JitPack for effortless integration


Installation

Step 1 — Add JitPack to your root build.gradle:

allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

Step 2 — Add the SDK dependency:
dependencies {
    implementation 'com.github.your-username:flash-call-sdk:1.0.0'
}

Step 3 -Initialize the SDK

FlashSDK.getInstance().initialize(
    context = this,
    baseUrl = "https://your.api.url/",
    token = "your_api_token",
    appId = "your_app_id",
    callback = object : FlashCallback {
        override fun onFailure(error: String) {
            Log.e("FlashSDK", "Failed: $error")
        }
    }


🌟 Why Flash Call?

Many users struggle with delayed or undelivered SMS OTPs, especially in regions with poor network coverage or where SMS is blocked.
Flash calls offer a faster, cost-effective, and reliable alternative.


🛡️ Permissions

The SDK requires the following permissions:
	•	android.permission.READ_PHONE_STATE
	•	android.permission.READ_CALL_LOG (depending on implementation)

For Android 10+ (API 29), the SDK works with limited access due to scoped storage changes.

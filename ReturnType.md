# Introduction #

It looks increasingly likely that the API will have to return a custom object as the translation result. This will lose the simplicity of returning a String[.md](.md) but provided added functionality as detailed below.

# Reasons for #

  * [Issue 75](https://code.google.com/p/google-api-translate-java/issues/detail?id=75) - Returning the detected language as part of using AUTO\_DETECT
  * [Issue 76](https://code.google.com/p/google-api-translate-java/issues/detail?id=76) - Translating multiple Strings from a single Language to multiple Languages
# Black Twitter
By Suhas Maskar
## Overview

This is a test development app for reading your tweets and posting new tweets.

Time spent: 12 hours spent in total

 * Authenticating with any OAuth 1.0a or OAuth 2 API
 * Sending requests for and parsing JSON API data using a defined client
 * Persisting data to a local SQLite store through an ORM layer
 * Displaying and caching remote image data into views

The following libraries are used to make this possible:

 * [scribe-java](https://github.com/fernandezpablo85/scribe-java) - Simple OAuth library for handling the authentication flow.
 * [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
 * [codepath-oauth](https://github.com/thecodepath/android-oauth-handler) - Custom-built library for managing OAuth authentication and signing of requests
 * [UniversalImageLoader](https://github.com/nostra13/Android-Universal-Image-Loader) - Used for async image loading and caching them in memory and on disk.
 * [ActiveAndroid](https://github.com/pardom/ActiveAndroid) - Simple ORM for persisting a local SQLite database on the Android device

## Completed user stories:

 * [x]  User can sign in to Twitter using OAuth login
 * [x]  User can view the tweets from their home timeline
 * [x]  User is able to see the username, name, body and timestamp for each tweet
 * [x]  User is displayed the relative timestamp for a tweet "8m", "7h"
 * [x]  User can view more tweets as they scroll with infinite pagination
 * [x]  User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
 * [x]  Links in tweets are clickable and will launch the web browser
 * [x]  User can compose a new tweet.
 * [x]  User can click a “Compose” icon in the Action Bar on the top right
 * [x]  User can then enter a new tweet and post this to twitter
 * [x]  User can see a counter with total number of characters left for tweet
 * [x]  User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh) -partly broken :( 
 * [x]  black theme

Installation: 
Run the installation .apk file and it will prompt to install. After installation just run the "Google Image Search" from your Applications.

Walkthrough of all user stories:

![Video Walkthrough](BlackTwitter.gif)


# LoggerDude

Effortless remote logging in Android - saving baby kittens one-log-at-a-time!

LoggerDude provides a safe way to track events in your app's lifecycle and view them remotely. 
It requires minimal setup and works out of the box with a free open-source web portal. 
For greater control, devs can specify an API endpoint and reformat outgoing logs as needed.

This tool makes it easy to add __production logging__ without exposing your app data :)

1. Simply call `LoggerDude.initialize(context, url)` with your startup logic
2. Store logs anywhere with `LoggerDude.log("My first log!")`
3. Post logs to the url endpoint any time with `LoggerDude.dispatch()`

## How it works
Under the hood, LoggerDude stores data locally while your app is in use. 
The logs form a payload which gets dispatched to the provided API. 
Data stored is only visible to your application. Nothing ever gets written to the console! 

### Tools
[Room](https://developer.android.com/topic/libraries/architecture/room) for wrapping local storage  
[Ktor](https://ktor.io/) as a client for network communication

Useful for tracking/debugging:
- Network requests
- Location updates
- Application data
- IoT comms events
- Crash reports

### Future Releases

1.1 - Adds support for categorizing logs

2.0 - Adds automatic server synchronization

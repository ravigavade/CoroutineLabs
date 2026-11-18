# CoroutineLabs

An Android app (Kotlin + Jetpack Compose) that loads the current weather for 10 cities and compares plain blocking code with Kotlin coroutines. Each stage changes how the loading works and measures the result with a simple timer, so the differences are real numbers and not just claims.

## What this project shows

- Why blocking network calls break on Android's main thread
- How coroutines (`suspend`, `viewModelScope`, dispatchers) fix that
- How parallel loading with `async` / `awaitAll` changes the load time
- How to handle errors, timeouts, retries and cancellation in coroutines

## Setup

- Weather data: [Open-Meteo](https://open-meteo.com/) (free, no API key)
- Test device: Pixel 9 Pro emulator, API 37
- Measuring: a small `logTime` helper prints how long a block of code took to Logcat (tag `CoroutineLabs`)
- Numbers are averages of several runs on the emulator, so they vary a little from run to run

## Results

| Stage | Approach | Time for 10 cities | Notes |
|---|---|---|---|
| 1 | Blocking calls, one after another, in a plain `Thread` | ~385 ms (avg of 5 runs) | Crashes on the main thread |
| 2 | Coroutines, sequential | coming soon | |
| 3 | Coroutines, parallel (`async`) | coming soon | |
| 4 | Error handling | coming soon | |
| 5 | Timeout and retry | coming soon | |
| 6 | Cancellation | coming soon | |

## Stages

### Stage 0: Setup
Compose project, `INTERNET` permission, and the `logTime` helper. Tested it with a known 500 ms delay and it printed 501 ms.

### Stage 1: The naive loader
Loads each city with a blocking `URL(...).readText()` call in a `for` loop.
- On the main thread, Android throws `NetworkOnMainThreadException`, because a blocking call would freeze the screen.
- Moving the loop into a plain `Thread` fixes the crash, but the cities still load one after another.
- An error inside that thread (a failed connection) crashed the whole app, because nothing catches it.
- Baseline: about 385 ms for 10 cities.

## Project structure

- `Cities.kt`: the list of cities with their coordinates
- `WeatherApi.kt`: fetches the temperature for one city
- `Timing.kt`: the `logTime` helper
- `MainActivity.kt`: runs the loader
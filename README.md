# Advanced State in Jetpack Compose Codelab

This folder contains the source code for the
[Advanced State in Jetpack Compose Codelab](https://developer.android.com/codelabs/jetpack-compose-advanced-state-side-effects)
codelab.

The project is built in multiple git branches:
* `main` – the starter code for this project, you will make changes to this to complete the codelab
* `end` – contains the solution to this codelab

## [Optional] Google Maps SDK setup

Seeing the city on the MapView is not necessary to complete the codelab. However, if you want
to get the MapView to render on the screen, you need to get an API key as
the [documentation says](https://developers.google.com/maps/documentation/android-sdk/get-api-key),
and include it in the `local.properties` file as follows:

```
MAPS_API_KEY={insert_your_api_key_here}
```

When restricting the Key to Android apps, use `androidx.compose.samples.crane` as package name, and
`A0:BD:B3:B6:F0:C4:BE:90:C6:9D:5F:4C:1D:F0:90:80:7F:D7:FE:1F` as SHA-1 certificate fingerprint.

## References

* [State and Jetpack Compose](http://developer.android.com/jetpack/compose/state)
* [Side-effects in Compose](http://developer.android.com/jetpack/compose/side-effects)
* [UI State](http://developer.android.com/topic/architecture/ui-layer/stateholders#elements-ui)
* [UI State production](http://developer.android.com/topic/architecture/ui-layer/state-production)
* [Output types in state production pipelines](http://developer.android.com/topic/architecture/ui-layer/state-production#output-types)
* [Consuming flows safely in Jetpack Compose](http://medium.com/androiddevelopers/consuming-flows-safely-in-jetpack-compose-cde014d0d5a3)
* [Lifecycle of Composable](http://developer.android.com/jetpack/compose/lifecycle)
* [Thinking in Compose](http://developer.android.com/jetpack/compose/mental-model#any-order)
* [Maps for Compose](http://developers.google.com/maps/documentation/android-sdk/maps-compose)
* [When should I use derivedStateOf?](http://medium.com/androiddevelopers/jetpack-compose-when-should-i-use-derivedstateof-63ce7954c11b)
* [Architecting your Compose UI](http://developer.android.com/jetpack/compose/architecture)
* [Compose and other-libraries](http://developer.android.com/jetpack/compose/libraries)

## License
```
Copyright 2021 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

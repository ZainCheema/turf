# "Turf" (Work In Progress)


![Demo](https://user-images.githubusercontent.com/8540348/123616518-47ba7880-d7fe-11eb-82e0-a788de38cdf2.gif)

"Turf" is an interactive game where every user interacts on the same 10x10 grid of boxes. Users can change the colour of these boxes, the goal being to try and create your own picture within the grid (if you can!)

The purpose of me making this was to gain an understanding of what is expected from an Android Engineer in 2021.

The app currently has the following:

. Written in Kotlin, uses MVVM with Repository architecture</br>
. UI is state-driven using LiveData and Jetpack Compose</br>
. Retrofit, Moshi and RxJava used for Network Querying and Streaming</br>
. Dependency Injection using Dagger Hilt</br>

I wrote a RESTFul Web Service for the app, which was made using Python, and stores data via a MongoDB cluster.</br> Source code for that can be found in the root directory of this project. The service is hosted by Google Cloud Platform

What I am working on within the app can be seen by clicking on the "Projects" tab on the repository.

Currently working on:

. Significant UI and UX polishing</br>
. Unit Tests with JUnit and Espresso</br>
. Network error handling


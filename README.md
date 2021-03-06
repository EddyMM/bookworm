[![CircleCI](https://circleci.com/gh/EddyMM/bookworm.svg?style=svg)](https://circleci.com/gh/EddyMM/bookworm)

# Bookworm

An Android Application to show details on the best-selling books based on the New York Times ratings.

<img src="https://www.androidpolice.com/wp-content/uploads/2017/05/nexus2cee_ic_launcher_play_store_new-1.png" alt="google_play_icon" width=40 height=40 /> Get it on Google Play using this [link](https://play.google.com/store/apps/details?id=com.eddy.bookworm)


<img src="github_assets/imgs/intro.jpg" alt="intro" width=250 height=450 /> <img src="github_assets/imgs/categories-screen.jpg" alt="categories" width=250 height=450 /> <img src="github_assets/imgs/book-list.jpg" alt="book list" width=250 height=450 /> <img src="github_assets/imgs/book-detail.jpg" alt="book detail" width=250 height=450 />


## Getting Started

To run this project, do the following:

1. Clone the project
2. Open the project from `Android Studio`
3. Get an API KEY for books api from [here](https://developer.nytimes.com/docs/books-product/1/overview)
4. Ensure you have installed NDK, CMake and LLDB as shown in the [developer page](https://developer.android.com/studio/projects/add-native-code)
5. Add a folder named 'native' in the `data/src/main`
6. Create a C++ file named `native-lib.cpp` inside the `native` folder
7. Add the following C++ code to it. Replace the `<API_KEY>` with a base64 encoded version of the API key you obtained
```
#include <jni.h>

extern "C" {

    JNIEXPORT jstring JNICALL
    Java_com_eddy_data_rest_BooksApi_getAPIKey(JNIEnv *env, jclass type) {
        return env-> NewStringUTF("<API_KEY>");
    }
}
```


### Prerequisites

- Android Studio IDE
- Gradle


### Installing

- Clone the project

```
git clone https://github.com/EddyMM/bookworm.git
```

- Open project using `Android Studio`
- Sync with gradle files to get the necessary dependencies
- Run the project using emulator or connected Android Device


## Built With

- Android SDK
- Gradle - Dependency Management
- Retrofit as a REST client library
- Picasso for image loading and caching 
- FirebaseAuth - User Authentication
- Firebase Realtime Database - Data persistence


## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Push your branch (git push origin my-new-feature)
5. Create a new Pull Request


## Authors

* **Eddy Mwenda Mwiti**  - [EddyMM](https://github.com/EddyMM)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

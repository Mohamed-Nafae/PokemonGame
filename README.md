# Pokemon Game App - `com.benmeddour.pokemongame`

**Author:** Benmeddour Mohamed Nafae


## Package: `com.benmeddour.pokemongame`

This package contains the Android app for the Pokemon Game.

## Class: `MapsActivity`

### Overview

The `MapsActivity` class is the main activity for the Pokemon Game. It provides a map-based user interface where players can interact with the game world, capture Pokemon, and engage in various in-game activities.

### Constructor

- `onCreate(savedInstanceState: Bundle)`: The `onCreate` method initializes the activity, requests location permissions, and sets up the map. It also loads Pokemon data and starts location updates.

### Functions

- `CheckPermissions()`: Checks if the app has location permissions and requests them if not granted. If permissions are granted, it calls `getUserLocation()`.

- `onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)`: Handles the result of the permission request and proceeds to get the user's location.

- `getUserLocation()`: Retrieves the user's location and sets up location updates. It also initializes a background thread for game logic and rendering.

- `onMapReady(googleMap: GoogleMap)`: Callback when the map is ready to be used. It initializes the map and adds markers for the user's location and Pokemon.

- `MyLocationListener`: A custom `LocationListener` class to track the user's location and update `mylocation`.

- `MyThread`: A custom thread class to handle game logic, marker rendering, and Pokemon capture events.

- `loadPokemon()`: Initializes a list of Pokemon with their attributes, including name, description, image, power, and location.

### Gameplay

1. Launch the app on your Android device.
2. Grant location permissions when prompted.
3. The map displays your location and nearby Pokemon markers.
4. Walk around to encounter Pokemon.
5. If you get close to a Pokemon marker, you can tap it to capture the Pokemon.
6. Capturing a Pokemon increases your power.

### Dependencies

The app relies on Google Maps APIs for map functionality. Additional assets, such as Pokemon images, are not included in this documentation but should be part of your Android project.

### Contributing

If you want to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and test them thoroughly.
4. Create a pull request with a detailed description of your changes.


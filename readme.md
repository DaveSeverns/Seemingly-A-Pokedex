# Seemingly a PokeDex
This application appears to be a PokéDex. But in a much more real sense it is a very specific way to 
view all original 151 Pokémon (cause that is all there is) in ultra fun List/Detail format. Use 
`Seemingly a PokéDex` to view a list of all the Pokémon and learn their names, ID numbers, and 
see a picture of them. Want to learn more? Just click on the Pokémon's card you want to learn more about 
and be taken details page to learn it's base stats, average size, and even its learnable Move-set!

## Dependencies and resources
This app would not serve much function without the awesome and free public Pokémon API hosted at
[https://graphql-pokeapi.vercel.app/api/graphql](https://graphql-pokeapi.vercel.app/api/graphql) 
which provides a GraphQL interface from which I used to source the data displayed in the application.

In order to easily parse the GraphQL data and communicate with the server I made use of the 
[Apollo](https://www.apollographql.com/docs/kotlin/) library. It is pretty easy to set up and luckily
the schema file needed was also available from `pokeapi`'s server.

## App design and overview
It is my intention to display the basic best practices for developing a modern Android application.
The use of patterns such as MVVM and Dependency injection and inversion should be evident. I chose
to lean on the guidelines of "Clean architecture" as I believe clear lines of separation and abstaction
between software layers are what will allow the app be best maintained while allowing for the possibility
of growth and scaling. The use of dependency on abstractions over implementations will allow future changes
that may occur in the codebase to be isolated and with the best intentions allow implementation details
such as `Apollo` to be "hot swappable" should a better solution become available or if the server
moves away from the GraphQL API.

## Running the project
The app should be able to run "out of the box." There are no API keys needed, as long as your local 
environment is set up with the correct AGP and JDK versions you should be able to run the app once you
have pulled down the code and synced the dependencies.

This project does require Java 17 to be set as the "Gradle JDK" in the project configuration.

`Android Studio > Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK `




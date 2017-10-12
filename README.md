# Minecraft Server Control

Minecraft Server Control is a webserver implemented with Dropwizard in Java which allows you to start and stop your
minecraft servers using a web interface.

## Build
1. You need Java 8 on a linux system
2. Clone the repo.
3. `./gradlew jar`

## Configure
All the settings are in `mc-server-control.yml`.
This file supports the configurations provided by the Dropwizard framework and setting the server directory.

The server directory should contain one directory for every server, a `stop.sh`in the root folder and a `start.sh` in every sub folder.
You can find example scripts (using GNU screen) in `scripts`.

## Run
`java -jar build/lib/mc-server-switcher.[VERSION].jar server mc-server-switcher.yml`

# Kotlin Tesseract API

API for image reading with Tesseract.

https://grd-tesseract-api.herokuapp.com/swagger

https://github.com/tesseract-ocr/tessdoc
https://www.baeldung.com/java-ocr-tesseract
https://stackoverflow.com/questions/48446708/securing-spring-boot-api-with-api-key-and-secret
https://stackoverflow.com/questions/50467035/adding-a-header-to-all-swagger-requests
https://medium.com/@rejaluo/read-spring-boot-application-properties-in-kotlin-e8deb7682294

https://towardsdatascience.com/deploy-python-tesseract-ocr-on-heroku-bbcc39391a8d ?

## TODO

- Heroku
- Move some app properties to .env (apiKeys, tempDir, maxFileSizeBytes?, installFullPath), implement
- get API to read aptFile, not app.propperties to find installedLanguages ? abd idea 
- remove src etc. from heroku git, only apt, proc, jar needed

## Usage (Windows)

Running in IntelliJ:
1. Download dependencies with Maven
2. Bring database online
3. Run Main 
4. Go to one of the urls below or run tests
   - [localhost:8080/tesseract/...](localhost:8080/tesseract/)
5. See the link below for more details on endpoints:
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Compile an executable Über/fat JAR:
1. $ `mvn clean package`
2. Validate JAR(here called "api-0.0.1-SNAPSHOT.jar"):
    - $ `jar tf ./target/api-0.0.1-SNAPSHOT.jar`
3. Execute the JAR(here called "api-0.0.1-SNAPSHOT.jar"):
    - $ `java -jar ./target/api-0.0.1-SNAPSHOT.jar`
4. Go to endpoints listed in the IntelliJ section above

Run using Docker-Compose:
1. Install [Docker](https://www.docker.com)
2. Package JAR file
   - $ `mvn clean package` 
3. Navigate to the root folder where the [docker-compose](./docker-compose.yml) file is
4. Build the Compose file:
   - $ `docker-compose build`
5. Run the Compose file:
   - $ `docker-compose up`
6. Go to endpoints listed in the IntelliJ section above

## Heroku Deploy (CLI)

An executable JAR can be sent directly to Heroku without going through Git.
1. Set up your ProcFile
1. Build an executable JAR file, see [Usage (Windows)](#usage-(windows))
1. Install Heroku if not already installed
1. Install Heroku plugin for deploy:
   - $ `heroku plugins:install java`
1. (Optional) Create new project
   - $ `heroku create --no-remote`
1. Deploy JAR
   - $ `heroku deploy:jar <jar-name>.jar --app <app-name>`

Tesseract needs buildpacks and extra modules:
- https://github.com/heroku/heroku-buildpack-apt
- https://github.com/oswellchan/heroku-buildpack-tesseract
- heroku/jvm

For usage with Aptfile, see this guide: https://medium.com/analytics-vidhya/deploying-a-streamlit-and-opencv-based-web-application-to-heroku-456691d28c41
 
Short version is to commit files (Aptfile, Procfile, .jar) to Heroku Git:
1. Add Heroku Git remote:
   - $ `heroku git:remote -a <your app name>`
2. Add your files ("." will add all):
   - $ `git add .`
3. Commit:
   - $ `git commit -am "Added Heroku files"`
4. Push to main-branch:
   - $ `git push heroku main`
5. Assuming your process is called "web" in the Procfile, start the app:
   - $ `heroku ps:scale web=1`

Running bash inside Heroku might be useful for debug.
- `$ heroku run bash`
Find Tessdata:
- `$ find -iname tessdata`
Watch tesseract:
- `$ which tesseract`
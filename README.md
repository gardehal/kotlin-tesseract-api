# Kotlin Tesseract API

API for image reading with Tesseract.

https://grd-tesseract-api.herokuapp.com/swagger

## Improve reading result

- Remove static and irrelevant data, including cut off words or sentences, symbols and orther artifacts like smidgeons, anything that is not the text you want to capture. Even seemingly irrelevant parts of an image, lime the vertical edge of a piece of paper, may be interperated as text, eg. "|" for the the page edge. Text close to the edge of the image may also be ignored.
  - Here is an example of a bad screengrab or potential image of text. Notice the cut off line at the top, the cut of word before the colon, the lack of space on the left, the marking of "simple" as well as the marker interfering, and the arrow on the right: ![example-bad-framing](./docs/example-bad-text-frame.png)
  - Example of a good framing. Notice that the picture contains all the words and letters we want to scan, there is a devent frame around the text, and no blocking of any words: ![example-good-framing](./docs/example-good-text-frame.png)
- Set the correct language, Tesseract will use language syntax and dictionaries from the language you select, often resulting in more correct scans.
- Be aware that Tesseract can misinterprate "1" (one), "I" (capital letter i), and "l" (lower case letter l) as each other.
- Have uniform lighting and perspective for the text across the images. Taking a pictures where one part of the bookpage is in focus, but other parts are blurry or in a darker light will lead to inaccurate results.
- Know the Tesseract page segmentation modes. This will be useful for parsing differently formated (see [enum IDs](C:\Users\Alex5\Documents\GitHub\kotlin-tesseract-api\src\main\kotlin\com\tesseract\api\model) and [tricks for improving results under useful links](#useful-links)). Most of the time, 1, 3, or 6 will be enough.
- Often it will be useful to remove colour from the picture, as it may reduce the filesize and allow you to use the saved bytes on making the iamge larger.
- TODO various iamge manipulation like using editing software to sharpen image contrasts etc.

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

https://devcenter.heroku.com/articles/heroku-cli-commands

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

**NOTE**: Tesseract doesn't seem to be working well with the heroku-20 (Ubuntu 20.04) stack. 
Set heroku-18 stack:
- `$ heroku stack` (optional)
- `$ heroku stack:set heroku-18`

Tesseract needs buildpacks and extra modules:
- https://github.com/heroku/heroku-buildpack-apt
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

**NOTE**: To update the environment, a build though Herokus Git must be made. This will also update the app if, and only if, an updated .jar file was added in the commit.
Otherwise it is possible to update the app though deploy:jar (?)

Restart dynos (restart app):
- `$ heroku ps:restart`
Running bash inside Heroku might be useful for debug.
- `$ heroku run bash`
Find Tessdata:
- `$ find -iname tessdata`
Watch tesseract:
- `$ which tesseract`

## Useful links

- Some tricks for improving results: https://tesseract-ocr.github.io/tessdoc/ImproveQuality.html
- Tesseract documentation: https://github.com/tesseract-ocr/tessdoc
- Tesseract usage with Java: https://www.baeldung.com/java-ocr-tesseract
- Spring API keys: https://stackoverflow.com/questions/48446708/securing-spring-boot-api-with-api-key-and-secret
- Swagger header: https://stackoverflow.com/questions/50467035/adding-a-header-to-all-swagger-requests
- Better usage of app properties: https://medium.com/@rejaluo/read-spring-boot-application-properties-in-kotlin-e8deb7682294
- App properties simpler file annotation: https://stackoverflow.com/questions/45953118/kotlin-spring-boot-configurationproperties
- Tesseract on Heroku Aptfile: https://towardsdatascience.com/deploy-python-tesseract-ocr-on-heroku-bbcc39391a8d
- Alternative usage of Tesseract (may not be supported by tess4j dependency, but works with CMD `$ tesseract -v`): https://github.com/pathwaysmedical/heroku-buildpack-tesseract
- Fix for using Tesseract in Heroku, stack to heroku-18: https://stackoverflow.com/questions/66087588/tesseract-error-while-loading-shared-libraries-libarchive-so-13-python
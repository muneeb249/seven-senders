How to execute the application
=========
In order to execute the applicaton, from the base directoy of the project
execute 

        docker-compose up

Solution
=========
   This service uses the following endpoints
                https://xkcd.com/info.0.json
                https://xkcd.com/<num>/info.0.json
                http://feeds.feedburner.com/PoorlyDrawnLines
   These values are added in the resources application.yaml file
   In order to change the default config values use these environment variables
            <br>WEB_COMIC_URL (Default Value -> https://xkcd.com)
            <br>WEBCOMIC_PREFIX (Default Value -> info.0.json)
            <br>WEBCOMIC_MAX_RECORDS (Default Value -> 10)
            <br>PDL_URL (Default Value -> http://feeds.feedburner.com/PoorlyDrawnLines)
            <br>PDL_MAX_RECORDS (Default Value -> 10)
            
    <br>
    The application first of all calls  https://xkcd.com/info.0.json url to get the latest comic. Below is the response 
    from this endpoint
    <code>
    {
        "month": "4",
        "num": 2448,
        "link": "",
        "year": "2021",
        "news": "",
        "safe_title": "Eradication",
        "transcript": "",
        "alt": "When you get to hell, tell smallpox we say hello.",
        "img": "https://imgs.xkcd.com/comics/eradication.png",
        "title": "Eradication",
        "day": "9"
    }
    </code>
    
    "num" is parsed from the response and the rest of the data is called by decreasing the num value to the point
    it fullfills the maxRecords needed from this endpoint https://xkcd.com/<num>/info.0.json
    
    The second endpoint is an RSS FEED http://feeds.feedburner.com/PoorlyDrawnLines
    In order to parse this, I used Rome 3rd party jar to parse the response. 
    The basic information is available in the SyndEntry i.e. publishing Date, uri, title, but in order to get the 
    img url. the contents of each SyndEntry is parsed to get the img url with the help of another 3rd party library called
    Jsoup.
    
    Afterwards these results are combined and sorted based on the descending order of publishing date
    
    The endpoint exposed is (GET) http://localhost:8080
    
future Improvement 
=========    
The application calls the endpoints on each HTTP Call.
For future improvements, if said so I'll implement a cache based system based on the id of the url to verify
 that these responses are already saved in the HashMap


Techstack
=========

 The Routes of the application is declared in the class ComicsRouter by using the Reactive Functional Programming approach
* Java 11
* Apache Maven v3.6.xx
* Spring Framework (2.4.4)
* Reactive WebFlux
* Rome
* Jsoup

Get started
===========

#### Prequisites
* JDK 11
* Apache Maven 3.5.xx
* Java IDE (Intellij IDEA recommended)
* Docker

#### Installation Build:
* use the following maven commands for building the project:

-> To execute the project execute the following command

    mvn clean install spring-boot:run

-> Command to build project and run Tests:
```
mvn clean install
```
-> Command to build project and skip skipTests:
```
mvn clean install -DskipTests
```
**Note:** All paths in this instruction are relative to project root folder.
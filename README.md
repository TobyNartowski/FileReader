# FileReader
File reader project which loads txt and xml files to database

## Tech stack
Project is using Java 11 and it's build with Maven 3.6.2. Current version is using MySQL database which could be configured in *prod-config.properties* file.

## Install
Project needs Maven 3.6.2 to be build
```
mvn clean install
```

## Usage
Generated jar is located in *target* directory.

Program accepts two parameters:

**Input** - which is a path to directory containing input files.

**Profile** - optional profile of an application, at current time there is only one profile declared - *prod*. 
```
java -jar file-reader-1.0.jar [input] [profile]
```

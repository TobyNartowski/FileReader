# FileReader
File reader project which loads txt and xml files to database

## Overview
Program is supporting multiple profiles for future expansion. Every profile could have it's own properties,
database connection or specific class implementation. Application will detect if tables doesn't exist and create them
if there is such a necessity. Tables creation is designed to remain topological dependency structure. Program is able to
accept as a parameter specific file to parse and upload or whole directory. In the second case supported files
will be detected and processed in a batch.

## Tech stack
Project is using Java 11 and it's built with Maven 3.6.2. Current version is using MySQL database which could be configured in *prod-config.properties* file.

## Install
Project needs Maven 3.6.2 to be build
```
mvn clean install
```

## Usage
Generated jar is located in *target* directory.

Program accepts two parameters:

**Input** - which is a path to specific file or directory containing input files.

**Profile** - optional profile of an application, at current time there is only one profile declared - *prod*. 
```
java -jar file-reader-1.0.jar [input] [profile]
```

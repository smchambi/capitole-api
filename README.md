# Capitole API
Application in charge of search product prices data.

![Java v11](https://img.shields.io/badge/Java-v11-orange.svg)
![technology Gradle](https://img.shields.io/badge/technology-Gradle-blue.svg)
![technology SpringBoot](https://img.shields.io/badge/technology-SpringBoot-green.svg)
![test](https://img.shields.io/badge/test-Spock-green.svg)

***
### Usage 

The next command line is going to start up the application.

```
./gradlew clean bootRun
```
However, you can see and test the resources in [Heroku](https://capitole-api.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config).

**Remember** the data stored in db is:

| BRAND_ID | START_DATE | END_DATE | PRICE_LIST | PRODUCT_ID | PRIORITY  | PRICE | CURR |
| :---: | :---: | --- | --- | --- | --- | --- | --- |
|1|2020-06-14-00.00.00| 2020-12-31-23.59.59 | 1 | 35455 | 0 | 35.50 | EUR |
|1|2020-06-14-15.00.00| 2020-06-14-18.30.00 | 2 | 35455 | 0 | 25.45 | EUR |
|1|2020-06-15-00.00.00| 2020-06-15-11.00.00 | 3 | 35455 | 0 | 30.50 | EUR |
|1|2020-06-15-16.00.00| 2020-12-31-23.59.59 | 4 | 35455 | 0 | 38.95 | EUR |


### Documentation
- [Sequence diagrams](docs/usecases/use-cases-diagrams.md)
- [Components diagram](docs/components/components-diagrams.md)
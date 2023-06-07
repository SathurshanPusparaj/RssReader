# Application Overview

This is a spring boot based application. Application polls an RSS feed for every 5 minutes and
stores the item or update the item in an in-memory datastore(H2 database). It keeps track of the 10 latest
items and stores the title, article link, description, published date. If an item updated, the
corresponding record also updated in the database.

The application exposes RESTful endpoints to retrieve the 10 newest items and provides
pagination with sorting capabilities based on a specified field.

# Getting Started
 This project uses Java 17, Spring boot 3, Maven, H2 database
 
## Configuration
Rss feed url needs to be configured in the application.properties file (rss.feed.url)

## Database Schema
Primary key is ID type Bigint ,
title varchar 255 unique,
pub_date timestamp 6, 
desc TEXT, 
link varchar 255 

Access H2 console = ipaddress:port/h2-console
JDBC URL - jdbc:h2:mem:rss-reader-datasource

## Application Layers
1. Data Access Layer(Feed Repository) - Stores and retrieves data from the database
2. Service Layer (FeedService, FeedPollService) - Retrieves the data from Rss website and inserts it into the database, as well as providing an additional layer of abstraction for adding business logic.
3. Controller Layer (FeedController) - swagger - http://{ipaddress:port}/swagger-ui/index.html

## Testing 
Unit test and Integration tests are added in the project

## Improvements
1. Add DockerCompose artifact to make a docker image
2. Add Circuit breaker to the third party api call
3. More log messages
4. Add Java Docs
5. Use advanced spring scheduler cron expression
6. Enable Spring Security
7. Asynchronous processing : use message queues to improve the responsiveness. Useful when dealing with large or slow feeds.
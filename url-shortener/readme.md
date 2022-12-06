# URL Shortener

Spring Boot based REST API that takes a URL and returns a shortened URL and uses MySQL to persist data.

# Getting Started

## Dependencies

This project depends on 
* spring-boot-starter-web (Spring boot framework)
* spring-boot-starter-data-jpa (for data persistence)
* spring-boot-starter-actuator (for API statistics)
* spring-boot-starter-thymeleaf (for model view mapping)
* javax-validation (for URL validation)
* h2 (for tests)
* spring-boot-starter-test (for tests)

**The application will be accessible on http://localhost:9090**

## API Endpoints

You can access following API endpoints at http://localhost:9090

### POST `/shortUrl`
It takes a JSON object in the following format as payload

```json
{
  "url":"<The URL to be shortened>"
}
```

#### cURL

```shell script
curl -X POST \
  http://localhost:9090/shortUrl \
  -H 'Content-Type: application/json' \
  -d '{"url":"https://test.com/test/1"}'
```

Response:

```json
{
  "shortenedUrl": "<shortened url for the originalUrl provided in the request payload>"
}
```

Please note that API works only with valid HTTP or HTTPS Urls. In case of malformed Url, it returns `400 Bad Request` error with response body containing a JSON object in the following format

```json
{
  "field":"originalUrl",
  "value":"<Malformed Url provided in the request>",
  "message":"<Exception message>"
}
```

### GET `/<shortened_text>`

This endpoint redirects to the corresponding originalUrl.

### GET `/actuator/health`

Included the spring boot actuator dependency for API metrics. You can try this endpoint for health checks.

#### cURL

```shell script
curl -X GET   http://localhost:9090/actuator/health
```
# Url Shortening Algorithm

I thought of 
1. Performing a Base62 conversion from Base10 on the id of stored originalUrl

With the Base conversion approach, even the maximum value of Long produces 10 characters which is still somewhat easy to remember. 
> There is a dependency from Google named Guava that could be used here to generate hashes. Although murmur_3_32 hash implemented in Guava was generating up to 10 characters long string, I left it for future testing and evaluation.

# Future Enhancements / Known Issues
* Implement https
* Mount volumes for MySql container to persist data outside of the container


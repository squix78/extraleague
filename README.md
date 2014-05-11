# Extraleague

[ ![Codeship Status for squix78/extraleague](https://www.codeship.io/projects/85f58d70-b430-0131-7737-3ef5356443ef/status?branch=master)](https://www.codeship.io/projects/20276)

## Prerequisites
Extraleague is using lombok to automatically add getters and setters. In order add this byte code manipulation
to the IDE of your choise please follow the instructions here: http://projectlombok.org/features/index.html

## Starting the development server

```
mvn appengine:devserver
```
and navigate to
```
http://localhost:8080/
```
## Live reload
To have your browser updated after a change of a static resource run
```
livereloadx -l -p 35729 src/main/webapp/ --proxy http://localhost:8080/
```
install the  livereload browser plugin and open your browser at
```
http://localhost:35729
```

## Database admin
If you want to look at your local datastore navigate to
```
http://localhost:8080/_ah/admin
```
after starting your development server.

## Test environment/demo
After every successful commit the latest version will be deployed to 
```
http://ncaleague-test.appspot.com/
```
If you plan to write a new client I suggest you experiment against this instance, rather than the "production" application.

## Import Player Data 
Navigate to
```
http://localhost:8080/admin
```
and import the file src/main/resources/players-example.json
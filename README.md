# Extraleague

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
``
livereloadx -l -p 35729 src/main/webapp/ --proxy http://localhost:8080/
```
install the  livereload browser plugin and open your browser at
```
http://localhost:35729
``

## Database admin
If you want to look at your local datastore navigate to
```
http://localhost:8080/_ah/admin
```
after starting your development server.
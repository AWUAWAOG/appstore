# APPSTORE APPLICATION

This is a simple online service that provides the opportunity to purchase many different programs and games.

## The application uses the following technologies and frameworks:

* Apache Maven;
* PostgreSQL database;
* Exception handling;
* Spring Data Jpa;
* Spring Boot. Rest API. DTO;
* Java Persistence API;
* Spring Security JWT.

## Application also uses additional Swagger technology.

    check out -> http://localhost:8080/swagger-ui/index.html

## As application uses PostgreSQL database technology it needs Postgres server (jdbc:postgresql://localhost:5432/appstore_db)
and has a database 'appstore_db'. To pass authentication application has admin account with:
* username = postgres
* password = root

## Database contains five tables:

* Table 'users' contains information about application users;
* Table 'applications' contains information about all available applications;
* Table 'developers' contains information about all developers;
* Table 'l_applications_users' contains all applications which single user has;
* Table 'l_applications_developers' contains all developers which are included in one application;

## There is a list of all available endpoints for USERS:

* http://localhost:8080/auth - POST method. Gives temporary jwt token;

* http://localhost:8080/user - POST method. Creates new user;
* http://localhost:8080/user - GET method. Gives list of all users;
* http://localhost:8080/user - PUT method. Updates user;
* http://localhost:8080/user/reg - POST method. Creates user, using DTO;
* http://localhost:8080/user/addApp - POST method. Adds application to single user;
* http://localhost:8080/user/{id} - GET method. Gets user by id;
* http://localhost:8080/user/rl/{role} - GET method. Gets user by role;
* http://localhost:8080/user/lg/{login} - GET method. Gets user by login;
* http://localhost:8080/user/em/{email} - GET method. Gets user by email;

* http://localhost:8080/app - POST method. Creates new application;
* http://localhost:8080/app/addDev - POST method. Adds developer to application;
* http://localhost:8080/app - PUT method. Updates application;
* http://localhost:8080/app - GET method. Gives list of all applications;
* http://localhost:8080/app/{id} - GET method. Gets application by id;
* http://localhost:8080/app/rat/{rating} - GET method. Gets application by rating;
* http://localhost:8080/app/name/{appMame} - GET method. Gets application by name;
* http://localhost:8080/app/cat/{category} - GET method. Gets application by category;

* http://localhost:8080/dev - POST method. Creates developer;
* http://localhost:8080/dev - PUT method. Updates developer;
* http://localhost:8080/dev - GET method. Gives list of all developers;
* http://localhost:8080/dev/{id} - GET method. Gets developer by id;
* http://localhost:8080/dev/fnln/{firstName}, {lastName} - GET method. Gets developer by firstname and lastname;
* http://localhost:8080/dev/bd/ {birthDate} - GET method. Gets developer by birthdate;

* http://localhost:8080/file/upload - POST method. Uploads a new file;
* http://localhost:8080/file - GET method. Gets all files;
* http://localhost:8080/file{filename} - Gets specific file;

## Endpoints for ADMINS:

* http://localhost:8080/user/{id} - DELETE method. Deletes user (changes field 'is_deleted' to TRUE);
* http://localhost:8080/app/{id} - DELETE method. Deletes application (changes field 'is_deleted' to TRUE);
* http://localhost:8080/dev/{id} - DELETE method. Deletes developer from database (changes field 'is_deleted' to TRUE);
* http://localhost:8080/file{filename} - DELETE method. Deletes specific file;
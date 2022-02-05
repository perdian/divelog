# DiveLog

[![Releases](https://img.shields.io/github/v/release/perdian/divelog)](https://github.com/perdian/divelog/releases)
[![Build](https://img.shields.io/circleci/build/github/perdian/divelog/master)](https://circleci.com/gh/perdian/divelog)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

As a hobby diver I was looking to migrate away from the classical diving paper logbook to something electronic.

Strangely I didn't find any existing application that really met my expecations so I decided to implement something from scratch - the DiveLog is the result.

## Build

DiveLog is a [Spring Boot application](https://spring.io/guides/gs/spring-boot/) application and can be built and executed accordingly:

    $ git clone https://github.com/perdian/divelog.git
    $ cd divelog
    $ mvn clean package

This creates the application JAR file into `target/divelog.war`

## Run

### JAR file

The JAR file already contains everything needed to start the application with its default settings:

    $ mvn clean package
    $ java -jar target/divelog.jar

The application will now start and can be accessed:

    http://localhost:8080/divelog/

An embedded H2 database file will stored at `/var/divelog/database`.
Both the database location as well as the database type can be configured using environment variables (see the **Configuration** section for details).

### Docker container

#### Fetch from DockerHub

An alternative do running the JAR file directly is to create a Docker container that wraps the complete application.

Releases are automatically pushed to DockerHub packages so all you need to do is to run the Docker image.

    $ docker run -p 8080:8080 perdian/divelog:1

Replace the `1` version with the latest release which can be found at https://github.com/perdian/divelog/releases.

The application will be available on the machine on which you're executing the container at:

    http://localhost:8080/divelog/

An embedded H2 database file will stored as volume for the created container. It can be configured using environment variables (see the **Configuration** section for details).

To persist the database you have to mount the directory `/var/divelog/database/` from the container to somewhere on your host machine:

    $ docker run -v /path/to/your/host/directory:/var/divelog/database -p 8080:8080 perdian/divelog

### Build from sources

If you want to build the Docker image yourself from the sources it can be done like this:

    $ mvn clean package
    $ docker build -t perdian/divelog .

After the container is built you can verify the application by running it:

    $ docker run -p 8080:8080 perdian/divelog

The application will be available on the machine on which you're executing the container at:

    http://localhost:8080/divelog/

## Configuration

The complete configuration can be done using environment variables so whether you're launching the application directly from the JAR file or via a Docker container, the same set of environment variables is used.

### Database

By default an embedded H2 database is used but any database supported by Hibernate can be configured.
The JDBC drivers for H2, PostgreSQL and MySQL are already bundled within the standard Spring Boot application and Docker image.

| Environment variable | Default value | Comment |
| -------------------- | ------------- | ------- |
| `DIVELOG_DB_URL` | `jdbc:h2:/var/divelog/database/divelog` | The complete JDBC URL of the database |
| `DIVELOG_DB_DRIVER_CLASS_NAME` | `org.h2.Driver` | The JDBC driver class name. The class must be accessible on the classpath. |
| `DIVELOG_DB_USERNAME` | `sa` | The JDBC username |
| `DIVELOG_DB_PASSWORD` | | The JDBC password |
| `DIVELOG_DB_HIBERNATE_DIALECT` | `org.hibernate.dialect.H2Dialect` | The [Hibernate dialect](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#database-dialect) class (must correspond to the selected JDBC driver class) |

### Authentication

By default the application is not secured and all dives are associated to the `NULL` user.

To enable authentication, the following environment variables can be set:

### Other environment variables

| Environment variable | Default value | Comment |
| -------------------- | ------------- | ------- |
| `DIVELOG_SERVER_PORT` | `8080` | The port on which the application will listen for incoming requests |
| `DIVELOG_SERVLET_CONTEXT_PATH` | `/divelog` | The context path under which the application will be made available. The default value `/divelog` implies that when using the default port of `8080` the applications main page can be reached at `http://localhost:8080/divelog/`. |
| `DIVELOG_COOKIE_NAME` | `divelog-session` | The name of the cookie that is used to store session related data. |

## Credits

This application would not be possible without the great work of other open source projects.

A big thank you therefore goes to:

* Openlayers for the ground work in providing the world map (<https://openlayers.org/>).
* Fomantic UI for its beautiful frontend components (<https://fomantic-ui.com/>).
* JQuery for the ultimate DOM manipulation tool (<https://jquery.com/>).
* Spring Boot for the heavy lifting in the backend (<https://projects.spring.io/spring-boot/>).
* Hibernate for the SQL abstraction layer (<http://hibernate.org/>).

## License

DiveLog is licensed under the Apache Licence 2.0 (<http://www.apache.org/licenses/>).

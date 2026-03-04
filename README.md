# CRUD APPLICATION
I developed a desktop CRUD application for managing books to deepen my understanding of Java and database integration.

The application follows the MVC architecture to ensure separation of concerns and maintainable code.

I used Java for its strong ecosystem and long-term relevance to backend and API development. Maven was used for dependency management and project structure.

For the database, I chose PostgreSQL to gain experience with a production-level relational database system.

The project helped me understand JDBC/database connectivity, data integrity constraints, and clean architectural organization.

# Usage
First of all make sure [Java 21](https://www.oracle.com/br/java/technologies/downloads/), [Maven](https://github.com/apache/maven), [Docker](https://github.com/docker/compose) is installed on your computer.

`Clone` the repository, change to its directory, and then start the PostgreSQL container.
```bash
#clone the repo
git clone github.com/d4alencar/crud_desktop

#change to its directory
cd crud_desktop

#start container
docker compose up -d
```

Run `mvn clean` before compiling to avoid issues from previous builds
```bash
#clean project's maven output directory
mvn clean

#compile and execute 
mvn compile exec:java
```

## Screenshots
<img src="imgs/example1.png" alt="example1" />

<img src="imgs/example2.png" alt="add dialog" />

### next steps
- [x] add search tool
- [x] handle unexpected behaviour
- [x] improve GUI
- [ ] improve search tool

### tech stack

| Layer | Technology |
|-------|-------------|
| Language | Java (JDK 21) |
| GUI | Swing |
| Database | PostgreSQL |
| IDE | LunarVim |
| Build Tool | Maven |

# Votely — Smart Voting System (Java)

A classroom demonstration website built with Java 17+, Spring Boot, Thymeleaf, Spring Security, and an embedded H2 database.

> **Important:** This is an educational demo, not an official election service. Use fictional data only. It is not certified for real public elections.

## Requirements
- JDK 17 or newer
- Internet access on first build so Maven can download dependencies
- Eclipse with Spring Tools, IntelliJ IDEA, or VS Code with Java extensions

## Run in Eclipse
1. Extract the ZIP file.
2. Open Eclipse and choose **File → Import → Maven → Existing Maven Projects**.
3. Select the extracted `smart-voting-system` folder and finish the import.
4. Wait for Maven dependencies to download.
5. Open `src/main/java/edu/demo/voting/SmartVotingApplication.java`.
6. Right-click the file → **Run As → Java Application** (or Spring Boot App if Spring Tools is installed).
7. Open `http://localhost:8080` in your browser.

Alternatively, from a terminal in the project folder run:
```bash
./mvnw spring-boot:run
```
If no Maven Wrapper is included, install Maven and run:
```bash
mvn spring-boot:run
```

## Demo accounts
- Voter: `voter@example.com` / `VoterDemo123!`
- Admin: `admin@example.com` / `AdminDemo123!`

These accounts are configured in `SecurityConfig.java` for local demonstration only. Change them before any deployment. They are not production credentials.

## What works in this starter
- Responsive home page and sign-in page
- Spring Security login/logout and role-protected admin results page
- Demo voter dashboard
- Candidate selection and one recorded vote per signed-in account for the running application
- Receipt reference after vote submission
- Aggregate results page for admins
- H2 local database dependency and configuration

## Important implementation notes
This starter uses in-memory demo users and in-memory vote counts so it can be run with minimal setup. Although H2 is configured, voter/candidate/ballot data is **not yet persisted to database tables**. Restarting the application resets demo vote data. Do not present this starter as production-ready. To satisfy a full persistent project, the next development step is to add JPA entities/repositories for users, elections, candidates, eligibility, ballots and audit logs, then move voting into a transactional service with database uniqueness constraints and automated tests.

## Database console
The H2 console is enabled at `http://localhost:8080/h2-console` for local development. JDBC URL: `jdbc:h2:file:./data/smartvoting`; username `sa`; password blank. Do not expose this console publicly.

## Pages
- `/` home
- `/login` sign in
- `/dashboard` voter dashboard (sign-in required)
- `/admin/results` aggregate demo results (admin only)
- `/about` project information

## Troubleshooting
- **Port 8080 busy:** change `server.port=8081` in `src/main/resources/application.properties`.
- **Maven dependencies fail:** check internet access and run Maven update.
- **Login loops or 403:** use the provided demo credentials and ensure cookies are enabled.
- **Java version error:** set the project's JRE/JDK to Java 17 or newer.

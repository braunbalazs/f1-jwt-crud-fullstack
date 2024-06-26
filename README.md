# Full Stack Demo REST API With JWT

This project is to showcase my back-end and front-end skills.

## Fully functional login, basic crud and logout demo
The app lists formula one teams and provides basic CRUD operations on them
- It handles protected / unprotected routes on both the front end and the back end.
- Full login functionality with JWT tokens, custom JwtFilter on the back end.
- The list of Formula 1 teams are available without login, but creating, modifying and deleting a team requires a login.
- User input validated on both the front end and the back end

## Starting the App

### Starting the Back End
- cd into the `backend` directory
- `docker-compose up`
- run the app

### Starting the Front End
- cd into the `frontend` directory
- `npm run dev`
- go to `http://localhost:5173`

### User Login Details:
- username: `admin`
- password: `f1test2018`

## Tech Stack
### Back End
- Java 21
- Maven
- Spring Boot 3.3
- Spring Data JPA
- PostgreSQL
- Docker
- Flyway
- Spring Web
- Spring Security
- JWT

### Front End
- React 18
- React Router 6
- TypeScript
- Material UI
- React Hook Form
- Axios
- Vite
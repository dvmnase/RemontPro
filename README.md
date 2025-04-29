```markdown
# RemontPro - Backend Service

Java Spring Boot backend with REST API, JPA and Docker support.

## Technologies
- Java
- Spring Boot
- REST API
- JPA
- Docker

## Docker Commands
```bash
docker build -t backend .
docker run -p 8080:8080 backend
```

## API Endpoints

### Registration 
**POST** `http://localhost:8080/auth/signup`

```json
{
    "username": "qwerty",
    "email": "qwerty@example.com",
    "password": "sdfghj",
    "role": "ADMIN"
}
```

### Authentication (returns token)
**POST** `http://localhost:8080/auth/signin`

```json
{
    "username": "qwerty",
    "password": "sdfghj"
}
```

### Token Authentication
**GET** `http://localhost:8080/secured/user`  
**Auth Type**: Bearer Token 

###ADMIN
#### CRUD Operations with services (Require Bearer Token)
**GET**    `http://localhost:8080/secured/admin/services`  
**GET**    `http://localhost:8080/secured/admin/services/{id}`  
**POST**   `http://localhost:8080/secured/admin/services`

```json
{
    "name": "мега ремонт",
    "description": "очень крутой ремонт в стиле бахо",
    "price": 2563
}
```

**PUT**    `http://localhost:8080/secured/admin/services/{id}`  
**DELETE** `http://localhost:8080/secured/admin/services/{id}`

#### CRUD Operations with employees (Require Bearer Token)
**GET**    `http://localhost:8080/secured/admin/employees`  
**GET**    `http://localhost:8080/secured/admin/employees/{id}`  
**POST**   `http://localhost:8080/secured/admin/employees`

```json
{
  "username": "lol118",
  "email": "lo111",
  "password": "123",
  "fullName": "Иван Новичок",
  "qualification": "штукатур",
  "phoneNumber": "number"
}
```

**PUT**    `http://localhost:8080/secured/admin/employees/{id}`  
```json
 {
  "fullName": "Данечка Петров",
  "qualification": "сантехник",
  "phoneNumber": "number"
}

```
**DELETE** `http://localhost:8080/secured/admin/employees/{id}`
```


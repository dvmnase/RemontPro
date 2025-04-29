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

### ADMIN
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

#### Block/unblock clients (Require Bearer Token)
**GET** (get all users)    `http://localhost:8080/secured/admin/users`  
**GET**  (get all clients)  `http://localhost:8080/secured/admin/users/clients`  
**POST** (block client by id)  `http://localhost:8080/secured/admin/users/clients/{id}/block`
**POST** (unblock client by id)  `http://localhost:8080/secured/admin/users/clients/{id}/unblock`

#### Viewing statistics (Require Bearer Token)
**GET** (get all employees with their statistics)    `http://localhost:8080/admin/stats/employees`  
**GET**  (get employee by id with his statistics)  `http://localhost:8080/admin/stats/employees/{id}`  
```json
{
    "id": 5,
    "fullName": "Иван Новичок",
    "qualification": "штукатур",
    "phoneNumber": "+375295554483",
    "userId": 15,
    "username": "lol118",
    "email": "lo111l@example.com",
    "newOrders": 0,
    "inProgressOrders": 1,
    "completedOrders": 1,
    "cancelledOrders": 0
}
```
**GET** (get general statistics)  `http://localhost:8080/admin/stats/orders-by-status`
```json
{
    "IN_PROGRESS": 2,
    "NEW": 2,
    "CANCELLED": 1,
    "COMPLETED": 1
}

```
### USER
#### SEARCH SERVICES (Require Bearer Token)
**GET** (get all)   `http://localhost:8080/user/services`  
**GET**  (get service by id)  `http://localhost:8080/user/services/3`  
**GET**  (search service)  `http://localhost:8080/user/services/search?query=ремонт`

#### FILTER SERVICES by price (Require Bearer Token)
**GET** (get all services what price <=150)   `http://localhost:8080/user/services/filter?maxPrice=150`  

#### order management (Require Bearer Token)
**GET** (get history for client)   `http://localhost:8080/user/orders/history/{clientId}`  
**GET** (get order by id)   `http://localhost:8080/user/orders/{orderId}`  
**GET** (get employees)   `http://localhost:8080/user/orders/employees`  
**POST** (add order)   `http://localhost:8080/user/orders`  
```json
{
  "clientId": 1,
  "serviceId": 1,
  "employeeId": 1,
  "description": "Хочу заменить унитаз",
  "status": "NEW"
}
```
**POST** (delete order if status is new)   `http://localhost:8080/user/orders/{orderId}/cancel?clientId={clientId}`  

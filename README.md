# Code Challenged - Encora

Proyecto de API Restful para el manejo de productos y credenciales.

## Comenzando 🚀

Estructura del Proyecto:

```agsl
code-challenged-encora
├─ .git
├─ .gitignore
├─ microservices
│  ├─ ms-credentials-control    -> Microservicio para el manejo de credenciales
│  ├─ ms-customer-products      -> Microservicio de consulta a los otros microservicios
│  └─ ms-products               -> Microservicio para el manejo de productos
├─ utils                        -> Libreria para el control de excepciones y errores
├─ docker-compose.dev.yml       -> Despliegue en ambiente de desarrollo (mongodb, mysql)
├─ docker-compose.qa.yml        -> Despliegue en ambiente de qa (mongodb, mysql, credentials, products, customer)
├─ Makefile                     -> Archivo de automatización de tareas
└─Readme.md
```
La architectura de cada microservicio fue diseñada mediante Clean Architecture:
```agsl
├─ com.enconra.challenged.credentials
│  ├─ applications    
│  │    ├─ config
│  │    ├─ controllers
│  │    ├─ dto   
│  ├─ domain 
│  │    ├─ model    
│  │    ├─ service 
│  └─ infrastructure
│       ├─ repository
│       ├─ entity               
                   
```

## Microservicio: ms-credentials-control 📋

_ Este ms permite registrar las credenciales y el cluster, asi como generar un token JWT para la autenticación al cluster.

Curl: Registro del usuario para poder generar el token
```bash
# Registro de credenciales
curl --location 'localhost:8081/auth/sign-up' \
--header 'Content-Type: application/json' \
--data '{
	"clusterKey": "core",
    "user": "david",
	"password": "123456"
} '
```
```json
//Response:
{
    "id": 53,
    "clusterKey": "core",
    "user": "Yonatan",
    "password": "$2a$10$8JyaJINYZhKajyjEtUUz2.y8sV/qDSWbOR6rRkAjOIUtwqZae38gS"
}
```

Curl: Permite realizar el login con la finalidad de obtener el token jwt
- expiresIn: el tiempo de vida del token esta en segundos.
```bash
# login
curl --location 'localhost:8081/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "clusterKey": "core",
    "user": "david",
	"password": "123456"
} '
```
```json
//Response:
{
  "clusterKey": "core",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJjbHVzdGVyS2V5IjoiY29yZSIsInN1YiI6IllvbmF0YW4iLCJpYXQiOjE3MTQwNDEwNDAsImV4cCI6MTcxNDA0MTE2MH0.zIiMXSLGXKunpaK4ohVUZE_oDNnp6s7olsFn1PD5EV0",
  "expiresIn": 120
}
```

Curl: Permite validar el token jwt y obtener la información del usuario y el ttl del token.
- expiresIn: Te muestra los segundos que le quedan al token para expirar.
```bash
# valid token
curl --location 'localhost:8081/auth/token' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJjbHVzdGVyS2V5IjoiY29yZSIsInN1YiI6IllvbmF0YW4iLCJpYXQiOjE3MTQwNDEwNDAsImV4cCI6MTcxNDA0MTE2MH0.zIiMXSLGXKunpaK4ohVUZE_oDNnp6s7olsFn1PD5EV0' \
--header 'Cookie: JSESSIONID=241FA92836F9ECBD3AD76509CF6E2BA0'
```
```json
//Response:
{
  "clusterKey": "core",
  "user": "Yonatan",
  "password": "$2a$10$8JyaJINYZhKajyjEtUUz2.y8sV/qDSWbOR6rRkAjOIUtwqZae38gS",
  "expiresIn": 44
}
```

## Microservicio: ms-products 📋

_ Este ms permite registrar, eliminar y consultar los productos.

Curl: Registro productos
```bash
# Registro
curl --location 'localhost:8082/api/products' \
--header 'Authorization: Bearer 34534' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=241FA92836F9ECBD3AD76509CF6E2BA0' \
--data '{
    "name":"Laptop",
    "description":"Mackbook Pro",
    "document_number":"45645-45645",
    "state": true
}'
```
```json
//Response:
{
  "id": "662a32f00b62c676f5946d07",
  "name": "Laptop",
  "description": "Mackbook Pro",
  "state": false,
  "document_number": "45645-45645"
}
```

Curl: Consulta productos
```bash
# Consulta
curl --location 'localhost:8082/api/products/662a32f00b62c676f5946d07' \
--header 'Authorization: Bearer 34534'
```
```json
//Response:
{
  "id": "662a32f00b62c676f5946d07",
  "name": "Laptop",
  "description": "Mackbook Pro",
  "state": false,
  "document_number": "45645-45645"
}
```

Curl: Delete productos
```bash
# Consulta
curl --location --request DELETE 'localhost:8082/api/products/662a32f00b62c676f5946d07' \
--header 'Authorization: Bearer 34534' 
```
```json
//Response 200 ok
```


## Microservicio: ms-customer-products 📋

_ Este ms permite consultar los productos registrados en el ms-products y validar el token desde el ms-credentials-control.

Curl: Obtener el token del usuario segun sus credenciales
```bash
# get token
curl --location 'localhost:8080/api/credential/token' \
--header 'cluster: core' \
--header 'user: david' \
--header 'password: 123456' 
```
```json
//Response:
{
  "clusterKey": "core",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJjbHVzdGVyS2V5IjoiY29yZSIsInN1YiI6ImRhdmlkIiwiaWF0IjoxNzE0MDQxOTIwLCJleHAiOjE3MTQwNDIwNDB9.NCFcqp4Ju3L49m2QBsT6KTDKQ-4XUNZl1u1ZNOLavhs",
  "expiresIn": 120
}
```

Curl: registrar un producto en la bd de ms-products
```bash
# get token
curl --location 'localhost:8080/api/product' \
--header 'token: eyJhbGciOiJIUzI1NiJ9.eyJjbHVzdGVyS2V5IjoiY29yZSIsInN1YiI6ImRhdmlkIiwiaWF0IjoxNzE0MDM3NzIzLCJleHAiOjE3MTQwMzc4NDN9.ewsaoXtaTEYaG1mbvvwnBYs2uNVuwzJrAIUVnWqVjWA' \
--header 'Content-Type: application/json' \
--data '{
    "name":"libro",
    "description":"consa descripcion",
    "document_number":"123345",
    "state": false
}'
```
```json
//Response:
{
  "id": "662a1fe9d1d7e15ee5330e78",
  "name": "libro",
  "description": "consa descripcion",
  "state": false,
  "document_number": "123345"
}
```

Curl: Buscar un producto en la bd de ms-products
```bash
# get token
curl --location 'localhost:8080/api/product/662a1fe9d1d7e15ee5330e78' \
--header 'token: eyJhbGciOiJIUzI1NiJ9.eyJjbHVzdGVyS2V5IjoiY29yZSIsInN1YiI6ImRhdmlkIiwiaWF0IjoxNzE0MDM3NzIzLCJleHAiOjE3MTQwMzc4NDN9.ewsaoXtaTEYaG1mbvvwnBYs2uNVuwzJrAIUVnWqVjWA'
```
```json
//Response:
{
  "id": "662a1fe9d1d7e15ee5330e78",
  "name": "libro",
  "description": "consa descripcion",
  "state": false,
  "document_number": "123345"
}
```

Curl: Eliminar un producto en la bd de ms-products
```bash
# get token
curl --location --request DELETE 'localhost:8082/api/products/662a1fe9d1d7e15ee5330e78' \
--header 'Authorization: Bearer 34534'
```
```json
//Response 200 ok
```

### Instalación 🔧

Generar los contenedores con el profile de QA (PROBAR ESTE ENTORNO):
- Contenedor de mongodb
- Contenedor de mysql
- Contenedor de ms-credentials-control
- Contenedor de ms-products
- Contenedor de ms-customer-products

EL puerto externo para este entorno de QA: 
- 8080 => ms-customer-products
- 8081 => ms-credentials-control
- 8082 => ms-products

```bash
1ro Clonar el repositorio
- git clone https://github.com/cbdavid14/ms-api-go-banking.git

2do Compilar el proyecto

3ro Generar los contenedores
#Generar contenedores en ambiente de QA
make qa
4to Bajar los contenedores
#Bajar los contenedores en ambiente de QA
make qa-down
```

Generar los contenedores con el profile de Dev:
- Contenedor de mongodb
- Contenedor de mysql

EL puerto externo para este entorno de QA:

7001 => ms-customer-products
9001 => ms-credentials-control
8001 => ms-products
```bash
1ro Clonar el repositorio
- git clone https://github.com/cbdavid14/code-challenge-encora.git

2do Compilar el proyecto

3ro Generar los contenedores
#Generar contenedores en ambiente de DEV
make dev
4to Bajar los contenedores
#Bajar los contenedores en ambiente de DEV
make dev-down
```

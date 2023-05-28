# REST-web-services-final-project
**4th semester.** 
Final project on the subject of **REST-oriented web services**.


This is a prototype auction, which consists of 3 independent REST servers and databases.

Each server is written in spring boot and uses MongoDB.

The project also has a web interface using vue.js. The web interface is for demonstration only.



------------------------------------------------------------------------------------------

## How to run MongoDBs. 
#### Each server has its own database. The container must be run separately for each server.

1.Build Docker images based on the configuration defined in the docker-compose.yml

    docker-compose build

2.Download Docker images for all services defined in the docker-compose.yml

    docker-compose pull

3.Start containers and run composition for all services defined in the docker-compose.yml

    docker-compose up -d


## How to run Spring boot servers

https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-running-your-application.html


## How to run Vue.js web client

1.Project Setup

    npm install

2.Compile and Hot-Reload for Development

    npm run dev


## How to connect to servers and web client

<table>
    <thead>
        <tr>
            <th></th>
            <th>Authentication server</th>
            <th>Application server</th>
            <th>Payment server</th>
            <th>Web client app</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Base URL</td>
            <td align="center" colspan="4"><a>http://localhost</a></td>
        </tr>
        <tr>
            <td>app ports</td>
            <td align="center">8080</td>
            <td align="center">8081</td>
            <td align="center">8082</td>
            <td align="center">3000</td>
        </tr>
        <tr>
            <td>DB ports</td>
            <td align="center">27017</td>
            <td align="center">27018</td>
            <td align="center">27019</td>
            <td align="center">-</td>
        </tr>
    </tbody>
</table>



## How to run stripe locally to test payments

1.Install the Stripe CLI

    https://stripe.com/docs/stripe-cli#install

2.Login in to the CLI

    https://stripe.com/docs/stripe-cli#login-account

3.Start webhook listener in terminal

    stripe listen --forward-to=localhost:8082/api/payment/webhook



## Authentication Server

### Base Url: http://localhost:8080

#### Login
<details>
<summary>
    <code>POST</code> <code><b>/api/user/login</b></code><code>(allows to login, issues a bearer token)</code>
</summary>

##### Parameters
> | name     | type       | data type    |                                                           
> |----------|------------|--------------|
> | login    | required   | string       |
> | password | required   | string       |
##### Responses
> | http code | content-type              | response                                                                |
> |-----------|---------------------------|-------------------------------------------------------------------------|
> | `200`     | `application/json`        | `{"type": "Bearer", "accessToken": "eyJhbGciOi..........."}`            |
> | `401`     | `application/json`        | `{"status": "UNAUTHORIZED", "message": "email or password is not valid"}` |
</details>


#### Logout (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/user/logout</b></code><code>(allows to logout, deactivates the token)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type                | response                                                      |
> |-----------|-----------------------------|---------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8`  | `"User logged out successfully"`                              |
> | `401`     | `application/json`          | `{"status": "UNAUTHORIZED", "message": "token is not valid"}` |
</details>


#### Create user
<details>
<summary>
    <code>POST</code> <code><b>/api/user/create</b></code><code>(allows to create an account)</code>
</summary>

##### Parameters
> | name      | type       | data type |                                                           
> |-----------|------------|-----------|
> | login     | required   | string    |
> | password  | required   | string    |
> | firstName | required   | string    |
> | lastName  | required   | string    |


##### Responses
> | http code | content-type               | response                                                                      |
> |-----------|----------------------------|-------------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"user added successfully"`                                                   |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "firstName: must not be empty........"` |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "User already exists"`                  |
</details>


#### Get users (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/user</b></code><code>(find all active users)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type          | response                                                                                                      |
> |-----------|-----------------------|---------------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json`    | `[{"id": "645e5414830e393ff09ce411", "email": "user1@gmail.com","firstName": "user1","lastName": "user1"}, ]` |
> | `401`     | `application/json`    | `{"status": "UNAUTHORIZED", "message": "token is not valid"}`                                                 |
</details>


#### Get one user (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/user/{ userId }</b></code><code>(find one active user by id)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type       | response                                                                                                  |
> |-----------|--------------------|-----------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json` | `{"id": "645e5414830e393ff09ce411", "email": "user1@gmail.com","firstName": "user1","lastName": "user1"}` |
> | `400`     | `application/json` | `{"status": "BAD_REQUEST", "message": "User not found for id: 214e1423..."}`                              |
> | `401`     | `application/json` | `{"status": "UNAUTHORIZED", "message": "token is not valid"}`                                             |
</details>


#### Get authorized user (*token required*) 
<details>
<summary>
    <code>GET</code> <code><b>/api/user/auth/info</b></code><code>(get only this authorized user)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type       | response                                                                                                  |
> |-----------|--------------------|-----------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json` | `{"id": "645e5414830e393ff09ce411", "email": "user1@gmail.com","firstName": "user1","lastName": "user1"}` |
> | `400`     | `application/json` | `{"status": "BAD_REQUEST", "message": "User does not exist"`                                              |
> | `401`     | `application/json` | `{"status": "UNAUTHORIZED", "message": "token is not valid"}`                                             |
</details>


#### Update authorized user (*token required*)
<details>
<summary>
    <code>PUT</code> <code><b>/api/user</b></code><code>(update only this authorized user)</code>
</summary>

##### Parameters
> | name      | type       | data type |                                                           
> |-----------|------------|-----------|
> | firstName | required   | string    |
> | lastName  | required   | string    |

##### Responses
> | http code | content-type               | response                                                                                 |
> |-----------|----------------------------|------------------------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"User successfully changed"`                                                            |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "firstName: must not be empty........"`            |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "User is not authorized to update this profile"}` |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "token is not valid"}`                            |            
</details>


#### Delete authorized user (*token required*)
<details>
<summary>
    <code>DELETE</code> <code><b>/api/user</b></code><code>(delete only this authorized user)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type               | response                                                                                 |
> |-----------|----------------------------|------------------------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"User successfully deleted"`                                                            |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "User is not authorized to delete this profile"}` |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "token is not valid"}`                            |            
</details>


## Application Server

### Base Url: http://localhost:8081


#### Create a new auction lot (*token required*)
<details>
<summary>
    <code>POST</code> <code><b>/api/auction/create</b></code><code>(allows to create a new auction lot)</code>
</summary>

##### Parameters
> | name          | type     | data type                                                    |                                                           
> |---------------|----------|--------------------------------------------------------------|
> | name          | required | string                                                       |
> | description   | required | string                                                       |
> | startingPrise | int      | string                                                       |
> | lifeTime      | required | string ("test" OR "one-day" OR "three-days"  OR "one-week" ) |


##### Responses
> | http code | content-type               | response                                                                             |
> |-----------|----------------------------|--------------------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"645e5414830e393ff09ce411"`                                                         |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "description: must not be empty........"`      |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "An active lot with this name already exists"` |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                |
</details>


#### Upload an image for the auction lot (*token required*) (*image required*)
<details>
<summary>
    <code>POST</code> <code><b>/api/image/upload/{ auctionId }</b></code><code>(allows to attach an image to your auction)</code>
</summary>


##### Parameters
> None

##### Responses
> | http code | content-type               | response                                                              |
> |-----------|----------------------------|-----------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"645e5414830e393ff09ce411"`                                          |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "Image already exist"`          |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}` |
</details>


#### Get an image for the auction lot (*token required*)

<details>
<summary>
    <code>GET</code> <code><b>/api/image/download/{ auctionId }</b></code><code>(allows you to get an auction image)</code>
</summary>

##### Parameters
> None

##### Responses
> | http code | content-type       | response                                                              |
> |-----------|--------------------|-----------------------------------------------------------------------|
> | `200`     | `image/png`        | `image file`                                                          |
> | `400`     | `application/json` | `{"status": "BAD_REQUEST", "message": "Image not found"`              |
> | `401`     | `application/json` | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}` |
</details>


#### Update the auction lot (*token required*)
<details>
<summary>
    <code>PUT</code> <code><b>/api/auction/{ auctionId }</b></code><code>(allows an authorized user to update their auction)</code>
</summary>

##### Parameters
> | name          | type     | data type                                                    |                                                           
> |---------------|----------|--------------------------------------------------------------|
> | description   | required | string                                                       |

##### Responses
> | http code | content-type               | response                                                                                    |
> |-----------|----------------------------|---------------------------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"Auction id: 645e5414830e393ff09ce411 successfully changed"`                               |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "description: must not be empty........"`             |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "Auction not found for id: 645e5414830e393ff09ce411"` |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "User is not authorized to update this auction"}`    |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                       |
</details>


#### Delete an authorized user's auction (*token required*)
<details>
<summary>
    <code>DELETE</code> <code><b>/api/user</b></code><code>(allows an authorized user to delete his lot if there aren't participants)</code>
</summary>

##### Parameters
> None

##### Responses
> | http code | content-type               | response                                                                                    |
> |-----------|----------------------------|---------------------------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"Auction id: 645e5414830e393ff09ce411 successfully deleted"`                               |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "You can't delete an auction that has participants"`  |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "Auction not found for id: 645e5414830e393ff09ce411"` |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "User is not authorized to delete this auction"}`    |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                       |                                     
</details>


#### Place a bet (*token required*)
<details>
<summary>
    <code>POST</code> <code><b>/api/user</b></code><code>(allows to place a bet)</code>
</summary>

##### Parameters
> | name      | type     | data type |                                                           
> |-----------|----------|-----------|
> | auctionId | required | string    |
> | bet       | required | int       |


> | http code | content-type               | response                                                                                        |
> |-----------|----------------------------|-------------------------------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"The new bet has been created successfully"`                                                   |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "bet: must not be empty........"`                         |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "User is trying to buy his product"`                      |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "The auction is outdated"`                                |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "The bet is less than the current price"`                 |
> | `400`     | `application/json`         | `{"status": "BAD_REQUEST", "message": "Auction lot not fount for id: 645e5414830e393ff09ce411"` |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                           |                                        
</details>


#### Get active auction lots (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/auction</b></code><code>(find all active auction lots)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type          | response                                                                                                                                                                                                                                                                                                                             |
> |-----------|-----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json`    | `[{"id": "645fa196aa3af742845525bb", "sellerEmail": "test2@gmail.com","sellerName": "test2", "status": "ACTIVE", "participation": {"645e5414830e393ff09ce411": 546,}, "name": "testauction", "description": "testauction", "starting_price": 100, "current_price": 546, "end_time": "2023-06-20T14:41:26.021Z", "winnerId": nul}, ]` |
> | `401`     | `application/json`    | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                                                                                                                                                                                                                                                                |
</details>


#### Get auctions that were created by an authorized user (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/auction/auth-user-auctions</b></code><code>(find all auction lots that were created by an authorized user)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type          | response                                                                                                                                                                                                                                                                                                                             |
> |-----------|-----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json`    | `[{"id": "645fa196aa3af742845525bb", "sellerEmail": "test2@gmail.com","sellerName": "test2", "status": "ACTIVE", "participation": {"645e5414830e393ff09ce411": 546,}, "name": "testauction", "description": "testauction", "starting_price": 100, "current_price": 546, "end_time": "2023-06-20T14:41:26.021Z", "winnerId": nul}, ]` |
> | `401`     | `application/json`    | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                                                                                                                                                                                                                                                                |
</details>


#### Get auctions that were created by an authorized user (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/auction/auth-participant-auctions</b></code><code>(find all active auctions in which the authorized user participates)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type          | response                                                                                                                                                                                                                                                                                                                             |
> |-----------|-----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json`    | `[{"id": "645fa196aa3af742845525bb", "sellerEmail": "test2@gmail.com","sellerName": "test2", "status": "ACTIVE", "participation": {"645e5414830e393ff09ce411": 546,}, "name": "testauction", "description": "testauction", "starting_price": 100, "current_price": 546, "end_time": "2023-06-20T14:41:26.021Z", "winnerId": nul}, ]` |
> | `401`     | `application/json`    | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                                                                                                                                                                                                                                                                |
</details>


#### Get finished auctions, where the authorized user is winner (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/auction/auth-winner-auctions</b></code><code>(find all finished auctions, where the authorized user is winner)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type          | response                                                                                                                                                                                                                                                                                                                                                      |
> |-----------|-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json`    | `[{"id": "645fa196aa3af742845525bb", "sellerEmail": "test2@gmail.com","sellerName": "test2", "status": "FINISHED", "participation": {"645e5414830e393ff09ce411": 546,}, "name": "testauction", "description": "testauction", "starting_price": 100, "current_price": 546, "end_time": "2023-04-20T14:41:26.021Z", "winnerId": "645e5414830e393ff09ce411"}, ]` |
> | `401`     | `application/json`    | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                                                                                                                                                                                                                                                                                         |
</details>


#### Get auctions paid for by an authorized user (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/auction/auth-purchased-auctions</b></code><code>(find all auctions paid for by an authorized user)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type          | response                                                                                                                                                                                                                                                                                                                                                  |
> |-----------|-----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json`    | `[{"id": "645fa196aa3af742845525bb", "sellerEmail": "test2@gmail.com","sellerName": "test2", "status": "PAID", "participation": {"645e5414830e393ff09ce411": 546,}, "name": "testauction", "description": "testauction", "starting_price": 100, "current_price": 546, "end_time": "2023-04-20T14:41:26.021Z", "winnerId": "645e5414830e393ff09ce411"}, ]` |
> | `401`     | `application/json`    | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                                                                                                                                                                                                                                                                                     |
</details>


#### Get one auction lot (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/auction/{ auctionId }</b></code><code>(find one auction lot by id)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type          | response                                                                                                                                                                                                                                                                                                                         |
> |-----------|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> | `200`     | `application/json`    | `{"id": "645fa196aa3af742845525bb", "sellerEmail": "test2@gmail.com","sellerName": "test2", "status": "ACTIVE", "participation": {"645e5414830e393ff09ce411": 546,}, "name": "testauction", "description": "testauction", "starting_price": 100, "current_price": 546, "end_time": "2023-06-20T14:41:26.021Z", "winnerId": nul}` |
> | `401`     | `application/json`    | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`                                                                                                                                                                                                                                                            |
</details>


## Payment Server

### Base Url: http://localhost:8082

#### Initial payment (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/payment/{ auctionId }</b></code><code>(initial payment)</code>
</summary>

##### Parameters
> None
##### Responses
> | http code | content-type               | response                                                                  |
> |-----------|----------------------------|---------------------------------------------------------------------------|
> | `200`     | `text/plain;charset=UTF-8` | `"https://stripe.com/....."`                                              |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "Auction integration exception"}`  |
> | `401`     | `application/json`         | `{"status": "UNAUTHORIZED", "message": "Auth integration exception"}`     |
</details>


#### Stripe validate webhook (*token required*)
<details>
<summary>
    <code>GET</code> <code><b>/api/payment/webhook</b></code><code>webhook for validate payment</code>
</summary>

https://stripe.com/docs/api/webhook_endpoints

</details>


## Links

* [Docker](https://developer.fedoraproject.org/tools/docker/about.html) how to install Docker
* [Docker Compose](https://developer.fedoraproject.org/tools/docker/compose.html) how to install docker-compose

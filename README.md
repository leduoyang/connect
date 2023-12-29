# Connect Social Networking Platform

Connect is a social networking platform designed to be a hub where users can share and showcase their side projects and ideas. Built on a robust technology stack and equipped with various services, Connect aims to foster a community of creative minds.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.1.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Redis](https://img.shields.io/badge/Redis-6.0.8-red)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-8.11.3-blue)
[![Docker](https://img.shields.io/badge/Docker-✔-blue)](https://www.docker.com/)


## Technology Stack

- Java Spring Boot
- MySQL
- Redis
- Elasticsearch, Kibana, Logstash

## Running the Service
To run the Connect Social Networking Platform service, follow the steps below:

1. Navigate to the docker-compose directory: ``cd docker-compose``
2. Build and start the service using Docker Compose: ``docker-compose up --build`` This command will download the necessary Docker images, build the Connect service, and start the containers.
3. Once the service is up and running, you can interact with endpoints of the service through http://localhost:8080

## Entities

Connect is organized around the following Entities:

- User
- Follow
- Star
- Project
- Post
- Comment

## Enums
| Parameter        | Type     | Description      |
|------------------|----------|--------------|
| UserStatus   | Integer   | 0 - PUBLIC, 1 - SEMI-PUBLIC, 2 - PRIVATE, 3 - DELETED |
| UserRole   | Integer   | 0 - ESSENTIAL, 1 - PLUS, 2 - PREMIUM, 3 - ADMIN |
| CodeStatus (Email Verification Code)   | Integer   | 0 - DELETED, 1 - PENDING, 2 - COMPLETED, 3 - EXPIRED |
| FollowStatus   | Integer   | 0 - UNFOLLOW, 1 - PENDING, 2 - APPROVED, 3 - REJECTED |
| StarTargetType   | Integer   | 0 - PROJECT, 1 - POST, 2 - COMMENT |

ProjectStatus, PostStatus, CommentStatus all follow the definition of UserStatus

## API Endpoints
**Prefix: "/api/connect/v1"**

All requests, except for public APIs, must indicate a JWT token , acquired through the sign-in endpoint, in the header Authorization.

### User

#### Sign Up
- Endpoint: `/public/user/signup`
- Method: POST
- Description: user needs to complete email verification process (Query Email Verification + Verify Email Verification) before requesting for signup

Payload:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| username         | String   | john_doe      |
| password            | String   |  |
| description         | String   |   |
| email         | String   | user@email.com  |
| phone         | String   | 12345678  |
| uid         | String   | acquired from the response of email verification  |


#### Sign In
- Endpoint: `/public/user/signin`
- Method: POST

Payload:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| username         | String   | john_doe      |
| password            | String   |  |

#### Query User By Username
- Endpoint: `/user/{username}`
- Method: GET

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| username           | String   | john_doe     |

#### Query User With Filter
- Endpoint: `/user`
- Method: GET

Params:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| keyword           | String   | software     |
| pageIndex           | Integer   | 1     |
| pageSize           | Integer   | 20     |

#### Edit Personal Info
- Endpoint: `/user/me/edit`
- Method: PATCH

Payload:

| Parameter       | Type     | Example      |
|-----------------|----------|--------------|
| username          | String   | john_doe     |
| passowrd        | String   |  |
| status          | Integer  | 0 - public, 1 - semi, 2 - private |
| description     | String   | john_doe's profile |
| email           | String   |  |
| phone           | String   |  |

Note. TODO email should be verficiation again if getting updated

#### Edit Profile
- Endpoint: `/user/{username}`
- Method: PATCH

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| username           | String   | john_doe     |

Payload:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| username           | String   | john_doe     |
| status           | Integer   | 0 - public, 1 - semi, 2 - private |
| description      | String   | john_doe's profile |


#### Delete User
- Endpoint: `/user/{username}`
- Method: DELETE

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| username           | String   | john_doe     |

#### Upload Profile Image
- Endpoint: `/user/upload/profileImage`
- Method: POST

Payload:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| profileImage     | File     | *.jpeg, *.jpg, *.png |

#### Query Personal Info
- Endpoint: `/user/me`
- Method: GET

#### Query Personal Star List
- Endpoint: `/user/me/starList`
- Method: GET
- Description: projects, posts, and comments, of which users have given stars, are retrieved

#### Query Personal Pending List
- Endpoint: `/user/me/pendingList`
- Method: GET
- Description: only users of semi-public can query pending list of requests for following

#### Query Personal Follower List
- Endpoint: `/user/me/followerList`
- Method: GET

#### Query Personal Following List
- Endpoint: `/user/me/followingList`
- Method: GET

### Verification

#### Query Email Verification
- Endpoint: `/public/verification/email`
- Method: GET

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| email            | String       | leduoyoung@gmail.com       |

#### Verify Email Verification
- Endpoint: `/public/verification/email`
- Method: POST

Payload:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| email            | String       | leduoyoung@gmail.com       |
| code            | String       | abcd1234       |

### Follow

#### Follow
- Endpoint: `/follow/{followingId}`
- Method: POST
- Description: when following a public account, the request will be made instantly while when following a semi-public account, the user will be able to follow target user {followingId} after {followingId} approves the request

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| followingId (target username to follow )   | String   |  john_doe   |

#### Unfollow
- Endpoint: `/follow/{following}`
- Method: DELETE

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| following (target username to unfollow )   | String   |  john_doe   |


#### Is Following
- Endpoint: `/follow/isFollowing/{following}`
- Method: GET
- Description: check if the current user is following user {following}

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| following   | String   |  john_doe   |

#### Remove
- Endpoint: `/follow/remove/{follower}`
- Method: DELETE
- Description: remove a user {follower} from the follower list

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| follower (target username to remove from followers )   | String   |  john_doe   |

#### Approve
- Endpoint: `/follow/approve/{follower}`
- Method: POST
- Description: when the current user is a semi-public account, approve the follow request from user {followerId}

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| follower   | String   |  john_doe   |

#### Reject
- Endpoint: `/follow/reject/{follower}`
- Method: POST
- Description: when the current user is a semi-public account, reject the follow request from user {followerId}

PathVariable:

| Parameter        | Type     | Example      |
|------------------|----------|--------------|
| follower   | String   |  john_doe   |

#### Approve All
- Endpoint: `/follow/approveAll`
- Method: POST
- Description: when the current user is a semi-public account, approve all requests of following for other users in the pending list

Examples demonstrating the interactions to the service can be found and import to Postman in the`` postman_script.json``
Access the API documentation through Swagger for more details: http://localhost:8080/swagger-ui.html

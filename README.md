# News-Feed 프로젝트

***

## 📖 와이어프레임

![Image](https://github.com/user-attachments/assets/ffc84e07-c39f-4f98-bc91-9e17a4899792)

***

## 📖 API 문서

***

### 🔥 BASE URL: `/api/v1` 🔥

***

### ⛔ Error Response Example

#### Code List

- `400(BAD_REQUEST)`, `401(UNAUTHORIZED)`, `403(FORBIDDEN)` `404(NOT_FOUND)`, `409(CONFLICT)`

```json
{
  "status": "UNAUTHORIZED",
  "code": "401",
  "message": "로그인이 필요합니다.",
  "timestamp": "2025-02-19T15:00:31.082217100"
}
```

***

## 🛠 Users API

| Method  | Endpoint            | Description | JWT Required     | Parameters                 | Request Body                                                                                                                  | Response                                                                                                                                                                                                                                                                            | Status Code                                                                                             | 
|---------|---------------------|-------------|------------------|----------------------------|-------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| `POST`  | `/users/signup`     | 회원 가입       | <pre>⛔ No</pre>  | 없음                         | <pre>{<br/>  "email": "string",<br/>  "password": "string",<br/>  "username": "string",<br/>  "address": "string"<br/>}</pre> | <pre>{<br/>  "id": "long",<br/>  "email": "string",<br/>  "username": "string",<br/>  "address": "string",<br/>  "followerCount": "long",<br/>  "followingCount": "long",  <br/>  "createdAt": "string",<br/>  "modifiedAt": "string" <br/>}</pre>                                  | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 409 CONFLICT</pre>                                            |
| `POST`  | `/users/login`      | 로그인         | <pre>⛔ No</pre>  | 없음                         | <pre>{<br/>  "email": "string",<br/>  "password": "string"<br/>}</pre>                                                        | <pre>Header: {<br/>  Authorization: Bearer JWT Token<br/>}<br/><br/>Body: {<br/>  "message": "로그인에 성공했습니다."<br/>}</pre>                                                                                                                                                             | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 404 NOT_FOUND</pre>                    |
| `POST`  | `/users/logout`     | 로그아웃        | <pre>✅ Yes</pre> | 없음                         | 없음                                                                                                                            | <pre>{<br/>  "message": "로그아웃에 성공했습니다."<br/>}</pre>                                                                                                                                                                                                                                 | <pre>✅ 200 OK<br/>⛔ 401 UNAUTHORIZED</pre>                                                              | 
| `GET`   | `/users/{id}`       | 유저 단건 조회    | <pre>✅ Yes</pre> | <pre>Path: <br/>- id</pre> | 없음                                                                                                                            | <pre>{<br/>  "id": "long",<br/>  "email": "string",<br/>  "username": "string",<br/>  "address": "null or string",<br/>  "followerCount": "long",<br/>  "followingCount": "long",  <br/>  "createdAt": "string",<br/>  "modifiedAt": "string"<br/>}</pre>                           | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 404 NOT_FOUND</pre>                    | 
| `GET`   | `/users/followers`  | 팔로워 목록 조회   | <pre>✅ Yes</pre> | 없음                         | 없음                                                                                                                            | <pre>[<br/>  { <br/>    "id": "long",<br/>    "email": "string",<br/>    "username": "string",<br/>    "address": "string",<br/>    "followerCount": "long",<br/>    "followingCount": "long",  <br/>    "createdAt": "string",<br/>    "modifiedAt": "string" <br/>  }<br/>]</pre> | <pre>✅ 200 OK<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 404 NOT_FOUND</pre>                                          |
| `GET`   | `/users/followings` | 팔로잉 목록 조회   | <pre>✅ Yes</pre> | 없음                         | 없음                                                                                                                            | <pre>[<br/>  { <br/>    "id": "long",<br/>    "email": "string",<br/>    "username": "string",<br/>    "address": "null",<br/>    "followerCount": "long",<br/>    "followingCount": "long",  <br/>    "createdAt": "string",<br/>    "modifiedAt": "string" <br/>  }<br/>]</pre>   | <pre>✅ 200 OK<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 404 NOT_FOUND</pre>                                          | 
| `PATCH` | `/users`            | 비밀번호 수정     | <pre>✅ Yes</pre> | 없음                         | <pre>{<br/>  "oldPassword": "string",<br/>  "newPassword": "string"<br/>}</pre>                                               | <pre>{<br/>  "message": "비밀번호 변경에 성공했습니다."<br/>}</pre>                                                                                                                                                                                                                              | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 404 NOT_FOUND<br/>⛔ 409 CONFLICT</pre> |
| `POST`  | `/users/withdraw`   | 회원 탈퇴       | <pre>✅ Yes</pre> | 없음                         | <pre>{<br/>  "password": "string"<br/>}</pre>                                                                                 | <pre>{<br/>  "message": "회원탈퇴에 성공했습니다."<br/>}</pre>                                                                                                                                                                                                                                 | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 409 CONFLICT</pre>                     | 

## 🛠 Posts API

| Method   | Endpoint          | Description       | JWT Required     | Parameters                                                                                                                       | Request Body                                                                                        | Response                                                                                                                                                                                                                                                                                                                          | Status Code                                                                                              | 
|----------|-------------------|-------------------|------------------|----------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| `POST`   | `/posts`          | 게시물 생성            | <pre>✅ Yes</pre> | 없음                                                                                                                               | <pre>{<br/>  "title": "string",<br/>  "contents": "string"<br/>}</pre>                              | <pre>{<br/>  "postId": "long",<br/>  "username": "string",<br/>  "title": "string",<br/>  "contents": "string",<br/>  "createdAt": "string",<br/>  "modifiedAt": "string"<br/>}</pre>                                                                                                                                             | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED</pre>                                         | 
| `GET`    | `/posts/find/all` | 게시물 전체 목록 조회      | <pre>✅ Yes</pre> | <pre>Query:<br/>- page(default: 1)<br/>- size(default: 10)<br/>- startDate(required: false)<br/>- endDate(required: false)</pre> | 없음                                                                                                  | <pre>{<br/>  contents: [{ <br/>    "postId": "long",<br/>    "username": "string",<br/>    "title": "string",<br/>    "contents": "string",<br/>    "createdAt": "string",<br/>    "modifiedAt": "string"<br/>  }], <br/>  "size": "int",<br/>  "number": "int",<br/>  "totalElements": long,<br/>  "totalPages": int<br/>}</pre> | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST</pre>                                                                |
| `GET`    | `/followers`     | 팔로잉한 유저 게시물 목록 조회 | <pre>✅ Yes</pre> | <pre>Query:<br/>- page(default: 1)<br/>- size(default: 10)<br/>- userId(nullable)</pre>                                          | 없음                                                                                                  | <pre>{<br/>  contents: [{ <br/>    "id": "long",<br/>    "username": "string",<br/>    "title": "string",<br/>    "contents": "string",<br/>    "createdAt": "string",<br/>    "modifiedAt": "string"<br/>  }], <br/>  "size": "int",<br/>  "number": "int",<br/>  "totalElements": int,<br/>  "totalPages": int<br/>}</pre>      | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST</pre>                                                                |
| `GET`    | `/posts/{postId}` | 게시물 단건 조회         | <pre>✅ Yes</pre> | <pre>Path: <br/>- postId</pre>                                                                                                   | 없음                                                                                                  | <pre>{<br/>  "id": "long",<br/>  "username": "string",<br/>  "title": "string",<br/>  "contents": "string",<br/>  "createdAt": "string",<br/>  "modifiedAt": "string"<br/>}</pre>                                                                                                                                                 | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 404 NOT_FOUND</pre>                                            | 
| `PATCH`  | `/posts/{postId}` | 게시물 제목, 내용 수정     | <pre>✅ Yes</pre> | <pre>Path: <br/>- postId</pre>                                                                                                   | <pre>{<br/>  "password": "string", <br/>  "title": "string",<br/>  "contents": "string"<br/>}</pre> | <pre>{<br/>  "id": "long",<br/>  "username": "string",<br/>  "title": "string",<br/>  "contents": "string",<br/>  "createdAt": "string",<br/>  "modifiedAt": "string"<br/>}</pre>                                                                                                                                                 | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 403 FORBIDDEN<br/>⛔ 404 NOT_FOUND</pre> | 
| `DELETE` | `/posts/{postId}` | 게시물 단건 삭제         | <pre>✅ Yes</pre> | <pre>Path: <br/>- postId</pre>                                                                                                   | <pre>{<br/>  "password": "string"}</pre>                                                            | <pre>{<br/>  "message": "게시물 삭제에 성공했습니다."<br/>}</pre>                                                                                                                                                                                                                                                                             | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 403 FORBIDDEN<br/>⛔ 404 NOT_FOUND</pre> | 

## 🛠 Followers API

| Method   | Endpoint     | Description | JWT Required     | Parameters | Request Body                                       | Response                                            | Status Code                                                                          | 
|----------|--------------|-------------|------------------|------------|----------------------------------------------------|-----------------------------------------------------|--------------------------------------------------------------------------------------|
| `POST`   | `/followers` | 팔로우 요청      | <pre>✅ Yes</pre> | 없음         | <pre>{<br/>  "followingUserId": "Long"<br/>}</pre> | <pre>{<br/>  "message": "팔로우에 성공했습니다."<br/>}</pre>  | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 409 CONFLICT</pre>  |
| `DELETE` | `/followers` | 언팔로우        | <pre>✅ Yes</pre> | 없음         | <pre>{<br/>  "followingUserId": "Long"<br/>}</pre> | <pre>{<br/>  "message": "언팔로우에 성공했습니다."<br/>}</pre> | <pre>✅ 200 OK<br/>⛔ 400 BAD_REQUEST<br/>⛔ 401 UNAUTHORIZED<br/>⛔ 404 NOT_FOUND</pre> | 

***

## 📖 ERD

![Image](https://github.com/user-attachments/assets/bdf16c32-3ccb-4aa4-b1a5-1855c5bf6074)
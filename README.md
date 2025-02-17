# News-Feed 프로젝트

***

## 📖 와이어프레임

![Image](https://github.com/user-attachments/assets/ffc84e07-c39f-4f98-bc91-9e17a4899792)

***

## 📖 API 문서

***

### 🔥 BASE URL: `/api/v1` 🔥

## 🛠 Users API

| Method   | Endpoint            | Description | JWT Required     | Parameters                 | Request Body                                                                                                                  | Response                                                                                                                                                                                                                                                                          | Status Code       | 
|----------|---------------------|-------------|------------------|----------------------------|-------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| `POST`   | `/users/signup`     | 회원 가입       | <pre>⛔ No</pre>  | 없음                         | <pre>{<br/>  "email": "string",<br/>  "password": "string",<br/>  "username": "string",<br/>  "address": "string"<br/>}</pre> | <pre>{<br/>  "id": "long",<br/>  "email": "string",<br/>  "username": "string",<br/>  "address": "string",<br/>  "followerCount": "long",<br/>  "followingCount": "long",  <br/>  "createdAt": "string",<br/>  "modifiedAt": "string" <br/>}</pre>                                | <pre>200 OK</pre> |
| `POST`   | `/users/login`      | 로그인         | <pre>⛔ No</pre>  | 없음                         | <pre>{<br/>  "email": "string",<br/>  "password": "string"<br/>}</pre>                                                        | <pre>Header: {<br/>  Authorization: Bearer JWT Token<br/>}<br/><br/>Body: {<br/>  "message": "로그인에 성공했습니다."<br/>}</pre>                                                                                                                                                           | <pre>200 OK</pre> |
| `POST`   | `/users/logout`     | 로그아웃        | <pre>✅ Yes</pre> | 없음                         | 없음                                                                                                                            | <pre>{<br/>  "message": "로그아웃에 성공했습니다."<br/>}</pre>                                                                                                                                                                                                                               | <pre>200 OK</pre> | 
| `GET`    | `/users/{id}`       | 유저 단건 조회    | <pre>✅ Yes</pre> | <pre>Path: <br/>- id</pre> | 없음                                                                                                                            | <pre>{<br/>  "id": "long",<br/>  "email": "string",<br/>  "username": "string",<br/>  "address": "null or string",<br/>  "followerCount": "long",<br/>  "followingCount": "long",  <br/>  "createdAt": "string",<br/>  "modifiedAt": "string"<br/>}</pre>                         | <pre>200 OK</pre> | 
| `GET`    | `/users/followings` | 팔로잉 목록 조회   | <pre>✅ Yes</pre> | 없음                         | 없음                                                                                                                            | <pre>[<br/>  { <br/>    "id": "long",<br/>    "email": "string",<br/>    "username": "string",<br/>    "address": "null",<br/>    "followerCount": "long",<br/>    "followingCount": "long",  <br/>    "createdAt": "string",<br/>    "modifiedAt": "string" <br/>  }<br/>]</pre> | <pre>200 OK</pre> | 
| `PATCH`  | `/users`            | 비밀번호 수정     | <pre>✅ Yes</pre> | 없음                         | <pre>{<br/>  "oldPassword": "string",<br/>  "newPassword": "string"<br/>}</pre>                                               | <pre>{<br/>  "message": "비밀번호 변경에 성공했습니다."<br/>}</pre>                                                                                                                                                                                                                            | <pre>200 OK</pre> |
| `DELETE` | `/users`            | 회원 탈퇴       | <pre>✅ Yes</pre> | 없음                         | <pre>{<br/>  "password": "string"<br/>}</pre>                                                                                 | <pre>{<br/>  "message": "회원탈퇴에 성공했습니다."<br/>}</pre>                                                                                                                                                                                                                               | <pre>200 OK</pre> | 

## 🛠 Posts API

| Method   | Endpoint          | Description   | JWT Required     | Parameters                                                                              | Request Body                                                           | Response                                                                                                                                                                                                                                                                                       | Status Code       | 
|----------|-------------------|---------------|------------------|-----------------------------------------------------------------------------------------|------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| `POST`   | `/posts`          | 게시물 생성        | <pre>✅ Yes</pre> | 없음                                                                                      | <pre>{<br/>  "title": "string",<br/>  "contents": "string"<br/>}</pre> | <pre>{<br/>  "id": "long",<br/>  "title": "string",<br/>  "contents": "string",<br/>  "createdAt": "string",<br/>  "modifiedAt": "string"<br/>}</pre>                                                                                                                                          | <pre>200 OK</pre> | 
| `GET`    | `/posts`          | 게시물 목록 조회     | <pre>✅ Yes</pre> | <pre>Query:<br/>- page(default: 1)<br/>- size(default: 10)<br/>- userId(nullable)</pre> | 없음                                                                     | <pre>{<br/>  contents: [{ <br/>    "id": "long",<br/>    "title": "string",<br/>    "contents": "string",<br/>    "createdAt": "string",<br/>    "modifiedAt": "string"<br/>  }], <br/>  "size": "int",<br/>  "number": "int",<br/>  "totalElements": int,<br/>  "totalPages": int<br/>}</pre> | <pre>200 OK</pre> |
| `GET`    | `/posts/{postId}` | 게시물 단건 조회     | <pre>✅ Yes</pre> | <pre>Path: <br/>- postId</pre>                                                          | 없음                                                                     | <pre>{<br/>  "id": "long",<br/>  "title": "string",<br/>  "contents": "string",<br/>  "createdAt": "string",<br/>  "modifiedAt": "string"<br/>}</pre>                                                                                                                                          | <pre>200 OK</pre> | 
| `PATCH`  | `/posts/{postId}` | 게시물 제목, 내용 수정 | <pre>✅ Yes</pre> | <pre>Path: <br/>- postId</pre>                                                          | <pre>{<br/>  "title": "string",<br/>  "contents": "string"<br/>}</pre> | <pre>{<br/>  "id": "long",<br/>  "title": "string",<br/>  "contents": "string",<br/>  "createdAt": "string",<br/>  "modifiedAt": "string"<br/>}</pre>                                                                                                                                          | <pre>200 OK</pre> | 
| `DELETE` | `/posts/{postId}` | 게시물 단건 삭제     | <pre>✅ Yes</pre> | <pre>Path: <br/>- postId</pre>                                                          | 없음                                                                     | <pre>{<br/>  "message": "게시물 삭제에 성공했습니다."<br/>}</pre>                                                                                                                                                                                                                                          | <pre>200 OK</pre> | 

## 🛠 Followers API

| Method   | Endpoint     | Description | JWT Required     | Parameters | Request Body                                       | Response                                            | Status Code       | 
|----------|--------------|-------------|------------------|------------|----------------------------------------------------|-----------------------------------------------------|-------------------|
| `POST`   | `/followers` | 팔로우 요청      | <pre>✅ Yes</pre> | 없음         | <pre>{<br/>  "followingUserId": "Long"<br/>}</pre> | <pre>{<br/>  "message": "팔로우에 성공했습니다."<br/>}</pre>  | <pre>200 OK</pre> |
| `DELETE` | `/followers` | 언팔로우        | <pre>✅ Yes</pre> | 없음         | <pre>{<br/>  "followingUserId": "Long"<br/>}</pre> | <pre>{<br/>  "message": "언팔로우에 성공했습니다."<br/>}</pre> | <pre>200 OK</pre> | 

***

## 📖 ERD

![Image](https://github.com/user-attachments/assets/9719c2ca-d900-4d81-8c2a-cfbcf0b5d49c)
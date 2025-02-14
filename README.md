# News-Feed 프로젝트

***

## 📖 API 문서

***

## ROOT_PATH: /api/v1



## 🛠 Users API
| Method  | Endpoint        | Description | Parameters        | Request Body                                                                     | Response                                                                                                                    | Status Code |
|---------|-----------------|-------------|-------------------|----------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|-------------|
| `POST`  | `/users/signup` | 회원 가입       | 없음                | `{ "email": string, "password": string, "username": string, "address": string }` | `{ "id": long, "email": string, "username": string, "address": string, "createdAt": string, "modifiedAt": string }`         | `200 OK`    |
| `POST`  | `/users/login`  | 로그인         | 없음                | `{ "email": string, "password": string }`                                        | `{ message: "로그인에 성공했습니다."} `                                                                                               | `200 OK`    |
| `POST`  | `/users/logout` | 로그아웃        | 없음                | 없음                                                                               | `{ message: "로그아웃에 성공했습니다." }`                                                                                              | `200 OK`    |
| `GET`   | `/users/{id}`   | 유저 단건 조회    | Path:<br/>- `id`  |                                                                                  | `{ "id": long, "email": string, "username": string, "address": null or string, "createdAt": string, "modifiedAt": string }` | `200 OK`    |
| `PATCH` | `/users/{id}`   | 비밀번호 수정 | Path:<br/>- `id`  | `{ "oldPassword": string, "newPassword": string }`                               | `{ message: "비밀번호 변경에 성공했습니다." }`                                                                                           | `200 OK`     |
| `PATCH` | `/users/{id}` | 회원 탈퇴 | Path:<br/>- `id` | `{ "password": string }`                                                         | `{ message: "회원탈퇴에 성공했습니다." }`                                                                                              | `200 OK`|

## 🛠 Posts API

| Method   | Endpoint           | Description   | Parameters        | Request Body                                       | Response                                                                                           | Status Code |
|----------|--------------------|---------------|-------------------|----------------------------------------------------|----------------------------------------------------------------------------------------------------|-------------|
| `POST`   | `/posts`           | 게시물 생성        | 없음                | `{ "title": string, "contents": string }`          | `{ "id": long, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }`   | `200 OK`    |
| `GET`    | `/posts`           | 게시물 목록 조회     | 없음                | 없음                                                 | `[{ "id": long, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }]` | `200 OK`    |
| `GET`    | `/posts/followers` | 팔로워 게시물 목록 조회 | 없음 | 없음 | `[{ "id": long, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }]` | `200 OK`    |
| `GET`    | `/posts/{postId}`  | 게시물 단건 조회     | Path:<br/>- `id` | 없음 | `{ "id": long, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }`   | `200 OK`    |
| `PATCH`  | `/posts/{postId}` | 게시물 제목, 내용 수정 | Path:<br/>- `id` | `{ "title": string, "contents": string }` | `{ message: "게시물 수정에 성공했습니다." } `                                                                  | `200 OK`    |
| `DELETE` | `/posts/{postId}` | 게시물 단건 삭제 | Path:<br/>- `id` | 없음 | `{ message: "게시물 삭제에 성공했습니다." }`                                                                   | `200 OK`    |


## 🛠 Followers API
| Method   | Endpoint           | Description   | Parameters | Request Body             | Response                                                                                                                      | Status Code |
|----------|--------------------|---------------|------------|--------------------------|-------------------------------------------------------------------------------------------------------------------------------|-------------|
| `POST` | `/followers` | 팔로우 요청 | 없음         | `{ "followerId": Long }` | `{ message: "팔로우에 성공했습니다." }`                                                                                                 | `200 OK`    |
| `GET` | `/followers` | 팔로우 목록 조회 | 없음 | 없음                       | `[{ "id": long, "email": string, "username": string, "address": null or string, "createdAt": string, "modifiedAt": string }]` | `200 OK`    |
|`DELETE` | `/followers` | 언팔로우 | 없음 | `{ "followerId": Long }` | `{ message: "언팔로우에 성공했습니다." }`                                                                                                | `200 OK`    |

***

## 📖 SQL

```mysql

```

***

## 📖 ERD

![Image](https://github.com/user-attachments/assets/6d89d7d9-e8ce-4d3c-afa8-6153c8afa588)
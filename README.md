# News-Feed 프로젝트

***

## 📖 와이어프레임

![Image](https://github.com/user-attachments/assets/ffc84e07-c39f-4f98-bc91-9e17a4899792)

***

## 📖 API 문서

***

## ROOT_PATH: /api/v1

## 🛠 Users API
| Method   | Endpoint            | Description      | Parameters        | Request Body                                                                           | Response                                                                                                                                           | Status Code |
|----------|---------------------|------------------|-------------------|----------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| `POST`   | `/users/signup`     | 회원 가입            | 없음                | `{ "email": string, "password": string, "username": string, "address": string }` | `{ "id": long, "email": string, "username": string, "address": string, "createdAt": string, "modifiedAt": string }`                                | `200 OK`    |
| `POST`   | `/users/login`      | 로그인              | 없음                | `{ "email": string, "password": string }`                                              | `{ Set-Cookie: JSESSIONID string, message: "로그인에 성공했습니다."} `                                                                                       | `200 OK`    |
| `POST`   | `/users/logout`     | 로그아웃             | 없음                | 없음                                                                                     | `{ message: "로그아웃에 성공했습니다." }`                                                                                                                     | `200 OK`    |
| `GET`    | `/users/{id}`       | 유저 단건 조회         | Path:<br/>- `id`  |                                                                                        | `{ "id": long, "email": string, "username": string, "address": null or string, "followerCount": long, "createdAt": string, "modifiedAt": string }` | `200 OK`    |
| `GET`    | `/users/followings` | 팔로잉 목록 조회(질문해야함) | 없음 | 없음                                                                                     | `[{ "id": long, "email": string, "username": string, "address": null or string, "createdAt": string, "modifiedAt": string }]`                      | `200 OK`    |
| `PATCH`  | `/users`            | 비밀번호 수정          | Path:<br/>- `id`  | `{ "oldPassword": string, "newPassword": string }`                                     | `{ message: "비밀번호 변경에 성공했습니다." }`                                                                                                                  | `200 OK`     |
| `DELETE` | `/users`            | 회원 탈퇴            | Path:<br/>- `id` | `{ "password": string }`                                                               | `{ message: "회원탈퇴에 성공했습니다." }`                                                                                                                     | `200 OK`|

## 🛠 Posts API

| Method   | Endpoint           | Description   | Parameters                                                                                                         | Request Body                                       | Response                                                                                                                                                                               | Status Code |
|----------|--------------------|---------------|--------------------------------------------------------------------------------------------------------------------|----------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| `POST`   | `/posts`           | 게시물 생성        | 없음                                                                                                                 | `{ "title": string, "contents": string }`          | `{ "id": long, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }`                                                                                       | `200 OK`    |
| `GET`    | `/posts`           | 게시물 목록 조회     | Query:<br/>- `page(default: 1)`<br/>- `size(default: 10)`<br/>- `userId={nullable}` | 없음                                                 | `{ contents: [{ "id": long, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }], "size": "int", "number": int, "totalElements": int, "totalPages": int}` | `200 OK`    |
| `GET`    | `/posts/{postId}`  | 게시물 단건 조회     | Path:<br/>- `id`                                                                                                   | 없음 | `{ "id": long, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }`                                                                                       | `200 OK`    |
| `PATCH`  | `/posts/{postId}` | 게시물 제목, 내용 수정 | Path:<br/>- `id`                                                                                                   | `{ "title": string, "contents": string }` | `{ "id": long, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }`                                                                                       | `200 OK`    |
| `DELETE` | `/posts/{postId}` | 게시물 단건 삭제 | Path:<br/>- `id`                                                                                                   | 없음 | `{ message: "게시물 삭제에 성공했습니다." }`                                                                                                                                                       | `200 OK`    |


## 🛠 Followers API
| Method   | Endpoint           | Description | Parameters | Request Body                  | Response                                                                                                                      | Status Code |
|----------|--------------------|-------------|------------|-------------------------------|-------------------------------------------------------------------------------------------------------------------------------|-------------|
| `POST` | `/followers` | 팔로우 요청      | 없음         | `{ "followingUserId": Long }` | `{ message: "팔로우에 성공했습니다." }`                                                                                                 | `200 OK`    |
|`DELETE` | `/followers` | 언팔로우        | 없음 | `{ "followingUserId": Long }` | `{ message: "언팔로우에 성공했습니다." }`                                                                                                | `200 OK`    |

***

## 📖 ERD

![Image](https://github.com/user-attachments/assets/fc5376d4-d05e-4c48-b2f8-384d4538093c)
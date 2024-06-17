> API Documentation

## Installation
```
pip3 install -r requirement.txt
```


## Run
```python
python3 run.py
```
<br>


# User Services
<details>
<summary>접기/펼치기</summary>

## User Registration

사용자 등록 API

```http
POST /register/user
```

| Parameter     | Type     | Description                        |
|---------------|----------|------------------------------------|
| `username`    | `string` | **Required**. Desired username     |
| `password`    | `string` | **Required**. Desired password     |
| `email`       | `string` | **Required**. User's email address |
| `originalname`| `string` | **Required**. User's real name     |

### Responses
```json
{
  "result": "success",
  "type": "register_user"
}
```
<br>

## Face Registration

얼굴 등록 API

```http
POST /register/face
```

| Parameter   | Type   | Description                          |
|-------------|--------|--------------------------------------|
| `face_image`| `file` | **Required**. Image of the user's face |

### Responses
```json
{
  "result": "success",
  "type": "register_face"
}
```
<br>

## Product Registration

제품 등록 API

```http
POST /register/product
```

| Parameter | Type     | Description                          |
|-----------|----------|--------------------------------------|
| `code`    | `string` | **Required**. Product code to register |

### Responses
```json
{
  "result": "success",
  "type": "register_product"
}
```
<br>

## Profile Image Registration

프로필 이미지 등록 API

```http
POST /register/profile
```

| Parameter     | Type   | Description                       |
|---------------|--------|-----------------------------------|
| `profile_image`| `file` | **Required**. User's profile image|

### Responses
```json
{
  "result": "success",
  "type": "profile_image"
}
```
<br>

## User Login

로그인 API

```http
POST /login
```

| Parameter | Type     | Description                        |
|-----------|----------|------------------------------------|
| `username`| `string` | **Required**. User's username      |
| `password`| `string` | **Required**. User's password      |

### Responses
```json
{
  "result": "success",
  "type": "login"
}
```
<br>

## Reset Password

비밀전호 재설정 API

```http
POST /reset_password
```

| Parameter | Type     | Description                     |
|-----------|----------|---------------------------------|
| `username`| `string` | **Required**. User's username   |
| `email`   | `string` | **Required**. User's email      |

### Responses
```json
{
  "result": "success",
  "type": "reset_password",
  "message": "New password"
}
```
<br>

## Change Password

비밀번호 변경 API

```http
POST /change_password
```

| Parameter        | Type     | Description                       |
|------------------|----------|-----------------------------------|
| `current_password` | `string`| **Required**. User's current password |
| `new_password`   | `string` | **Required**. User's new password |

### Responses
```json
{
  "result": "success",
  "type": "change_password"
}
```
<br>

## Change Profile Information

프로필 정보 변경 API

```http
POST /change_profile
```

| Parameter | Type     | Description                                  |
|-----------|----------|----------------------------------------------|
| `type`    | `string` | **Required**. Type of change (email or name) |
| `email`   | `string` | New email address (if type is email)         |
| `name`    | `string` | New name (if type is name)                   |

### Responses
```json
{
  "result": "success",
  "type": "change_{type}"
}
```
<br>

## Get User Information

사용자 정보 조회 API

```http
GET /get_user_info
```

### Responses
```json
{
  "result": "success",
  "type": "user_info",
  "message": {
    "username": "sampleuser",
    "email": "user@example.com",
    "originalname": "username",
    "profile_image": "url_to_profile_image"
  }
}
```
<br>

## Download User Image

사용자 이미지 다운로드 API

```http
GET /download_image/<path:subpath>
```

### Responses
Direct file download response.
</details>
<br><br>



# Face Services
<details>
<summary>접기/펼치기</summary>

## Face Recognition

얼굴 인식 API

```http
POST /face
```

| Parameter   | Type   | Description                        |
|-------------|--------|------------------------------------|
| `face_image`| `file` | **Required**. Image of the user's face |

### Responses
```json
{
  "result": "success",
  "type": "face",
  "face": "identified face name or description",
  "username": "username if identified"
}
```
<br>

## Measure Distance

거리 측정 API

```http
POST /distance
```

| Parameter       | Type   | Description                          |
|-----------------|--------|--------------------------------------|
| `distance_image`| `file` | **Required**. Image for distance calculation |

### Responses
```json
{
  "result": "success",
  "type": "distance",
  "distance": "calculated distance in some unit"
}
```
<br>

## Personal Color Analysis

퍼스널컬러 분석 API

```http
POST /personal_color
```

| Parameter   | Type   | Description                      |
|-------------|--------|----------------------------------|
| `face_image`| `file` | **Required**. Image of the user's face |

### Responses
```json
{
  "result": "success",
  "type": "personal_color",
  "tone": "identified color tone"
}
```
</details>
<br><br>



# Widget Services
<details>
<summary>접기/펼치기</summary>

## List Available Widgets

이용 가능한 위젯 목록 조회 API

```http
GET /widgets
```

### Responses
```json
{
  "result": "success",
  "type": "widget_list",
  "message": {
    "1": "Weather",
    "2": "DateTime",
    "3": "Login",
    "4": "CheerUp"
  }
}
```
<br>

## Set Custom Widget Layout

사용자별 위젯 레이아웃 설정 API

```http
POST /widgets_custom
```

| Parameter  | Type     | Description                                  |
|------------|----------|----------------------------------------------|
| `model_code` | `string`| **Required**. Model code of the user's device|
| `index`    | `json`   | **Required**. JSON string specifying the widget layout |

### Requests Example
```json
{
  "model_code" : "1234-5678",
  "index" : "{'weather' : [1, 1], 'time' : [1, 2]}"
}
```

### Responses
```json
{
  "result": "success",
  "type": "widget_custom",
  "message": "New Widget or Update Widget"
}
```
<br>

## Get Custom Widget Layout

사용자별 위젯 레이아웃 조회 API

```http
GET /get_widgets_custom/<path:subpath>
```

### Responses
```json
{
  "result": "success",
  "type": "get_widgets_custom",
  "message": {
    "Weather": [1, 1],
    "DateTime": [1, 2],
    "Login": [1, 3],
    "CheerUp": [1, 4]
  }
}
```
<br>

## List Users by Model Code

모델 코드별 사용자 목록 조회 API

```http
GET /get_model_user_list
```

| Parameter  | Type     | Description                       |
|------------|----------|-----------------------------------|
| `code`     | `string` | **Required**. Model code to query |

### Responses
```json
{
  "result": "success",
  "type": "model_user_list",
  "message": ["username1", "username2", ...]
}
```
<br>

## Default Widgets for Guest Users

게스트 사용자 기본 위젯 설정 API

```http
GET /widgets_index
```

### Responses
```json
{
  "result": "success",
  "message": {
    "Weather": [1, 1],
    "DateTime": [1, 2],
    "Login": [1, 3],
    "CheerUp": [1, 4]
  }
}
```
</details>
<br><br>

## List Users by Model Code

모델 코드별 사용자 목록 조회 API

```http
GET /get_model_user_list
```

| Parameter  | Type     | Description                       |
|------------|----------|-----------------------------------|
| `code`     | `string` | **Required**. Model code to query |

### Responses
```json
{
  "result": "success",
  "type": "model_user_list",
  "message": ["username1", "username2", ...]
}
```
<br>

# ToDo Widget Services
<details>
<summary>접기/펼치기</summary>

## ToDo Widget for User

사용자 Todo 위젯 설정 API

```http
GET /daily/add
```

| Parameter  | Type     | Description                       |
|------------|----------|-----------------------------------|
| `localdate`| `string` | **Required**. date to query       |
| `message`  | `string` | **Required**. message to query    |


### Responses
```json
{
  "result": "success",
  "type" : "todo_add",
  "message": String
}
```
<br>

## ToDo Widget List for User

사용자 Todo 위젯 리스트 조회 API

```http
GET /daily/view
```

| Parameter  | Type     | Description                       |
|------------|----------|-----------------------------------|
| `username` | `string` | **Required**. username to query   |
| `localdate`| `string` | **Required**. date to query       |


### Responses
```json
{
  "result": "success",
  "type" : "todo_view",
  "message": String
}
```
<br>

## ToDo Widget Monthly Data for User

사용자 Todo 위젯 월별 리스트 조회 API

```http
GET /daily/month
```

| Parameter  | Type     | Description                       |
|------------|----------|-----------------------------------|
| `username` | `string` | **Required**. username to query   |
| `year`     | `Int`    | **Required**. year (ex: 2024)     |
| `month`    | `Int`    | **Required**. month (ex: 06)      |


### Responses
```json
{
  "result": "success",
  "type" : "todo_month",
  "message": String
}
```
<br>

## ToDo Widget Check

사용자 Todo 위젯의 각 요소 체크값 변경 API

```http
GET /daily/check
```

| Parameter  | Type     | Description                       |
|------------|----------|-----------------------------------|
| `id`       | `Int`    | **Required**. Todo Database index id|


### Responses
```json
{
  "result": "success",
  "type" : "todo_check",
  "message": String
}
```
</details>
<br><br>



# Message Services
<details>
<summary>접기/펼치기</summary>

## Send Message

메세지 보내기 API

```http
POST /send_message
```

| Parameter          | Type     | Description                               |
|--------------------|----------|-------------------------------------------|
| `sender_username`  | `string` | **Required**. Username of the sender      |
| `receiver_username`| `string` | **Required**. Username of the receiver    |
| `content`          | `string` | **Required**. Content of the message      |

### Responses
```json
{
  "result": "success",
  "type": "message_sent",
  "message": "Message sent"
}
```
<br>

## Retrieve Messages

메세지 조회 API

```http
GET /get_messages/<receiver_username>
```

| Parameter          | Type     | Description                                 |
|--------------------|----------|---------------------------------------------|
| `receiver_username`| `string` | **Required**. Username of the message receiver |

### Responses
```json
{
  "result": "success",
  "type": "messages_retrieved",
  "messages": [
    {
      "sender": "username of the sender",
      "content": "message content",
      "timestamp": "YYYY-MM-DD HH:MM:SS"
    },
    {
      "sender": "username of the sender",
      "content": "message content",
      "timestamp": "YYYY-MM-DD HH:MM:SS"
    },
    ...
  ]
}
```
<br>

## Retrieve Unread Messages

메세지 조회 API (읽지 않은 메시지)

```http
GET /get_unread_messages/<receiver_username>
```

| Parameter          | Type     | Description                                |
|--------------------|----------|--------------------------------------------|
| `receiver_username`| `string` | **Required**. Username of the message receiver |

### Responses
```json
{
  "result": "success",
  "type": "messages_retrieved",
  "messages": [
    {
      "sender": "username of the sender",
      "content": "message content",
      "timestamp": "YYYY-MM-DD HH:MM:SS"
    },
    ...
  ]
}
```
<br>

## Mark Messages as Read

메세지 읽음 상태로 표시 API

```http
POST /mark_messages_as_read/<receiver_username>
```

| Parameter          | Type     | Description                                |
|--------------------|----------|--------------------------------------------|
| `receiver_username`| `string` | **Required**. Username of the message receiver |

### Responses
```json
{
  "result": "success",
  "type": "messages_marked_as_read",
  "message": "All unread messages marked as read"
}
```
</details>
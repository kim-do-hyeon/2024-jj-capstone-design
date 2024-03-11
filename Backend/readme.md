> API Documentation

## Installation
```
pip3 install -r requirement.txt
```

## Run
```python
python3 run.py
```

## User Login

```http
POST /login
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `username` | `string` | **Required**. Your UserName |
| `password` | `string` | **Required**. Your Password |

## Responses
### Success
```javascript
{
  "result" : "success",
  "type" : "login"
}
```
### Fail
```javascript
{
  "result" : "fail",
  "type" : "login",
  "message" : "Please check your username or password"
}
```

## User Register

```http
POST /register/user
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `username` | `string` | **Required**. Your UserName |
| `password` | `string` | **Required**. Your Password |
| `email` | `string` | **Required**. Your Email |

## Responses
### Success
```javascript
{
  "result" : "success",
  "type" : "register_user"
}
```
### Fail (Exist User)
```javascript
{
  "result" : "fail",
  "message" : "exist user"
}
```

## Reset Password

```http
POST /reset_password
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `username` | `string` | **Required**. Your UserName |
| `email` | `string` | **Required**. Your Email |

## Responses
### Success
```javascript
{
  "result" : "success",
  "type" : "reset_password",
  "message" : string
}
```
### Fail
```javascript
{
  "result" : "fail",
  "type" : "reset_password",
  "message" : "Not Found User"
}
```


## Face Register

```http
POST /register/face
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `username` | `string` | **Required**. Your UserName |
| `face_image` | `file` | **Required**. Your Face Image |

## Responses
### Success
```javascript
{
  "result" : "success",
  "type" : "register_face"
}
```
### Fail (Image Save Error)
```javascript
{
  "result" : "fail",
  "type" : "save_image",
  "message" : string
}
```
## Face Recognize

```http
GET /face
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `face_image` | `file` | **Required**. Your Face Image |

## Responses
### Success
```javascript
{
  "result" : string,
  "type" : "face",
  "face" : string
}
```
### Fail (Image Save Error)
```javascript
{
  "result" : string,
  "type" : "save_image",
  "message" : string
}
```
## Distance Recognize

```http
GET /distance
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `distance_image` | `file` | **Required**. Your Image File |

## Responses
### Success
```javascript
{
  "result" : string,
  "type" : "distance",
  "distance" : float
}
```
### Fail
```javascript
{
  "result" : string,
  "type" : "distance",
  "distance" : 0
}
```
### Fail (Image Save Error)
```javascript
{
  "result" : "fail",
  "type" : "save_image",
  "message" : string
}
```

## Personal Color Analysis

```http
GET /personal_color
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `face_image` | `file` | **Required**. Your Image File |

## Responses
### Success
```javascript
{
  "result" : string,
  "type" : "personal_color",
  "tone" : string
}
```
### Fail (Image Save Error)
```javascript
{
  "result" : "fail",
  "type" : "save_image",
  "message" : string
}
```
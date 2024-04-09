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
```javascript
{
  "result" : "success",
  "type" : "login"
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

```javascript
{
  "result" : "success",
  "type" : "register_user"
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
```javascript
{
  "result" : "success",
  "type" : "reset_password",
  "message" : string
}
```

## Face Register

```http
POST /register/face
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `displayname` | `string` | **Required**. Your Display Name (Real Name) |
| `face_image` | `file` | **Required**. Your Face Image |

## Responses
```javascript
{
  "result" : "success",
  "type" : "register_face"
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
```javascript
{
  "result" : string,
  "type" : "face",
  "face" : string
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
```javascript
{
  "result" : string,
  "type" : "distance",
  "distance" : float
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
```javascript
{
  "result" : string,
  "type" : "personal_color",
  "tone" : string
}
```

## Setting User Custom Widget Index

```http
POST /widgets_custom
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `model_code` | `string` | **Required**. Your Machine Model Code |
| `index` | `string` | **Required**. Your Widget Location (Json) |

## Requests Example
```javascript
{
  "model_code" : "1234-5678",
  "index" : "{'weather' : [1, 1], 'time' : [1, 2]}"
}
```
## Responses
```javascript
{
  "result" : string,
  "type" : "widget_custom",
  "message" : string("New Widget" or "Update Widget")
}
```

## Get User Custom Widget Index
```http
GET /get_widgets_custom
```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `model_code` | `string` | **Required**. Your Machine Model Code |

## Responses
```javascript
{
  "result" : string,
  "type" : "get_widget_custom",
  "message" : string
}
```

## Send Message
```http
POST /send_message
```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `sender_username` | `string` | **Required**. Username of the sender |
| `receiver_username` | `string` | **Required**. Username of the receiver |
| `content` | `string` | **Required**. Content of the message |

## Responses
```javascript
{
  "result": "success",
  "type": "message_sent",
  "message": "Message sent"
}
```

## Get Messages
```http
GET /get_messages/<receiver_username>
```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `receiver_username` | `string` | **Required**. Username of the receiver |

##### Responses
```javascript
{
  "result": "success",
  "type": "messages_retrieved",
  "messages": [
    {
      "sender": "sender_username",
      "content": "message_content",
      "timestamp": "YYYY-MM-DD HH:MM:SS"
    },
    {
      "sender": "sender_username",
      "content": "message_content",
      "timestamp": "YYYY-MM-DD HH:MM:SS"
    },
    ...
  ]
}
```
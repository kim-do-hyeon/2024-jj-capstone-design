> API Documentation

## Installation
```
pip3 install -r requirement.txt
```

## Run
```python
python3 run.py
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
  "result" : string
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

```javascript
{
  "result" : string
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
  "face" : string,
  "result" : string
}
```
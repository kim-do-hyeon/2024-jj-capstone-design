# -*- encoding: utf-8 -*-
from flask_login import UserMixin
from apps import db, login_manager
from sqlalchemy import PickleType
from apps.authentication.util import hash_pass
from datetime import datetime, timezone

class Users(db.Model, UserMixin):
    __tablename__ = 'Users'
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(64), unique=True)
    email = db.Column(db.String(64), unique=True)
    password = db.Column(db.LargeBinary)
    admin = db.Column(db.Integer, default = 0)
    def __init__(self, **kwargs):
        for property, value in kwargs.items():
            if hasattr(value, '__iter__') and not isinstance(value, str):
                value = value[0]
            if property == 'password':
                value = hash_pass(value)
            setattr(self, property, value)
    def __repr__(self):
        return str(self.username)

class Faces(db.Model, UserMixin) :
    __tablename__ = 'Faces'
    id = db.Column(db.Integer, primary_key = True)
    username = db.Column(db.String(64))
    face = db.Column(PickleType)

class Production(db.Model, UserMixin) :
    __tablename__ = 'production'
    id = db.Column(db.Integer, primary_key = True)
    username = db.Column(db.String(64))
    code = db.Column(db.String(64))

class Widget(db.Model, UserMixin) :
    __tablename__ = 'widget'
    id = db.Column(db.Integer, primary_key = True)
    widget_name = db.Column(db.String)

class AuthenticationAPI(db.Model, UserMixin) :
    __tablename__ = 'authenticationAPI'
    id = db.Column(db.Integer, primary_key = True)
    model_code = db.Column(db.String(64))
    api_key = db.Column(db.String)

class CustomLocation(db.Model, UserMixin) :
    __tablename__ = 'customlocation'
    id = db.Column(db.Integer, primary_key = True)
    username = db.Column(db.String(64))
    model_code = db.Column(db.String(64))
    index = db.Column(db.String)

class Message(db.Model):
    __tablename__ = 'messages'
    id = db.Column(db.Integer, primary_key=True)
    sender_id = db.Column(db.Integer, db.ForeignKey('Users.id'), name='sender_id')
    receiver_id = db.Column(db.Integer, db.ForeignKey('Users.id'), name='receiver_id')
    content = db.Column(db.String(500), name='content')
    timestamp = db.Column(db.DateTime, default=datetime.now(timezone.utc))
    is_read = db.Column(db.Boolean, default=False, name='is_read')
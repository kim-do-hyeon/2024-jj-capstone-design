# -*- encoding: utf-8 -*-
from flask_login import UserMixin
from apps import db, login_manager
from sqlalchemy import PickleType
from apps.authentication.util import hash_pass

class Users(db.Model, UserMixin):
    __tablename__ = 'Users'
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(64), unique=True)
    email = db.Column(db.String(64), unique=True)
    password = db.Column(db.LargeBinary)
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
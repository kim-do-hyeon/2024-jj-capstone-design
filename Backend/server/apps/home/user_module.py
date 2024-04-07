# -*- encoding: utf-8 -*-
import os
from flask import request, jsonify, session
from werkzeug.utils import secure_filename

from apps.authentication.util import verify_pass, hash_pass, get_random_string
from apps.home.face_module import train_face

''' Import DB '''
from apps import db
from apps.authentication.models import Users, Faces, Production


def register_module(path_type) :
    if path_type[0] == "user" :
        '''
            username <- required
            password <- required
            email <- required
        '''
        data = request.args.to_dict()
        username = data['username']
        password = data['password']
        email = data['email']

        ''' Exist User Check '''
        isUser = Users.query.filter_by(username = username).first()
        if isUser :
            return jsonify(result = "fail", message = "exist user")
        else :
            new_user = Users(username = username,
                              password = password,
                              email = email)
            db.session.add(new_user)
            db.session.commit()
            return jsonify(result = "success", type = "register_user")
    elif path_type[0] == "face" :
        '''
            username <- required
            face_model <- required
        '''
        displayname = request.form['displayname']
        username = session['username']
        face_model = request.files['face_image']
        upload_dir = "upload/user/" + username + "/"
    
        if not os.path.exists(upload_dir):
            os.makedirs(upload_dir)
    
        filename = secure_filename(face_model.filename)
        file_path = os.path.join(upload_dir, filename)
        try:
            face_model.save(file_path)
        except Exception as e:
            return jsonify(result = "fail", type = "save_image", message = str(e))
        
        known_face_encodings = train_face(username, file_path)

        new_face_data = Faces(username = username, displayname = displayname, face = known_face_encodings)
        db.session.add(new_face_data)
        db.session.commit()

        return jsonify(result = "success", type = "register_face")
    elif path_type[0] == "product" :
        if session['isLogin'] :
            username = session['username']
            try :
                data = request.args.to_dict()
                if data == {} :
                    return jsonify(result = "fail", type = "register_productiion", message = "Key Error")
                code = data['code']
                if code == "" :
                    return jsonify(result = "fail", type = "register_productiion", message = "Args is Blank")
                new_production = Production(username = username, code = code)
                db.session.add(new_production)
                db.session.commit()
            except :
                return jsonify(result = "fail", type = "register_productiion", message = "DB Error")
            return jsonify(result = "success", type = "register_productiion")
        else :
            return jsonify(result = "fail", type = "register_productiion", message = "Not logined")
        
def login_module() :
    data = request.args.to_dict()
    username = data['username']
    password = data['password']
    isUser = Users.query.filter_by(username = username).first()
    if isUser and verify_pass(password, isUser.password) :
        session['isLogin'] = True
        session['username'] = username
        cookie = (request.headers.get('Cookie'))
        return jsonify(result = "success", type = "login", message = cookie)
    else :
        return jsonify(result = "fail", type = "login", message = "Please check your username or password")

def reset_password_module() :
    data = request.args.to_dict()
    username = data['username']
    email = data['email']
    isUser = Users.query.filter_by(username = username, email = email).first()
    if isUser :
        new_password = get_random_string(8)
        Users.query.filter_by(username = username, email = email).update((dict(password = hash_pass(new_password))))
        db.session.commit()
        return jsonify(result = "success", type = "reset_password", message = str(new_password))
    else :
        return jsonify(result = "fail", type = "reset_password", message = "Not Found User")

def change_password_module() :
    data = request.args.to_dict()
    current_password = data['current_password']
    new_password = data['new_password']
    isUser = Users.query.filter_by(username = session['username']).first()
    if isUser and verify_pass(current_password, isUser.password):
        Users.query.filter_by(username = session['username']).update((dict(password = hash_pass(new_password))))
        db.session.commit()
        return jsonify(result = "success", type = "change_password")
    else :
        return jsonify(result = "fail", type = "reset_password", message = "Check Your Password")



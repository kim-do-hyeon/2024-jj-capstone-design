# -*- encoding: utf-8 -*-
import os, string, secrets
from flask import request, jsonify, session
from werkzeug.utils import secure_filename
from datetime import datetime
from apps.authentication.util import verify_pass, hash_pass, get_random_string
from apps.home.face_module import train_face

''' Import DB '''
from apps import db
from apps.authentication.models import Users, Faces, Production

def generate_random_string(length=16):
    # 알파벳 대소문자와 숫자를 포함
    characters = string.ascii_letters + string.digits
    # 주어진 길이의 랜덤 문자열 생성
    random_string = ''.join(secrets.choice(characters) for i in range(length))
    return random_string

def register_module(path_type) :
    if path_type[0] == "user" :
        '''
            username <- required
            password <- required
            email <- required
            originalname <- required
        '''
        data = request.args.to_dict()
        username = data['username']
        password = data['password']
        originalname = data['originalname']
        email = data['email']

        ''' Exist User Check '''
        isUser = Users.query.filter_by(username = username).first()
        if isUser :
            return jsonify(result = "fail", message = "exist user")
        else :
            isEmail = Users.query.filter_by(email = email).first()
            if isEmail :
                return jsonify(reulst = "fail", message = "exist email")
            else :
                new_user = Users(username = username,
                                password = password,
                                email = email,
                                originalname = originalname)
                db.session.add(new_user)
                db.session.commit()
                return jsonify(result = "success", type = "register_user")
    elif path_type[0] == "face" :
        '''
            username <- required
            face_model <- required
        '''
        username = session['username']
        displayname = Users.query.filter_by(username = username).first().originalname
        face_model = request.files['face_image']
        upload_dir = "upload/user/" + username + "/"

        if not os.path.exists(upload_dir):
            os.makedirs(upload_dir)
    
        filename = generate_random_string() + "." + (face_model.filename).split(".")[-1]
        if len(filename.split(".")) == 1 :
            filename = username + ".png"
            
        file_path = os.path.join(upload_dir, filename)
        try:
            face_model.save(file_path)
        except Exception as e:
            return jsonify(result = "fail", type = "save_image", message = str(e))
        
        try :
            known_face_encodings = train_face(username, file_path)
        except Exception as e:
            print(e)
            return jsonify(result = "fail", type = "train_image", message = "Train Face Error : " + str(e))

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
    elif path_type[0] == "profile" :
        if session['isLogin'] :
            profile_image = request.files['profile_image']
            upload_dir = "upload/profile_image/"
            try : 
                username = session['username']
            except :
                return jsonify(result = "fail", type = "profile image", message = "Not Loggined")
            
            if not os.path.exists(upload_dir):
                os.makedirs(upload_dir)

            filename = secure_filename(profile_image.filename)
            current_time = (datetime.now().strftime("%Y%m%d%H%M%S"))
            filename = str(username) + "_" + current_time + "." + filename.split(".")[-1]
            
            file_path = os.path.join(upload_dir, filename)
            try:
                profile_image.save(file_path)
                upload_dir = "upload/development_image/"
                profile_image_url = "https://jj.system32.kr/download_image/" + filename
                Users.query.filter_by(username = session['username']).update((dict(profile_image = profile_image_url)))
                db.session.commit()
            except Exception as e:
                return jsonify(result = "fail", type = "profile_image", message = str(e))
            return jsonify(result = "success", type = "profile_image", message = "")

        else :
            return jsonify(result = "fail", type = "profile", message = "Not logined")
        
        
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

def chagne_profile_module() :
    data = request.args.to_dict()
    print(data)
    type = data['type']
    if type == 'email' :
        try :
            new_email = data['email']
            Users.query.filter_by(username = session['username']).update((dict(email = new_email)))
            db.session.commit()
            return jsonify(result = "success", type = "change_email")
        except Exception as e:
            return jsonify(result = "fail", type = "change_email", message = str(e))
    elif type == 'name' :
        try :
            new_name = data['name']
            Users.query.filter_by(username = session['username']).update((dict(originalname = new_name)))
            db.session.commit()
            return jsonify(result = "success", type = "chage_originalname")
        except Exception as e :
            return jsonify(result = "fail", type = "change_originalname", message = str(e))

def get_user_info_module() :
    if session['isLogin'] :
        data = Users.query.filter_by(username = session['username']).first()
        username = data.username
        email = data.email
        originalname = data.originalname
        profile_image = data.profile_image
        data_dict = {'username' : username, 'email' : email, 'originalname' : originalname, 'profile_image' : profile_image}
        return jsonify(result = "success", type = "user_info", message = data_dict)
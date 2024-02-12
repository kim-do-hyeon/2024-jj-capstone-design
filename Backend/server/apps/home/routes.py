# -*- encoding: utf-8 -*-
import os
from flask import request, jsonify
from werkzeug.utils import secure_filename

''' Import Apps Module '''
from apps.home import blueprint
from apps.home.face_module import train_face, predict_face

''' Import DB '''
from apps import db
from apps.authentication.models import Users, Faces

@blueprint.route('/index')
def index():
    return ""

@blueprint.route('/register/<path:subpath>', methods = ['GET', 'POST'])
def register(subpath) :
    path_type = subpath.split("/")
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
            return jsonify(result = "success")
    elif path_type[0] == "face" :
        '''
            username <- required
            face_model <- required
        '''
        username = request.form['username']
        face_model = request.files['face_image']
        upload_dir = "upload/user/" + username + "/"
    
        if not os.path.exists(upload_dir):
            os.makedirs(upload_dir)
    
        filename = secure_filename(face_model.filename)
        file_path = os.path.join(upload_dir, filename)
        try:
            face_model.save(file_path)
        except Exception as e:
            return jsonify(result = "fail", message = str(e))
        known_face_encodings = train_face(username, file_path)
        new_face_data = Faces(username = username, face = known_face_encodings)
        db.session.add(new_face_data)
        db.session.commit()
        return jsonify(result = "success")
    
@blueprint.route('/face', methods = ['GET', 'POST'])
def face() :
    face_image = request.files['face_image']
    upload_dir = "upload/predict/"
    if not os.path.exists(upload_dir):
        os.makedirs(upload_dir)
    filename = secure_filename(face_image.filename)
    file_path = os.path.join(upload_dir, filename)
    try:
        face_image.save(file_path)
    except Exception as e:
        return jsonify(result = "faile", message = str(e))
    result = (predict_face(file_path))
    return jsonify(result = "success", face = str(result))
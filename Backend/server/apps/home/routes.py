# -*- encoding: utf-8 -*-
from flask import request, jsonify

''' Import Apps Module '''
from apps.home import blueprint

''' Import DB '''
from apps import db
from apps.authentication.models import Users

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
        data = request.args.to_dict()
        username = data['username']
        face_model = data['face_model']
        



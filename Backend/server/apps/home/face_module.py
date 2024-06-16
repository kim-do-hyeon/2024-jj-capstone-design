# -*- encoding: utf-8 -*-
from flask import request, jsonify
from werkzeug.utils import secure_filename
from apps.home.personal_color_moudle import analysis

''' Import DB '''
from apps import db
from apps.authentication.models import Users, Faces

import os
from datetime import datetime
from apps.home.face_detect_util import *

def train_face(username, image) :
    try :
        known_face_encodings = []
        known_face_names = []
        add_known_face(image, username, known_face_encodings, known_face_names)
        return (known_face_encodings[0])
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def predict_face(image) :
    try :
        predict_image = cv2.imread(image)
        known_face_names = [n.displayname for n in Faces.query.all()]
        known_face_encodings = [n.face for n in Faces.query.all()]
        try :
            return (name_labeling(predict_image, known_face_encodings, known_face_names))
        except :
            pass
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def redirect_face_module() :
    try :
        face_image = request.files['face_image']
        upload_dir = "upload/predict/"
        
        if not os.path.exists(upload_dir):
            os.makedirs(upload_dir)
        now = datetime.now().strftime('%Y%m%d%H%M%S%f')

        filename = now + "." + (face_image.filename).split(".")[-1]
        if len(filename.split(".")) == 1 :
            filename = "noname." + filename.split(".")[-1]
        file_path = os.path.join(upload_dir, filename)
        try:
            face_image.save(file_path)
        except Exception as e:
            return jsonify(result = "fail", type = "save_image", message = str(e))
        
        result = (predict_face(file_path))
        try :
            username = Faces.query.filter_by(displayname = result).first()
            username = (username.username)
        except :
            username = ""
        return jsonify(result = "success", type = "face", face = str(result), username = username)
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def distance_module() :
    try :
        focal_length = 950
        known_width = 18.0
        face_width_pixels = 200

        def distance_to_camera(known_width, focal_length, face_width):
            return (known_width * focal_length) / face_width
        
        distance_image = request.files['distance_image']
        upload_dir = "upload/distance/"
        
        if not os.path.exists(upload_dir):
            os.makedirs(upload_dir)
        filename = secure_filename(distance_image.filename)

        if len(filename.split(".")) == 1 :
            filename = "noname." + filename.split(".")[-1]

        file_path = os.path.join(upload_dir, filename)

        try:
            distance_image.save(file_path)
        except Exception as e:
            return jsonify(result = "fail", type = "save_image", message = str(e))
        
        face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
        distance_image = cv2.imread(file_path)
        gray = cv2.cvtColor(distance_image, cv2.COLOR_BGR2GRAY)
        faces = face_cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))
        result_distance = 0
        for (x, y, w, h) in faces:
            result_distance = distance_to_camera(known_width, focal_length, w)

        if result_distance == 0 :
            return jsonify(result = "fail", type = "distance", distance = 0)
        else :
            return jsonify(result = "success", type = "distance", distance = result_distance)
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def personal_color_module() :
    try :
        face_image = request.files['face_image']
        upload_dir = "upload/personal/"
        
        if not os.path.exists(upload_dir):
            os.makedirs(upload_dir)
        now = datetime.now().strftime('%Y%m%d%H%M%S%f')
        filename = secure_filename(face_image.filename)

        filename = now + "." + (face_image.filename).split(".")[-1]
        if len(filename.split(".")) == 1 :
            filename = "noname." + filename.split(".")[-1]

        file_path = os.path.join(upload_dir, filename)

        try:
            face_image.save(file_path)
        except Exception as e:
            return jsonify(result = "fail", type = "save_image", message = str(e))
        
        tone = analysis(file_path)
        return jsonify(result = "success", type = "perosnal_color", tone = tone)
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

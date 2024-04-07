
''' Import DB '''
from apps import db
from apps.authentication.models import Users, Faces

import os
from apps.home.face_detect_util import *

def train_face(username, image) :
    known_face_encodings = []
    known_face_names = []
    add_known_face(image, username, known_face_encodings, known_face_names)
    return (known_face_encodings[0])

def predict_face(image) :
    predict_image = cv2.imread(image)
    known_face_names = [n.username for n in Faces.query.all()]
    known_face_encodings = [n.face for n in Faces.query.all()]
    try :
        return (name_labeling(predict_image, known_face_encodings, known_face_names))
    except :
        pass


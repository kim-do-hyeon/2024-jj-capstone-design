import os
import pickle
from face_detect_util import *

with open(file='known_face_encodings.pickle', mode='rb') as f:
    known_face_encodings = pickle.load(f)
with open(file='known_face_names.pickle', mode='rb') as f:
    known_face_names = pickle.load(f)

print("TRAIN IMAGE COUNT : {}".format(len(known_face_encodings)))

''' Predict '''
FACE_TEST_IMAGE = "Backend/Face_Detect/test_image/"

face_image_user_list = os.listdir(FACE_TEST_IMAGE)
for i in face_image_user_list :
    try :
        predict_image_path = 'Backend/Face_Detect/test_image/' + i
        predict_image = cv2.imread(predict_image_path)
        print("[{}] - {}".format(predict_image_path, name_labeling(predict_image, known_face_encodings, known_face_names)))
    except :
        pass
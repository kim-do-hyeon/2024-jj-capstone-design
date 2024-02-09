import os
import pickle
from face_detect_util import *


if "known_face_encodings.pickle" in os.listdir() :
    with open(file='known_face_encodings.pickle', mode='rb') as f:
        known_face_encodings = pickle.load(f)
else : known_face_encodings = [] 

if "known_face_names.pickle" in os.listdir() :
    with open(file='known_face_names.pickle', mode='rb') as f:
        known_face_names = pickle.load(f)
else : known_face_names = [] 

FACE_TRAIN_IMAGE = "Backend/Face_Detect/train_image/user1/"
file_list = os.listdir(FACE_TRAIN_IMAGE)

for i in file_list :
    try :
        add_known_face("Backend/Face_Detect/train_image/user1/" + i, "User1", known_face_encodings, known_face_names)
    except Exception as e :
        print(e)

with open(file='known_face_encodings.pickle', mode='wb') as f:
    pickle.dump(known_face_encodings, f)

with open(file='known_face_names.pickle', mode='wb') as f:
    pickle.dump(known_face_names, f)

print("TRAIN IMAGE COUNT : {}".format(len(known_face_encodings)))

''' Predict '''
predict_image_path = 'Backend/Face_Detect/test_image/user1_test.jpg'
predict_image = cv2.imread(predict_image_path)
print(name_labeling(predict_image, known_face_encodings, known_face_names))
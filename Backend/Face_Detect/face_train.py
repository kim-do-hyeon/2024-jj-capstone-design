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


''' Select Train User Image '''
FACE_TRAIN_IMAGE = "Backend/Face_Detect/train_image/"

face_image_user_list = os.listdir(FACE_TRAIN_IMAGE)
print(">>>> Select Train User Image")
for i in range(len(face_image_user_list)) :
    print("[{}] {}".format(i + 1, face_image_user_list[i]))
username = face_image_user_list[int(input(">> ")) - 1]
''' End Train User Image '''

file_list = os.listdir(FACE_TRAIN_IMAGE + "/" + username)
train_path = FACE_TRAIN_IMAGE + "/" + username
for i in file_list :
    try :
        add_known_face(train_path + "/" + i, username, known_face_encodings, known_face_names)
    except Exception as e :
        print(e)

with open(file='known_face_encodings.pickle', mode='wb') as f:
    pickle.dump(known_face_encodings, f)

with open(file='known_face_names.pickle', mode='wb') as f:
    pickle.dump(known_face_names, f)

print("TRAIN IMAGE COUNT : {}".format(len(known_face_encodings)))
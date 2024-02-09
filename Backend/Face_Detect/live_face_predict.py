import os
import cv2
import pickle
from face_detect_util import *

with open(file='known_face_encodings.pickle', mode='rb') as f:
    known_face_encodings = pickle.load(f)
with open(file='known_face_names.pickle', mode='rb') as f:
    known_face_names = pickle.load(f)

print("TRAIN IMAGE COUNT : {}".format(len(known_face_encodings)))

cap= cv2.VideoCapture(0)
if cap.isOpened():
    while True:
        ret,frame = cap.read()
        if ret:
            try :
                cv2.imwrite('live_photo.jpg', frame)
                predict_image = cv2.imread('live_photo.jpg')
                print("{}".format(name_labeling(predict_image, known_face_encodings, known_face_names)))
            except :
                print("Unknown")
                pass
        else:
            print('no frame')
            break
else:
    print('no camera!')
cap.release()
cv2.destroyAllWindows()
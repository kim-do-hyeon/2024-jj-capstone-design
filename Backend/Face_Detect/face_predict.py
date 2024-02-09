import pickle
from face_detect_util import *


with open(file='known_face_encodings.pickle', mode='rb') as f:
    known_face_encodings = pickle.load(f)
with open(file='known_face_names.pickle', mode='rb') as f:
    known_face_names = pickle.load(f)

print("TRAIN IMAGE COUNT : {}".format(len(known_face_encodings)))

''' Predict '''
predict_image_path = 'Backend/Face_Detect/test_image/user1_test_1.jpg'
predict_image = cv2.imread(predict_image_path)
print(name_labeling(predict_image, known_face_encodings, known_face_names))
import os
import cv2
import numpy as np
from imutils import face_utils
import dlib
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
from itertools import compress
from colormath.color_objects import LabColor, sRGBColor, HSVColor
from colormath.color_conversions import convert_color

class DominantColors:

    CLUSTERS = None
    IMAGE = None
    COLORS = None
    LABELS = None

    def __init__(self, image, clusters=3):
        self.CLUSTERS = clusters
        img = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        self.IMAGE = img.reshape((img.shape[0] * img.shape[1], 3))
        kmeans = KMeans(n_clusters = self.CLUSTERS)
        kmeans.fit(self.IMAGE)
        self.COLORS = kmeans.cluster_centers_
        self.LABELS = kmeans.labels_

    def rgb_to_hex(self, rgb):
        return '#%02x%02x%02x' % (int(rgb[0]), int(rgb[1]), int(rgb[2]))
    def getHistogram(self):
        numLabels = np.arange(0, self.CLUSTERS+1)
        (hist, _) = np.histogram(self.LABELS, bins = numLabels)
        hist = hist.astype("float")
        hist /= hist.sum()

        colors = self.COLORS
        colors = colors[(-hist).argsort()]
        hist = hist[(-hist).argsort()]
        for i in range(self.CLUSTERS):
            colors[i] = colors[i].astype(int)
        fil = [colors[i][2] < 250 and colors[i][0] > 10 for i in range(self.CLUSTERS)]
        colors = list(compress(colors, fil))
        return colors, hist

    def plotHistogram(self):
        colors, hist = self.getHistogram()
        chart = np.zeros((50, 500, 3), np.uint8)
        start = 0
        for i in range(len(colors)):
            end = start + hist[i] * 500
            r,g,b = colors[i]
            cv2.rectangle(chart, (int(start), 0), (int(end), 50), (r,g,b), -1)
            start = end
        plt.figure()
        plt.axis("off")
        plt.imshow(chart)
        plt.show()

        return colors


class DetectFace:
    def __init__(self, image):
        self.detector = dlib.get_frontal_face_detector()
        self.predictor = dlib.shape_predictor(os.getcwd() + "/res/shape_predictor_68_face_landmarks.dat")
        self.img = cv2.imread(image)
        self.right_eyebrow = []
        self.left_eyebrow = []
        self.right_eye = []
        self.left_eye = []
        self.left_cheek = []
        self.right_cheek = []
        self.detect_face_part()

    def detect_face_part(self):
        face_parts = [[],[],[],[],[],[],[]]
        rect = self.detector(cv2.cvtColor(self.img, cv2.COLOR_BGR2GRAY), 1)[0]
        shape = self.predictor(cv2.cvtColor(self.img, cv2.COLOR_BGR2GRAY), rect)
        shape = face_utils.shape_to_np(shape)
        idx = 0
        for name, (i, j) in face_utils.FACIAL_LANDMARKS_IDXS.items():
            if idx < len(face_parts):
                face_parts[idx] = shape[i:j]
                idx += 1
            else:
                pass
        face_parts = face_parts[1:5]
        self.right_eyebrow = self.extract_face_part(face_parts[0])
        self.left_eyebrow = self.extract_face_part(face_parts[1])
        self.right_eye = self.extract_face_part(face_parts[2])
        self.left_eye = self.extract_face_part(face_parts[3])
        self.left_cheek = self.img[shape[29][1]:shape[33][1], shape[4][0]:shape[48][0]]
        self.right_cheek = self.img[shape[29][1]:shape[33][1], shape[54][0]:shape[12][0]]

    def extract_face_part(self, face_part_points):
        (x, y, w, h) = cv2.boundingRect(face_part_points)
        crop = self.img[y:y+h, x:x+w]
        adj_points = np.array([np.array([p[0]-x, p[1]-y]) for p in face_part_points])

        mask = np.zeros((crop.shape[0], crop.shape[1]))
        cv2.fillConvexPoly(mask, adj_points, 1)
        mask = mask.astype(bool)
        crop[np.logical_not(mask)] = [255, 0, 0]

        return crop

def is_warm(lab_b, a):
    warm_b_std = [11.6518, 11.71445, 3.6484]
    cool_b_std = [4.64255, 4.86635, 0.18735]

    warm_dist = 0
    cool_dist = 0
    for i in range(3):
        warm_dist += abs(lab_b[i] - warm_b_std[i]) * a[i]
        cool_dist += abs(lab_b[i] - cool_b_std[i]) * a[i]
    if(warm_dist <= cool_dist):
        return 1 #warm
    else:
        return 0 #cool

def is_spr(hsv_s, a):
    spr_s_std = [18.59296, 30.30303, 25.80645]
    fal_s_std = [27.13987, 39.75155, 37.5]

    spr_dist = 0
    fal_dist = 0

    for i in range(3):
        spr_dist += abs(hsv_s[i] - spr_s_std[i]) * a[i]
        fal_dist += abs(hsv_s[i] - fal_s_std[i]) * a[i]

    if(spr_dist <= fal_dist):
        return 1 #spring
    else:
        return 0 #fall

def is_smr(hsv_s, a):
    smr_s_std = [12.5, 21.7195, 24.77064]
    wnt_s_std = [16.73913, 24.8276, 31.3726]
    a[1] = 0.5
    smr_dist = 0
    wnt_dist = 0
    for i in range(3):
        smr_dist += abs(hsv_s[i] - smr_s_std[i]) * a[i]
        wnt_dist += abs(hsv_s[i] - wnt_s_std[i]) * a[i]

    if(smr_dist <= wnt_dist):
        return 1 #summer
    else:
        return 0 #winter


def analysis(imgpath):
    df = DetectFace(imgpath)
    face = [df.left_cheek, df.right_cheek,
            df.left_eyebrow, df.right_eyebrow,
            df.left_eye, df.right_eye]

    temp = []
    clusters = 4
    for f in face:
        dc = DominantColors(f, clusters)
        face_part_color, _ = dc.getHistogram()
        temp.append(np.array(face_part_color[0]))
    cheek = np.mean([temp[0], temp[1]], axis=0)
    eyebrow = np.mean([temp[2], temp[3]], axis=0)
    eye = np.mean([temp[4], temp[5]], axis=0)

    Lab_b, hsv_s = [], []
    color = [cheek, eyebrow, eye]
    for i in range(3):
        rgb = sRGBColor(color[i][0], color[i][1], color[i][2], is_upscaled=True)
        lab = convert_color(rgb, LabColor, through_rgb_type=sRGBColor)
        hsv = convert_color(rgb, HSVColor, through_rgb_type=sRGBColor)
        Lab_b.append(float(format(lab.lab_b,".2f")))
        hsv_s.append(float(format(hsv.hsv_s,".2f"))*100)

    # print('Lab_b[skin, eyebrow, eye]',Lab_b)
    # print('hsv_s[skin, eyebrow, eye]',hsv_s)

    Lab_weight = [30, 20, 5]
    hsv_weight = [10, 1, 1]
    if(is_warm(Lab_b, Lab_weight)):
        if(is_spr(hsv_s, hsv_weight)):
            tone = '봄 웜톤'
        else:
            tone = '가을 웜톤'
    else:
        if(is_smr(hsv_s, hsv_weight)):
            tone = '여름 쿨톤'
        else:
            tone = '겨울 쿨톤'
    return tone

import cv2
import mediapipe as mp
import time
import numpy as np

mp_drawing = mp.solutions.drawing_utils
mp_pose = mp.solutions.pose

display_width = 1280
display_height = 720
font_scale = 0.5
font_thickness = 1

pose = mp_pose.Pose(static_image_mode=False, model_complexity=1, smooth_landmarks=True, min_detection_confidence=0.5, min_tracking_confidence=0.5)
cap = cv2.VideoCapture(0)
cap.set(cv2.CAP_PROP_FRAME_WIDTH, display_width)
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, display_height)

prev_time = time.time()
interface_active = False
last_interface_change_time = 0  # 마지막 인터페이스 상태 변경 시간

def calculate_distance(point1, point2):
    """두 포인트 사이의 거리를 계산합니다."""
    return np.sqrt((point1.x - point2.x) ** 2 + (point1.y - point2.y) ** 2)

def gesture_logic(landmarks, current_time):
    global interface_active, last_interface_change_time

    left_index = landmarks[mp_pose.PoseLandmark.LEFT_INDEX.value]
    right_index = landmarks[mp_pose.PoseLandmark.RIGHT_INDEX.value]
    left_shoulder = landmarks[mp_pose.PoseLandmark.LEFT_SHOULDER.value]
    right_shoulder = landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value]

    distance_between_indices = calculate_distance(left_index, right_index)

    # 인터페이스 상태 변경 후 3초간은 상태 변경 금지
    if current_time - last_interface_change_time < 3:
        return

    # 인터페이스 활성화/비활성화 조건
    if distance_between_indices > 0.2 and left_index.y < left_shoulder.y and right_index.y < right_shoulder.y:
        if not interface_active:
            interface_active = True
            last_interface_change_time = current_time
            print("Interface Activated")
    elif distance_between_indices < 0.1 and left_index.y < left_shoulder.y and right_index.y < right_shoulder.y:
        if interface_active:
            interface_active = False
            last_interface_change_time = current_time
            print("Interface Deactivated")

while cap.isOpened():
    success, image = cap.read()
    if not success:
        print("Ignoring empty camera frame.")
        continue

    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    image.flags.writeable = False
    results = pose.process(image)

    image.flags.writeable = True
    image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)

    current_time = time.time()

    if results.pose_landmarks:
        mp_drawing.draw_landmarks(image, results.pose_landmarks, mp_pose.POSE_CONNECTIONS)
        gesture_logic(results.pose_landmarks.landmark, current_time)

    fps = 1 / (current_time - prev_time)
    prev_time = current_time
    cv2.putText(image, f'FPS: {int(fps)}', (20, 70), cv2.FONT_HERSHEY_SIMPLEX, font_scale, (0, 255, 0), font_thickness)

    cv2.imshow('MediaPipe Pose', image)
    if cv2.waitKey(5) & 0xFF == 27:
        break

cap.release()
cv2.destroyAllWindows()
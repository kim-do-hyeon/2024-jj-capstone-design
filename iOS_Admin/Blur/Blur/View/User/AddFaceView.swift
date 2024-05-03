import SwiftUI
import AVFoundation

struct AddFaceView: View {
    @State private var images: [UIImage?] = Array(repeating: nil, count: 4)
    @State private var isShowingImagePicker = false
    @State private var selectedImageIndex = 0
    @State private var isUploading = false

    var body: some View {
        VStack {
            HStack {
                imageButton(forIndex: 0)
                imageButton(forIndex: 1)
            }
            HStack {
                imageButton(forIndex: 2)
                imageButton(forIndex: 3)
            }
            
            Button(action: {
                self.uploadImages()
            }) {
                Text("전송하기")
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(8)
                    .padding(.top, 20)
                    .opacity(isUploading ? 0.5 : 1.0)
                    .disabled(isUploading)
            }
        }
        .sheet(isPresented: $isShowingImagePicker, onDismiss: loadImage) {
            ImagePicker(image: self.$images[self.selectedImageIndex], sourceType: .camera)
        }
        .onAppear {
            // 뷰가 나타날 때 카메라 권한을 요청합니다.
            self.requestCameraPermission()
        }
    }

    func loadImage() {
        // 이미지를 선택하고 가져온 후에 여기에 추가적인 로직을 넣을 수 있습니다.
    }
    
    func imageButton(forIndex index: Int) -> some View {
        Button(action: {
            self.selectedImageIndex = index
            self.isShowingImagePicker = true
        }) {
            if let image = images[index] {
                Image(uiImage: image)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 100, height: 100)
                    .clipShape(Circle())
            } else {
                Text("Select Image")
            }
        }
    }
    
    func requestCameraPermission() {
        AVCaptureDevice.requestAccess(for: .video) { granted in
            if granted {
                print("카메라 액세스 허용됨")
            } else {
                print("카메라 액세스 거부됨")
            }
        }
    }
    
    func uploadImages() {
        // 이미지를 업로드하는 API 호출
        // 업로드 중인지 확인하기 위해 isUploading을 사용할 수 있습니다.
        // API 호출이 완료되면 isUploading을 false로 설정하여 전송하기 버튼을 다시 활성화할 수 있습니다.
        
        // 예시:
        self.isUploading = true
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            self.isUploading = false
            print("이미지 업로드가 완료되었습니다.")
        }
    }
}

struct ImagePicker: UIViewControllerRepresentable {
    @Binding var image: UIImage?
    var sourceType: UIImagePickerController.SourceType // 추가된 속성

    class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
        @Binding var image: UIImage?

        init(image: Binding<UIImage?>) {
            _image = image
        }

        func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
            if let uiImage = info[.originalImage] as? UIImage {
                image = uiImage
            }

            picker.dismiss(animated: true, completion: nil)
        }

        func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
            picker.dismiss(animated: true, completion: nil)
        }
    }

    func makeCoordinator() -> Coordinator {
        return Coordinator(image: $image)
    }

    func makeUIViewController(context: Context) -> UIImagePickerController {
        let picker = UIImagePickerController()
        picker.delegate = context.coordinator
        picker.sourceType = sourceType // 카메라 또는 갤러리에서 가져올 소스 타입 설정
        return picker
    }

    func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {}
}

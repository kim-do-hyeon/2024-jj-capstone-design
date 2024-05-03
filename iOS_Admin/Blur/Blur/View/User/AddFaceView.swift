import SwiftUI
import AVFoundation

struct AddFaceView: View {
    @State private var images: [UIImage?] = Array(repeating: nil, count: 4)
    @State private var isShowingImagePicker = false
    @State private var isUploading = false
    @State private var Username = UserDefaults.standard.string(forKey: "Username")
    @State private var Password = UserDefaults.standard.string(forKey: "Password")
    @State private var selectedImageIndex: Int?

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
            ImagePicker(images: self.$images, sourceType: .camera, selectedImageIndex: selectedImageIndex ?? 0)
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
            self.isShowingImagePicker = true
            self.selectedImageIndex = index
        }) {
            if let image = images[index] {
                Image(uiImage: image)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 200, height: 200)
                    .clipShape(Circle())
            } else {
                Text("Select Image")
                    .frame(width: 200, height: 200)
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
        print(Username)
        print(Password)
        guard let username = Username, let password = Password else {
            print("Username 또는 Password가 nil입니다.")
            return
        }
        
        isUploading = true // 업로드 중임을 표시
        var success_count : Int = 0
        for (index, image) in images.enumerated() {
            if let image = image, let imageData = image.jpegData(compressionQuality: 0.5) {
                // 이미지 업로드 API 호출
                Task {
                    do {
                        // 이미지를 업로드합니다.
                        let apiModel = try await NetworkService.shared.registerface(images: images, username: username, password: password)
                        // 성공적으로 업로드된 경우 로그를 출력합니다.
                        print("이미지 \(index + 1) 업로드 성공: \(apiModel)")
                        // 모든 이미지가 성공적으로 업로드되었는지 확인
                        DispatchQueue.main.async {
                            // 메인 스레드에서 success_count를 증가시킵니다.
                            if apiModel.result == "success" {
                                success_count += 1
                            }
                            
                            // 모든 이미지가 성공적으로 업로드되었는지 확인
                            let allSuccess = success_count == images.count
                            print(success_count)
                            print(images.count)
                            if allSuccess {
                                showAlert(message: apiModel.result ?? "Error")
                            }
                        }
                    } catch {
                        // 업로드 중에 오류가 발생한 경우 오류를 출력합니다.
                        print("이미지 \(index + 1) 업로드 오류: \(error)")
                    }
                    
                    // 마지막 이미지를 업로드한 후에는 isUploading을 false로 설정하여 전송하기 버튼을 다시 활성화합니다.
                    if index == images.count - 1 {
                        isUploading = false
                    }
                }
            } else {
                print("이미지 \(index + 1)가 nil이거나 이미지 데이터를 생성하는 데 문제가 있습니다.")
            }
        }
    }
    
    func showAlert(message: String) {
        let alert = UIAlertController(title: "Message", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        UIApplication.shared.windows.first?.rootViewController?.present(alert, animated: true, completion: nil)
    }

}

struct ImagePicker: UIViewControllerRepresentable {
    @Binding var images: [UIImage?]
    var sourceType: UIImagePickerController.SourceType // 추가된 속성
    var selectedImageIndex: Int // 선택된 이미지의 인덱스

    class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
        @Binding var images: [UIImage?]
        var selectedImageIndex: Int

        init(images: Binding<[UIImage?]>, selectedImageIndex: Int) {
            _images = images
            self.selectedImageIndex = selectedImageIndex
        }

        func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
            if let uiImage = info[.originalImage] as? UIImage {
                images[selectedImageIndex] = uiImage // 선택된 버튼의 인덱스에 해당하는 위치에 이미지 추가
            }

            picker.dismiss(animated: true, completion: nil)
        }

        func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
            picker.dismiss(animated: true, completion: nil)
        }
    }

    func makeCoordinator() -> Coordinator {
        return Coordinator(images: $images, selectedImageIndex: selectedImageIndex) // 선택된 이미지의 인덱스 전달
    }

    func makeUIViewController(context: Context) -> UIImagePickerController {
        let picker = UIImagePickerController()
        picker.delegate = context.coordinator
        picker.sourceType = sourceType // 카메라 또는 갤러리에서 가져올 소스 타입 설정
        return picker
    }

    func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {}
}

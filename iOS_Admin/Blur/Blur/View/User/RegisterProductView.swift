import SwiftUI

struct RegisterProductView: View {
    @State private var isLoading = false // 로딩 상태를 나타내는 State 변수 추가
    @State private var model_code : String = ""
    @State private var Username = UserDefaults.standard.string(forKey: "Username")
    @State private var Password = UserDefaults.standard.string(forKey: "Password")

    var body: some View {
        VStack{
            Spacer()
            Image("Logo")
                .resizable()
                .frame(width: 120, height: 170)
            Text("BLUR")
                .foregroundColor(Color.mainText)
                .frame(width: 700)
                .font(.system(size: 62))
                .fontWeight(.bold)
            Text("Beyond Limitations, Unleashing Reflection")
                .foregroundColor(Color.mainsubText)
                .font(.system(size: 14))
            Spacer()
                .frame(height: 100)
            HStack{
                TextField("기기 번호", text : $model_code)
                    .frame(width: 305)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
            }
            .padding(.leading, 30)
        }
        Button(action: {
            isLoading = true // 버튼을 누를 때 로딩 상태를 true로 설정
            Task {
                do {
                    let apiModel = try await ProductionService.shared.registerProduct(username: Username ?? "Guest", password: Password ?? "Guest", model_code : model_code)
                    // API 모델을 사용하여 필요한 작업 수행
                    isLoading = false // 작업이 완료되면 로딩 상태를 false로 설정
                    if (isLoading == false) {
                        showAlert(message: apiModel.result ?? "Error")
                    }
                } catch {
                    // 오류 처리
                    print("Error: \(error)")
                    isLoading = false // 오류가 발생해도 로딩 상태를 false로 설정
                }
            }
        }, label: {
            if isLoading {
                ProgressView() // 로딩 중에는 로딩 표시기 표시
            } else {
                Text("등록하기")
                    .padding()
                    .frame(width: 305)
                    .background(Color.mainText)
                    .foregroundColor(Color.whiteFix)
                    .fontWeight(.bold)
                    .cornerRadius(10)
            }
        })
    }
}

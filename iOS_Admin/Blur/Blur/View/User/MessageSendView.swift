import SwiftUI

struct MessageSendView : View {
    @State private var users: [String] = []
    @State private var isLoading = false
    @State private var Username: String = UserDefaults.standard.string(forKey: "Username") ?? "Guest"
    @State private var modelCode: String = UserDefaults.standard.string(forKey: "model_code") ?? "0000-0000"
    @State private var message : String = ""
    @State private var isShowingUserPicker = false // 사용자 선택기 표시 상태
    @State private var selectedUser: String? // 선택된 사용자
    
//    init(users: [String] = [], isLoading: Bool = false, modelCode: String = "0000-0000", Username:String = "qqq") {
//           _users = State(initialValue: users)
//           _isLoading = State(initialValue: isLoading)
//           _modelCode = State(initialValue: modelCode)
//            _Username = State(initialValue: Username)
//       }
    
    var body: some View {
            VStack {
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

                VStack {
                    if isLoading {
                        ProgressView("Loading users...")
                    } else {
                        Button(action: {
                            isShowingUserPicker = true
                        }, label: {
                            Text("발송 대상")
                        })

                        if let user = selectedUser {
                            Text("Selected user: \(user)")
                                .padding()
                        }

                        TextField("메시지", text: $message)
                            .frame(width: 305)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                        
                        Button(action: {
                            isLoading = true // 버튼을 누를 때 로딩 상태를 true로 설정
                            Task {
                                do {
                                    let apiModel = try await MessageService.shared.sendMessage(sender: Username, receiver: selectedUser ?? "Error", message : message)
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
                Spacer()
            }
            .sheet(isPresented: $isShowingUserPicker) {
                UserPicker(users: users, selectedUser: $selectedUser)
            }
            .onAppear {
                loadUserData()
            }
        }
        
        func loadUserData() {
            isLoading = true
            Task {
                do {
                    let response = try await ProductionService.shared.getProductionUsers(model_code: modelCode)
                    if let message = response.message {
                        print(message)
                        decodeMessageAndSetUsers(message)
                    }
                } catch {
                    print("Error:", error)
                }
                isLoading = false
            }
        }
        
        func decodeMessageAndSetUsers(_ message: String) {
            let jsonString = message.replacingOccurrences(of: "'", with: "\"")
            if let jsonData = jsonString.data(using: .utf8) {
                do {
                    let decodedUsers = try JSONDecoder().decode([String].self, from: jsonData)
                    self.users = decodedUsers
                } catch {
                    print("Error decoding message: \(error)")
                }
            }
        }
    }

struct UserPicker: View {
    var users: [String]
    @Binding var selectedUser: String?

    var body: some View {
        List(users, id: \.self) { user in
            Button(user) {
                selectedUser = user
                // 현재 뷰를 종료합니다.
                UIApplication.shared.windows.first?.rootViewController?.dismiss(animated: true, completion: nil)
            }
        }
    }
}


//#Preview {
//    MessageSendView(users: [], isLoading: true, modelCode: "1234-5678", Username : "qqq")
//}

import SwiftUI

struct LoginView: View {
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var errormessage: String = ""
    @State private var isLoading = false
    @State private var loginSuccess = false
    @State private var admin = false
    
    var body: some View {
        NavigationView {
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
                TextField("Enter Your ID", text : $username)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(width: 300, height: 60)
                TextField("Enter Your Password", text : $password)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(width: 300, height: 60)
                
                Text("\(errormessage)")
                    .foregroundColor(Color.red)
                    .font(.system(size: 14))
                
                if loginSuccess && admin == false{
                    NavigationLink(destination: EmptyView(), isActive: $loginSuccess) {
                    }
                }else if loginSuccess && admin {
                    NavigationLink(destination: AdminView(), isActive: $loginSuccess) {
                    }
                }
                else {
                    Button(action: {
                        isLoading = true
                        login(username: username, password: password)
                        isAdmin()
                        
                    }, label: {
                        if isLoading {
                            ProgressView()
                        } else {
                            Text("로그인")
                                .padding()
                                .frame(width: 380)
                                .background(Color.mainText)
                                .foregroundColor(Color.whiteFix)
                                .fontWeight(.bold)
                                .cornerRadius(10)
                        }
                    })
                    .disabled(isLoading) // 버튼 비활성화 설정
                }
                
                NavigationLink(destination: AddUserView()) {
                    Text("회원가입")
                        .padding()
                        .frame(width: 380)
                        .background(Color.mainText)
                        .foregroundColor(Color.whiteFix)
                        .fontWeight(.bold)
                        .cornerRadius(10)
                }
            }
        }
        .navigationBarHidden(true) // NavigationBar 숨김
    }
    
    func login(username: String, password: String) {
        Task {
            do {
                let apiModel = try await LoginService.shared.login(username: username, password: password)
                print(apiModel.result)
                isLoading = false // 작업이 완료되면 로딩 상태를 false로 설정
                if(apiModel.result == "success") {
                    // 로그인 성공 시 UserDefaults에 로그인 상태 저장
                    UserDefaults.standard.set(true, forKey: "isLoggedIn")
                    loginSuccess = true
                } else {
                    errormessage = apiModel.message ?? "Error"
                }
            } catch {
                // 오류 처리
                print("Error: \(error)")
                isLoading = false // 오류가 발생해도 로딩 상태를 false로 설정
            }
        }
    }
    
    func checkLoggedIn() {
        let isLoggedIn = UserDefaults.standard.bool(forKey: "isLoggedIn")
        if isLoggedIn {
            // 이미 로그인되어 있는 경우, isAdmin()을 호출하여 관리자 여부를 확인할 수 있음
            isAdmin()
        }
    }
    
    func isAdmin() {
        Task {
            do {
                let apiModel = try await LoginService.shared.checkAdmin(username: username, password: password)
                print(apiModel.result)
                isLoading = false // 작업이 완료되면 로딩 상태를 false로 설정
                if(apiModel.message == "True") {
                    // 로그인 성공 시 UserDefaults에 로그인 상태 저장
                    UserDefaults.standard.set(true, forKey: "isAdmin")
                    admin = true
                } else {
                    errormessage = apiModel.message ?? "Error"
                }
            } catch {
                // 오류 처리
                print("Error: \(error)")
                isLoading = false // 오류가 발생해도 로딩 상태를 false로 설정
            }
            return false
        }
    }
}



#Preview {
    LoginView()
}

import SwiftUI

struct MainView: View {
    @State private var isLoggedIn = false
    @State private var isAdmin = false

    var body: some View {
        NavigationView {
            VStack {
                Spacer()
                Image("Logo")
                    .resizable()
                    .frame(width: 120, height: 170)
                Spacer(minLength: 5)
                Text("BLUR")
                    .foregroundColor(Color.mainText)
                    .frame(width: 700)
                    .font(.system(size: 62))
                    .fontWeight(.bold)
                Text("Beyond Limitations, Unleashing Reflection")
                    .foregroundColor(Color.mainsubText)
                    .font(.system(size: 14))
                Spacer()
                NavigationLink(destination: decideDestinationView()) {
                    Text(isLoggedIn ? "시작하기" : "로그인") // 로그인 상태에 따라 버튼 텍스트 변경
                        .padding()
                        .frame(width: 380)
                        .background(Color.mainText)
                        .foregroundColor(Color.whiteFix)
                        .fontWeight(.bold)
                        .cornerRadius(10)
                }
                
                if !isLoggedIn {
                    NavigationLink(destination: AddUserView()) {
                        Text("회원가입")
                            .padding()
                            .frame(width: 380)
                            .background(Color.mainText)
                            .foregroundColor(Color.whiteFix)
                            .fontWeight(.bold)
                            .cornerRadius(10)
                    }
                    
                    NavigationLink(destination: FindPasswordView()) {
                        Text("비밀번호 찾기 >")
                            .frame(width: 120)
                            .foregroundColor(Color.mainText)
                            .font(.system(size: 14))
                            .fontWeight(.bold)
                            .cornerRadius(10)
                    }
                    .padding(.top, 20)
                }
            }
            .navigationBarHidden(true) // NavigationBar 숨김
            .onAppear {
                // 앱 시작 시 로그인 상태 확인
//                isLoggedIn = UserDefaults.standard.bool(forKey: "isLoggedIn")
//                isAdmin = UserDefaults.standard.bool(forKey: "isAdmin")
            }
        }
    }

    func decideDestinationView() -> some View {
        if isLoggedIn {
            if isAdmin {
                return AnyView(AdminView(isLoggedIn : $isLoggedIn))
            } else {
                return AnyView(UserView(isLoggedIn: $isLoggedIn))
            }
        } else {
            return AnyView(LoginView())
        }
    }
}

#Preview {
    MainView()
}

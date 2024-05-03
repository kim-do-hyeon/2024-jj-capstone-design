import SwiftUI

struct MainView: View {
    @State private var isLoggedIn = UserDefaults.standard.bool(forKey: "isLoggedIn")
    @State private var isAdmin = UserDefaults.standard.bool(forKey: "isAdmin")

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
                NavigationLink(destination: {
                    print(isLoggedIn)
                    print(isAdmin)
                    if isLoggedIn{
                        return AnyView(UserView(isLoggedIn: $isLoggedIn))
                    }
                    else {
                        return AnyView(LoginView())
                    }
                }) {
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
        }
    }
}

#Preview {
    MainView()
}

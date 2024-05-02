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
                NavigationLink(destination: Group {
                    if isLoggedIn && isAdmin {
                        AdminView()
                    } else if isLoggedIn && isAdmin == false {
                        EmptyView()
                    }else {
                        LoginView()
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
                }
            }
            .navigationBarHidden(true) // NavigationBar 숨김
            .onAppear {
                // 앱 시작 시 로그인 상태 확인
                isLoggedIn = UserDefaults.standard.bool(forKey: "isLoggedIn")
                isAdmin = UserDefaults.standard.bool(forKey: "isAdmin")
            }
        }
    }
}

#Preview {
    MainView()
}

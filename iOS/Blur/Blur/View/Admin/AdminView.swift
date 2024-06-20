import SwiftUI

struct AdminView: View {
//    @State private var isLoggedIn = true // 로그아웃 버튼을 추가하기 위해 상태 추가
    @Binding var isLoggedIn: Bool
    var body: some View {
//        NavigationView {
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
                
                
                HStack {
                    NavigationLink(destination: UserListView()) {
                        Text("회원관리")
                            .modifier(ButtonStyle())
                    }

                    NavigationLink(destination: AddUserView()) {
                        Text("회원추가")
                            .modifier(ButtonStyle())
                    }
                }
                .padding(.horizontal)

                HStack {
//                    NavigationLink(destination: WidgetsListView()) {
//                        Text("위젯관리")
//                            .modifier(ButtonStyle())
//                    }

                    NavigationLink(destination: WidgetsAddView()) {
                        Text("위젯추가")
                            .modifier(ButtonStyle())
                    }
                }
                .padding(.horizontal)
                
                Spacer() // 버튼이 화면을 꽉 채우도록 함
                
                Button(action: {
                    // 로그아웃 버튼을 클릭했을 때 실행되는 액션
                    UserDefaults.standard.set(false, forKey: "isLoggedIn") // isLoggedIn을 false로 설정
                    UserDefaults.standard.set(false, forKey: "isAdmin") // isAdmin을 false로 설정
                    self.isLoggedIn = false // 상태 업데이트
                }) {
                    NavigationLink(destination: MainView(), isActive: $isLoggedIn) {
                        Text("로그아웃")
                            .padding()
                            .frame(width: 200)
                            .background(Color.red) // 로그아웃 버튼 색상
                            .foregroundColor(Color.white)
                            .cornerRadius(10)
                    }
                }
                .padding(.bottom, 20) // 로그아웃 버튼과 하단 간격 추가
            }
            .navigationBarHidden(true) // NavigationBar 숨김
//        }
    }
}

struct ButtonStyle: ViewModifier {
    func body(content: Content) -> some View {
        content
            .padding()
            .frame(maxWidth: .infinity)
            .background(Color.mainText)
            .foregroundColor(Color.white)
            .fontWeight(.bold)
            .cornerRadius(10)
    }
}

#Preview {
    AdminView(isLoggedIn:.constant(true))
}

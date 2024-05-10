//
//  UserView.swift
//  Blur
//
//  Created by pental on 5/2/24.
//

import SwiftUI

struct UserView: View {
    @Binding var isLoggedIn: Bool // MainView의 isLoggedIn 상태와 바인딩

    @State private var shouldNavigateToMainView = false

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
            
//            NavigationLink(destination: MainView(), isActive: $shouldNavigateToMainView) {
//                EmptyView()
//            }.navigationBarHidden(true)
            
            VStack{
                HStack{
                    //TODO: - Face Recognize
                    NavigationLink(destination: AddFaceView()) {
                        Text("얼굴 등록")
                            .modifier(ButtonStyle())
                    }
                    NavigationLink(destination: UserWidgetView()) {
                        Text("위젯 등록")
                            .modifier(ButtonStyle())
                    }
                }
                HStack{
                    NavigationLink(destination: RegisterProductView()) {
                        Text("기기 등록")
                            .modifier(ButtonStyle())
                    }
                    NavigationLink(destination: UserProductView()) {
                        Text("기기 회원")
                            .modifier(ButtonStyle())
                    }
                }
                HStack{
                    NavigationLink(destination: MessageSendView()) {
                        Text("메시지 전송")
                            .modifier(ButtonStyle())
                    }
                }
            }
            
            Button(action: {
                // 로그아웃 버튼을 클릭했을 때 실행되는 액션
                UserDefaults.standard.set(false, forKey: "isLoggedIn") // isLoggedIn을 false로 설정
                UserDefaults.standard.removeObject(forKey: "token")
                self.isLoggedIn = false // MainView의 isLoggedIn 상태 업데이트
                self.shouldNavigateToMainView = true // NavigationLink를 활성화하여 MainView로 이동
            }) {
                Text("로그아웃")
                    .padding()
                    .frame(width: 200)
                    .background(Color.red) // 로그아웃 버튼 색상
                    .foregroundColor(Color.white)
                    .cornerRadius(10)
            }
            .padding(.bottom, 20) // 로그아웃 버튼과 하단 간격 추가
        }
        .navigationBarHidden(true)
    }
}

#Preview {
    UserView(isLoggedIn: .constant(true))
}

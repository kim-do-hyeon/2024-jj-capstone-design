//
//  UserView.swift
//  Blur
//
//  Created by pental on 5/2/24.
//

import SwiftUI

struct UserView: View {
    @Binding var isLoggedIn: Bool // MainView의 isLoggedIn 상태와 바인딩

    var body: some View {
        VStack {
            Text("User View Content")
            
            NavigationLink(destination: MainView(), isActive: $isLoggedIn) {
                EmptyView()
            }
            .isDetailLink(false) // NavigationLink를 통해 전환 시 뒤로가기 버튼을 숨김
            
            Button(action: {
                // 로그아웃 버튼을 클릭했을 때 실행되는 액션
                UserDefaults.standard.set(false, forKey: "isLoggedIn") // isLoggedIn을 false로 설정
                self.isLoggedIn = false // MainView의 isLoggedIn 상태 업데이트
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


//#Preview {
//    UserView()
//}

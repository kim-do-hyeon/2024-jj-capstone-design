//
//  FindPasswordView.swift
//  Blur
//
//  Created by pental on 5/2/24.
//

import SwiftUI

struct FindPasswordView : View {
    @State private var username: String = ""
    @State private var email: String = ""
    @State private var errormessage: String = ""
    @State private var newPassword: String = ""
    @State private var isLoading = false
    @State private var isComplete = false
    
    var body: some View {
        VStack {
            Spacer()
            Text("암호를 잊어버렸습니까?")
                .frame(width: 300, height: 20)
                .font(.system(size: 30))
                .fontWeight(.bold)
            Text("걱정하지 마세요! 계정과 연결된 이메일 주소를\n입력하시면 메일로 비밀번호를 보내드립니다.")
                .frame(width: 300, height: 40)
                .font(.system(size: 15))
                .foregroundColor(.findpasswordSubText)
                .padding(.top, 30)
            
            VStack{
                TextField("Enter Your ID", text : $username)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(width: 300, height: 60)
                TextField("Enter Your Email", text : $email)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .frame(width: 300, height: 60)
            }
            .padding(.top, 30)
            
            if (isComplete == true){
                NavigationLink(destination: FindPasswordResultView(newPassword: newPassword), isActive: $isComplete) {
                    EmptyView()
                }

            }else {
                Button(action: {
                    isLoading = true
                    resetpassword(username: username, email: email)
                }, label: {
                    if isLoading {
                        ProgressView()
                    } else {
                        Text("메일전송")
                            .padding()
                            .frame(width: 305, height: 60)
                            .background(Color.mainText)
                            .foregroundColor(Color.whiteFix)
                            .fontWeight(.bold)
                            .cornerRadius(10)
                    }
                })
            }
            if(errormessage == "Not Found User") {
                Text("\(errormessage)")
            }
            
            Spacer()
            
        }
    }
    
    func resetpassword(username: String, email: String) {
        Task {
            do {
                let apiModel = try await LoginService.shared.findPassword(username: username, email: email)
                print(apiModel.result)
                isLoading = false
                if(apiModel.result == "success") {
                    newPassword = apiModel.message ?? "Contact Admin"
                    isComplete = true
                } else {
                    errormessage = apiModel.message ?? "Error"
                }
            } catch {
                print("Error: \(error)")
                isLoading = false
            }
        }
    }
}


#Preview {
    FindPasswordView()
}

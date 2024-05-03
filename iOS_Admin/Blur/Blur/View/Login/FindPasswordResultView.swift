//
//  FindPasswordResultView.swift
//  Blur
//
//  Created by pental on 5/2/24.
//

import SwiftUI

struct FindPasswordResultView : View {
    var newPassword: String
        
    init(newPassword: String) {
        self.newPassword = newPassword
    }
    
    var body: some View {
        Text("새로운 비밀번호을 알려드립니다!")
        Text("\(newPassword)")
            .frame(width: 300, height: 100)
            .foregroundColor(.mainText)
            .fontWeight(.bold)
            .font(.system(size: 32))
    }
}

#Preview {
    FindPasswordResultView(newPassword: "1")
}

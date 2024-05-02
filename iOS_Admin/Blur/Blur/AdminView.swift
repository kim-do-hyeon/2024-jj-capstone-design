import SwiftUI

struct AdminView: View {
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
                
                
                HStack {
                    NavigationLink(destination: UserView()) {
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
                    NavigationLink(destination: WidgetsListView()) {
                        Text("위젯관리")
                            .modifier(ButtonStyle())
                    }

                    NavigationLink(destination: WidgetsAddView()) {
                        Text("위젯추가")
                            .modifier(ButtonStyle())
                    }
                }
                .padding(.horizontal)
                
                Spacer() // 버튼이 화면을 꽉 채우도록 함
            }
            .navigationBarHidden(true) // NavigationBar 숨김
        }
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
    AdminView()
}

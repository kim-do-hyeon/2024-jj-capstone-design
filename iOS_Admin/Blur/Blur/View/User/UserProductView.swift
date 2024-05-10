import SwiftUI

struct UserProductView: View {
    @State private var users: [String] = []
    @State private var isLoading = false
    @State private var modelCode: String = UserDefaults.standard.string(forKey: "model_code") ?? "0000-0000"
    
//    init(users: [String] = [], isLoading: Bool = false, modelCode: String = "0000-0000") {
//           _users = State(initialValue: users)
//           _isLoading = State(initialValue: isLoading)
//           _modelCode = State(initialValue: modelCode)
//       }
    
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
                .frame(height: 100)

            List(users, id: \.self) { user in
                VStack(alignment: .leading) {
                    Text(user)
                        .font(.headline)
                }
            }
            .onAppear {
                loadUserData()
            }
            .toolbar {
                EditButton()
            }
        }
    }
    
    func loadUserData() {
        isLoading = true
        Task {
            do {
                let response = try await ProductionService.shared.getProductionUsers(model_code: modelCode)
                if let message = response.message {
                    decodeMessageAndSetUsers(message)
                }
            } catch {
                print("Error:", error)
            }
            isLoading = false
        }
    }
    
    func decodeMessageAndSetUsers(_ message: String) {
            // JSON 형식으로 문자열을 수정합니다.
            let jsonString = message.replacingOccurrences(of: "'", with: "\"")
            if let jsonData = jsonString.data(using: .utf8) {
                do {
                    let decodedUsers = try JSONDecoder().decode([String].self, from: jsonData)
                    self.users = decodedUsers
                } catch {
                    print("Error decoding message: \(error)")
                }
            }
        }
}

//#Preview {
//    UserProductView(users: [], isLoading: true, modelCode: "1234-5678")
//}

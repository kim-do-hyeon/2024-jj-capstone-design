import SwiftUI

struct UserListView: View {
    @State private var users: [User] = []

    var body: some View {
        List {
            ForEach(users, id: \.self) { user in
                VStack(alignment: .leading) {
                    Text(user.username)
                        .font(.headline)
                    Text(user.email)
                        .font(.subheadline)
                    Text(user.originalname)
                        .font(.subheadline)
                    Text(user.profile_image ?? "None")
                        .font(.subheadline)
                }
            }
            .onDelete(perform: delete)
        }
        .onAppear {
            loadUserData()
        }
        .toolbar {
            EditButton()
        }
    }

    func loadUserData() {
        Task {
            do {
                let response = try await NetworkService.shared.getUserData()
                if let message = response.message {
                    self.users = Array(message.values)
                    print(message.values)
                }
            } catch {
                print("Error:", error)
            }
        }
    }

    func delete(at offsets: IndexSet) {
            let deletedUserIDs = offsets.map { users[$0].id } // 삭제된 사용자의 ID를 가져옴
            print(deletedUserIDs)
            // API 서버에 삭제된 사용자의 ID를 전송
            Task {
                do {
                    // API 서버와의 통신
                    let response = try await NetworkService.shared.deleteUsers(withIDs: deletedUserIDs)
                    DispatchQueue.main.async {
                        // 비동기 작업 완료 후에 alert 띄우기
                        showAlert(message: response.message)
                    }
                    
                    // 로컬 데이터 업데이트
                    users.remove(atOffsets: offsets)
                } catch {
                    print("Error:", error)
                }
            }
        }
    
    func showAlert(message: String) {
        let alert = UIAlertController(title: "Message", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        UIApplication.shared.windows.first?.rootViewController?.present(alert, animated: true, completion: nil)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        UserListView()
    }
}


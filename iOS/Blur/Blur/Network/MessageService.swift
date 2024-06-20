import Foundation

class MessageService {
    static let shared: MessageService = MessageService()
    
    func sendMessage(sender: String, receiver: String, message: String) async throws -> APIModel {
        var components = URLComponents(string: "https://jj.system32.kr/send_message")
        
        // URL을 구성합니다.
        guard let url = components?.url else { throw URLError(.badURL) }
        
        // URLRequest를 생성하고, HTTP 메서드를 "POST"로 설정합니다.
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        
        // HTTP Body를 설정합니다. 이 경우 JSON 형식을 사용합니다.
        let requestBody = [
            "sender_username": sender,
            "receiver_username": receiver,
            "content": message
        ]
        
        // JSON 인코딩을 시도합니다.
        request.httpBody = try JSONEncoder().encode(requestBody)
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        // URLSession을 사용하여 데이터를 전송하고 응답을 받습니다.
        let (data, response) = try await URLSession.shared.data(for: request)
        
        // HTTP 응답 상태 코드를 확인합니다.
        guard let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 else {
            let statusCode = (response as? HTTPURLResponse)?.statusCode ?? 0
            throw NSError(domain: "HTTP Error", code: statusCode, userInfo: nil)
        }
        
        // 받은 데이터를 디코드합니다.
        let messageSendResult = try JSONDecoder().decode(APIModel.self, from: data)
        return messageSendResult
    }
}

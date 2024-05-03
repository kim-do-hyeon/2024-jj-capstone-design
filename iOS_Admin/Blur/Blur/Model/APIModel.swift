import Foundation

struct APIModel: Decodable {
    let message: String?
    let result: String
    let type: String
}


struct UserModel: Decodable {
    let message: [String: User]?
    let result: String
    let type: String
}

struct DeleteUser : Decodable {
    let result: String
    let type: String
    let message: String
}

struct User: Decodable, Hashable {
    let id : Int
    let email: String
    let username: String
    let originalname : String
    let profile_image : String?
    
    enum CodingKeys: String, CodingKey {
        case id
        case email
        case username
        case originalname
        case profile_image
    }
}


struct WidgetsModel: Decodable {
    let message: [String: Widgets]? // 딕셔너리 타입으로 수정
    let result: String?
    let type: String?
}


struct Widgets: Decodable, Hashable {
    let id: Int?
    let widgets_name: String?
    
    enum CodingKeys : String, CodingKey {
        case id
        case widgets_name
    }
    
}

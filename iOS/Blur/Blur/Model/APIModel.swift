import Foundation

struct APIModel: Decodable {
    let message: String?
    let result: String
    let type: String
}

//
//  UserModel.swift
//  Blur
//
//  Created by pental on 5/4/24.
//

import Foundation

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

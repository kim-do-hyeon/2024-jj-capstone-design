//
//  NetworkService.swift
//  Blur
//
//  Created by pental on 5/1/24.
//

import Foundation


class NetworkService {
    static let shared : NetworkService = NetworkService()
    
    func getUserData() async throws -> UserModel {
        var urlString = "https://jj.system32.kr/login?username=test&password=test"
        guard let url1 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data1, response1) = try await URLSession.shared.data(from: url1)
        
        urlString = "https://jj.system32.kr/admin/management/user/list/view"
        guard let url2 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data2, response2) = try await URLSession.shared.data(from: url2)
        
        // 두 번째 요청에 대한 데이터만 사용하여 HomeResponse를 디코딩
        let userModelList = try JSONDecoder().decode(UserModel.self, from: data2)
        
        return userModelList
    }
    
    func deleteUsers(withIDs ids: [Int]) async throws -> DeleteUser {
        var urlString = "https://jj.system32.kr/login?username=test&password=test"
        guard let url1 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data1, response1) = try await URLSession.shared.data(from: url1)
        
        urlString = "https://jj.system32.kr/admin/management/user/delete/" + ids[0].description
        print(urlString)
        guard let url2 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data2, response2) = try await URLSession.shared.data(from: url2)
        let deleteUser = try JSONDecoder().decode(DeleteUser.self, from: data2)
        return deleteUser
    }
    
    func addUser(username: String, password: String, email: String, originalname: String) async throws -> APIModel {
            var urlString = "https://jj.system32.kr/register/user?username=\(username)&password=\(password)&email=\(email)&originalname=\(originalname)"
            
            guard let url = URL(string: urlString) else { throw URLError(.badURL) }
            let (data, response) = try await URLSession.shared.data(from: url)
            
            let apiModel = try JSONDecoder().decode(APIModel.self, from: data)
            return apiModel
        }
}

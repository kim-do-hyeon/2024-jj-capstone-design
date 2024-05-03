//
//  LoginService.swift
//  Blur
//
//  Created by pental on 5/2/24.
//

import Foundation


class LoginService {
    static let shared : LoginService = LoginService()
    
    func login(username: String, password: String) async throws -> APIModel {
        var urlString = "https://jj.system32.kr/login?username=\(username)&password=\(password)"
        guard let url = URL(string: urlString) else { throw URLError(.badURL) }
        let (data, response) = try await URLSession.shared.data(from: url)
        print(data)
        let apiModel = try JSONDecoder().decode(APIModel.self, from: data)
        print(apiModel)
        return apiModel
    }
    
    func checkAdmin(username : String, password : String) async throws -> APIModel {
        var urlString = "https://jj.system32.kr/login?username=\(username)&password=\(password)"
        guard let url1 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data1, response1) = try await URLSession.shared.data(from: url1)
        
        urlString = "https://jj.system32.kr/admin/check"
        guard let url2 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data2, response2) = try await URLSession.shared.data(from: url2)
        
        // 두 번째 요청에 대한 데이터만 사용하여 HomeResponse를 디코딩
        let adminCheck = try JSONDecoder().decode(APIModel.self, from: data2)
        
        return adminCheck
    }
    
    func findPassword(username : String, email : String) async throws -> APIModel {
        var urlString = "https://jj.system32.kr/reset_password?username=\(username)&email=\(email)"
        guard let url = URL(string: urlString) else { throw URLError(.badURL) }
        let (data, response) = try await URLSession.shared.data(from: url)
        let result = try JSONDecoder().decode(APIModel.self, from: data)
        return result
    }
}

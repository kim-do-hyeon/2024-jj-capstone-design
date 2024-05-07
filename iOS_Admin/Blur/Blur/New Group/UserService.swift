//
//  NetworkService.swift
//  Blur
//
//  Created by pental on 5/1/24.
//

import Foundation
import UIKit


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
    
    func registerface(images: [UIImage?], username: String, password: String) async throws -> APIModel {
        // 로그인 요청
        var urlString = "https://jj.system32.kr/login?username=\(username)&password=\(password)"
        guard let url1 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data1, response1) = try await URLSession.shared.data(from: url1)
        
        // 얼굴 등록 요청
        urlString = "https://jj.system32.kr/register/face"
        guard let url = URL(string: urlString) else { throw URLError(.badURL) }
        
        // 요청 생성
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        
        // 멀티파트 형식으로 요청 바디 생성
        let boundary = "Boundary-\(UUID().uuidString)"
        request.setValue("multipart/form-data; boundary=\(boundary)", forHTTPHeaderField: "Content-Type")
        
        // 이미지 데이터 추가
        var body = Data()
        for (index, image) in images.enumerated() {
            guard let image = image else { continue }
            guard let imageData = image.jpegData(compressionQuality: 1.0) else { continue }
            
            // 멀티파트 형식에 맞게 데이터 추가
            body.append("--\(boundary)\r\n".data(using: .utf8)!)
            body.append("Content-Disposition: form-data; name=\"face_image\"; filename=\"image\(index).jpeg\"\r\n".data(using: .utf8)!)
            body.append("Content-Type: image/jpeg\r\n\r\n".data(using: .utf8)!)
            body.append(imageData)
            body.append("\r\n".data(using: .utf8)!)
        }
        
        // 멀티파트 형식 종료
        body.append("--\(boundary)--\r\n".data(using: .utf8)!)
        
        // 요청에 바디 설정
        request.httpBody = body
        
        // 요청 보내고 응답 받기
        let (data, _) = try await URLSession.shared.data(for: request)
        
        // 응답 데이터 디코딩하여 APIModel 반환
        let apiModel = try JSONDecoder().decode(APIModel.self, from: data)
        return apiModel
    }
    
    func widget_custom(username: String, password: String, locations: [String: [Int]]) async throws -> APIModel {
        // 로그인 URL 설정 및 데이터 요청
        let loginURLString = "https://jj.system32.kr/login?username=\(username)&password=\(password)"
        guard let loginURL = URL(string: loginURLString) else { throw URLError(.badURL) }
        let (_, _) = try await URLSession.shared.data(from: loginURL)
        
        // JSON 인코딩
        let encoder = JSONEncoder()
        encoder.outputFormatting = .withoutEscapingSlashes
        let jsonData = try encoder.encode(locations)
        guard let jsonString = String(data: jsonData, encoding: .utf8) else { throw URLError(.badURL) }
        
        // URL 인코딩
        let encodedJson = jsonString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? ""
        
        // 위젯 커스텀 설정 URL
        let widgetURLString = "https://jj.system32.kr/widgets_custom?model_code=1234-5678&index=\(encodedJson)"
        guard let widgetURL = URL(string: widgetURLString) else { throw URLError(.badURL) }
        let (data, response) = try await URLSession.shared.data(from: widgetURL)
        
        // 응답 데이터 디코딩
        let apiModel = try JSONDecoder().decode(APIModel.self, from: data)
        return apiModel
    }

}

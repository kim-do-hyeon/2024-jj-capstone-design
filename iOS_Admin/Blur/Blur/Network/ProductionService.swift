//
//  ProductionService.swift
//  Blur
//
//  Created by pental on 5/8/24.
//

import Foundation
class ProductionService {
    static let shared : ProductionService = ProductionService()
    
    func registerProduct(username : String, password : String, model_code : String) async throws -> APIModel {
        var urlString = "https://jj.system32.kr/login?username=\(username)&password=\(password)"
        guard let url1 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data1, response1) = try await URLSession.shared.data(from: url1)
        urlString = "https://jj.system32.kr/register/product?code=\(model_code)"
        guard let url2 = URL(string : urlString) else { throw URLError(.badURL) }
        let (data2, response2) =  try await URLSession.shared.data(from: url2)
        
        let apiModelResult = try JSONDecoder().decode(APIModel.self, from: data2)
        
        return apiModelResult
    }
}

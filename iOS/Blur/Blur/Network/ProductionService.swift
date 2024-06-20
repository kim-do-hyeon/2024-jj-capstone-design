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
    
    func getProductionUsers(model_code : String) async throws -> APIModel {
        var urlString = "https://jj.system32.kr/get_model_user_list?code=\(model_code)"
        guard let url = URL(string : urlString) else {throw URLError(.badURL)}
        let (data, response) = try await URLSession.shared.data(from: url)
        let ProductionModelResult = try JSONDecoder().decode(APIModel.self, from : data)
        return ProductionModelResult
    }
    
    func removeProduction(username : String, password : String, model_code : String) async throws -> APIModel {
        var urlString = "https://jj.system32.kr/login?username=\(username)&password=\(password)"
        guard let url1 = URL(string: urlString) else { throw URLError(.badURL) }
        let (data1, response1) = try await URLSession.shared.data(from: url1)
        urlString = "https://jj.system32.kr/register/product_remove?code=\(model_code)"
        guard let url2 = URL(string : urlString) else { throw URLError(.badURL) }
        let (data2, response2) =  try await URLSession.shared.data(from: url2)
        
        let apiModelResult = try JSONDecoder().decode(APIModel.self, from: data2)
        
        return apiModelResult
    }
}

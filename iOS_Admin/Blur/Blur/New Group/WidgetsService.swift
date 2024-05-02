//
//  WidgetsService.swift
//  Blur
//
//  Created by pental on 5/1/24.
//

import Foundation
class WidgetsService {
    static let shared : WidgetsService = WidgetsService()
    
    func getWidgetsList() async throws -> WidgetsModel {
        var urlString = "https://jj.system32.kr/admin/management/widgets/list"
        guard let url = URL(string: urlString) else { throw URLError(.badURL) }
        let (data, response2) = try await URLSession.shared.data(from: url)
        let widgetsModelList = try JSONDecoder().decode(WidgetsModel.self, from: data)
        
        return widgetsModelList
    }
    
}

//
//  WidgetsModel.swift
//  Blur
//
//  Created by pental on 5/4/24.
//

import Foundation

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


//
//  WidgetsLocationModel.swift
//  Blur
//
//  Created by pental on 5/4/24.
//

import Foundation

struct WidgetsLocationModel: Decodable {
    let result: String
    let type: String
    let message: [String: [Int]]? // 수정: message의 값은 [String: [Int]] 타입으로 변경

    // 추가: CodingKeys 정의
    enum CodingKeys: String, CodingKey {
        case result, type, message
    }
}

struct WidgetsLocation: Decodable, Hashable {
    let widgetsName: String // 수정: 속성명을 camelCase로 변경
    let index: [Int]? // 수정: List 타입 대신 [Int] 타입으로 변경

    // 추가: CodingKeys 정의
    enum CodingKeys: String, CodingKey {
        case widgetsName = "WidgetsName", index
    }
}


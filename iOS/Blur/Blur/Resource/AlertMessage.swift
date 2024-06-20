//
//  AlertMessage.swift
//  Blur
//
//  Created by pental on 5/4/24.
//

import SwiftUI
func showAlert(message: String) {
    let alert = UIAlertController(title: "Message", message: message, preferredStyle: .alert)
    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
    UIApplication.shared.windows.first?.rootViewController?.present(alert, animated: true, completion: nil)
}

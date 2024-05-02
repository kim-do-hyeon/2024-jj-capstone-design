//
//  WidgetsView.swift
//  Blur
//
//  Created by pental on 5/1/24.
//

import SwiftUI

struct WidgetsListView: View {
    @State private var widgets: [Widgets] = []
    var body: some View {
        VStack{
            Text("Widgets List")
                .font(.system(size: 20))
            List {
                ForEach(widgets, id: \.self) { widget in
                    VStack(alignment: .leading) {
                        Text(widget.widgets_name ?? "A")
                            .font(.headline)
                    }
                }
            }
            .onAppear {
                loadWidgetsData()
            }
            .toolbar {
                EditButton()
            }
        }
    }
    
    func loadWidgetsData() {
        Task {
            do {
                let response = try await WidgetsService.shared.getWidgetsList()
                if let message = response.message {
                    self.widgets = Array(message.values)
                }
            } catch {
                print("Error:", error)
            }
        }
    }
}

#Preview {
    WidgetsListView()
}

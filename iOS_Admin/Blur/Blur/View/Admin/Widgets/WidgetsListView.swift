import SwiftUI

struct WidgetsListView: View {
    @State private var widgets: [Widgets] = []
    @Binding var selectedWidget: SelectedWidget? // 선택한 위젯을 전달하기 위한 바인딩
    var body: some View {
        VStack{
            Text("Widgets List")
                .font(.system(size: 20))
            List {
                ForEach(widgets, id: \.self) { widget in
                    VStack(alignment: .leading) {
                        Button(action: {
                            selectedWidget = SelectedWidget(name: widget.widgets_name ?? "A", location: selectedWidget?.location ?? [0, 0])
                                            
                        }) {
                            Text(widget.widgets_name ?? "A")
                                .font(.headline)
                        }
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

struct WidgetsListView_Previews: PreviewProvider {
    static var previews: some View {
        WidgetsListView(selectedWidget: .constant(SelectedWidget(name: "Example", location: [1, 1])))
    }
}

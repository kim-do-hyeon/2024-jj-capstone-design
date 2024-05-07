import SwiftUI

struct SelectedWidget: Identifiable {
    let id = UUID()
    let name: String
    let location : [Int]
}

struct UserWidgetView: View {
    @State private var Username = UserDefaults.standard.string(forKey: "Username")
    @State private var Password = UserDefaults.standard.string(forKey: "Password")
    
    @State private var selectedWidget: SelectedWidget?
    @State private var widgetLocations: [String: [Int]] = [:]
    @State private var isUploading = false

    var body: some View {
        NavigationView {
            VStack{
                VStack{
                    VStack {
                        LazyVGrid(columns: Array(repeating: GridItem(), count: 4), spacing: 10) {
                            ForEach(1...12, id: \.self) { index in
                                let row = (index - 1) / 4 + 1
                                let column = (index - 1) % 4 + 1
                                let location = [row, column]
                                if let widget = findWidgetName(at: location) {
                                    Button(action: {
                                        selectedWidget = SelectedWidget(name: widget, location: location)
                                    }, label: {
                                        Text(widget)
                                            .font(.system(size: 20))
                                            .foregroundStyle(.mainText)
                                    })
                                } else {
                                    Button(action: {
                                        selectedWidget = SelectedWidget(name: "Empty", location: location)
                                    }, label: {
                                        Image(systemName: "plus.circle.dashed")
                                            .font(.system(size: 20))
                                            .foregroundColor(.mainText)
                                    })
                                }
                            }
                        }
                        .padding()
                    }
                    .onAppear{
                        getWidgetsLocation()
                    }
                    .navigationTitle("User Widgets - \(Username!)")
                    .sheet(item: $selectedWidget) { widget in
                        WidgetsListView(selectedWidget: $selectedWidget)
                            .onDisappear {
                                if let newWidget = selectedWidget {
                                    // 업데이트 위치와 이름을 새로운 선택으로 설정
                                    updateWidgetLocation(widget: newWidget.name, to: newWidget.location)
                                    selectedWidget = nil
                                }
                            }
                    }
                }
                
                VStack{
                    Button(action: {
                        isUploading = true
                        save_location()
                    }, label: {
                        
                        if isUploading {
                            ProgressView()
                        }else {
                            Text("저장하기")
                                .padding()
                                .frame(maxWidth: 200)
                                .background(Color.mainText)
                                .foregroundColor(Color.white)
                                .fontWeight(.bold)
                                .cornerRadius(10)
                        }
                    })
                }
            }
        }
    }
    
    func getWidgetsLocation() {
        Task {
            do {
                let apiModel = try await WidgetsService.shared.getUserWidgetsLocation(username: Username ?? "test")
                widgetLocations = apiModel.message ?? [:]
                print(widgetLocations)
            } catch {
                print("Failed to load widget locations")
            }
        }
    }
    
    func updateWidgetLocation(widget: String, to location: [Int]) {
        // 기존 위치 제거
        if let oldLocation = widgetLocations[widget] {
            widgetLocations.removeValue(forKey: widget)
        }
        // 새 위치 추가
        widgetLocations[widget] = location
    }

    func findWidgetName(at location: [Int]) -> String? {
        return widgetLocations.first(where: { $0.value == location })?.key
    }
    
    func save_location(){
        isUploading = true // 버튼을 누를 때 로딩 상태를 true로 설정
        Task {
            do {
                let apiModel = try await NetworkService.shared.widget_custom(username: Username ?? "Guest", password : Password ?? "Guest", locations: widgetLocations)
                // API 모델을 사용하여 필요한 작업 수행
                isUploading = false // 작업이 완료되면 로딩 상태를 false로 설정
                if (isUploading == false) {
                    print(apiModel.result)
                    if apiModel.result == "success" {
                        showAlertWidget(message: "성공적으로 저장되었습니다.")
                    }else {
                        showAlertWidget(message: "저장에 실패했습니다.\n관리자에게 연락부탁드립니다.")
                    }
                    
                }
            } catch {
                // 오류 처리
                print("Error: \(error)")
                isUploading = false // 오류가 발생해도 로딩 상태를 false로 설정
            }
        }
    }
    
    func showAlertWidget(message: String) {
        DispatchQueue.main.async {
            let alert = UIAlertController(title: "Message", message: message, preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            
            // Safely unwrapping the key window's rootViewController
            if let rootVC = UIApplication.shared.windows.first(where: { $0.isKeyWindow })?.rootViewController {
                rootVC.present(alert, animated: true, completion: nil)
            }
        }
    }
}

struct UserWidgetView_Previews: PreviewProvider {
    static var previews: some View {
        UserWidgetView()
    }
}

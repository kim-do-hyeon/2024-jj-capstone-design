import SwiftUI

struct SelectedWidget: Identifiable {
    let id = UUID() // Identifiable 프로토콜을 준수하기 위해 id 속성 추가
    let name: String // 선택한 위젯의 이름
}

struct UserWidgetView: View {
    // 기본 사용자명을 설정
    @State private var username = UserDefaults.standard.string(forKey: "Username")
    // 선택한 위젯 (옵셔널 타입으로 변경)
    @State private var selectedWidget: SelectedWidget? = nil
    // API로부터 받은 위젯 위치 데이터
    @State private var widgetLocations: [String: [Int]] = [:] // 초기에 빈 딕셔너리로 설정

    var body: some View {
        NavigationView {
            VStack {
                // 그리드 형식으로 위젯 위치를 렌더링
                LazyVGrid(columns: Array(repeating: GridItem(), count: 4), spacing: 10) {
                    ForEach(1...12, id: \.self) { index in
                        let row = (index - 1) / 4 + 1
                        let column = (index - 1) % 4 + 1
                        let location = [row, column] // 위치를 [행, 열]로 표현
                        if let widget = findWidgetName(at: location) {
                            Button(action: {
                                selectedWidget = SelectedWidget(name: widget)
                            }, label: {
                                Text(widget)
                                    .font(.system(size: 20))
                                    .foregroundStyle(.mainText)
                            })
                        } else {
                            Button(action: {
                                selectedWidget = nil
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
            .onAppear {
                // 뷰가 로드될 때 API를 호출하여 위젯 위치 데이터를 가져옴
                getWidgetsLocation()
            }
            .navigationTitle("User Widgets")
            .sheet(item: $selectedWidget) { _ in // 선택된 위젯은 아래로 전달됩니다.
                WidgetsListView(selectedWidget: $selectedWidget)
                    .onDisappear {
                        // 위젯 목록이 사라질 때 선택한 위젯을 처리합니다.
                        if let selectedWidget = selectedWidget {
                            // 선택한 위젯을 처리하는 로직을 여기에 추가하세요.
                            
                            print("Selected widget:", selectedWidget.name)
                            
                        }
                    }
            }
        }
    }

    func getWidgetsLocation() {
        Task {
            do {
                // API로부터 위젯 위치 데이터 가져오기
                let apiModel = try await WidgetsService.shared.getUserWidgetsLocation(username: username ?? "test")
                // 데이터 업데이트
                widgetLocations = apiModel.message ?? [:]
                print(widgetLocations)
            } catch {
                // 오류 처리
                showAlert(message: "위젯이 등록되지 않았습니다. 위젯 등록페이지로 이동합니다.")
            }
        }
    }
    
    // 위치에 해당하는 위젯 이름을 찾는 함수
    func findWidgetName(at location: [Int]) -> String? {
            if let widgetName = widgetLocations.first(where: { $0.value == location })?.key {
                return widgetName
            }
            return nil
        }

    // 오류 발생 시 경고 표시
    func showAlert(message: String) {
        // 여기에 경고창 표시 로직을 추가
    }
}

struct UserWidgetView_Previews: PreviewProvider {
    static var previews: some View {
        UserWidgetView()
    }
}


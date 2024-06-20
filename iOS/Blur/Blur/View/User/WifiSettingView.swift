import SwiftUI
import CoreBluetooth

class BluetoothManager: NSObject, ObservableObject, CBCentralManagerDelegate, CBPeripheralDelegate {
    var centralManager: CBCentralManager!
    var raspberryPiPeripheral: CBPeripheral?
    @Published var serialCharacteristic: CBCharacteristic?
    @Published var receivedData: String = ""

    override init() {
        super.init()
        centralManager = CBCentralManager(delegate: self, queue: nil)
    }

    // CBCentralManagerDelegate 메서드
    func centralManagerDidUpdateState(_ central: CBCentralManager) {
        if central.state == .poweredOn {
            centralManager.scanForPeripherals(withServices: nil, options: nil)
        } else {
            print("Bluetooth is not available.")
        }
    }

    func centralManager(_ central: CBCentralManager, didDiscover peripheral: CBPeripheral, advertisementData: [String: Any], rssi RSSI: NSNumber) {
        let peripheralName = peripheral.name ?? "Unknown"
        print("Discovered: \(peripheralName)")

        // 광고 데이터 출력
        print("Advertisement Data: \(advertisementData)")

        if peripheralName == "TEST" {
            raspberryPiPeripheral = peripheral
            raspberryPiPeripheral?.delegate = self
            centralManager.stopScan()
            centralManager.connect(peripheral, options: nil)
        }
    }

    func centralManager(_ central: CBCentralManager, didConnect peripheral: CBPeripheral) {
        print("Connected to TEST")
        peripheral.discoverServices(nil)
    }

    // CBPeripheralDelegate 메서드
    func peripheral(_ peripheral: CBPeripheral, didDiscoverServices error: Error?) {
        if let services = peripheral.services {
            for service in services {
                peripheral.discoverCharacteristics(nil, for: service)
            }
        }
    }

    func peripheral(_ peripheral: CBPeripheral, didDiscoverCharacteristicsFor service: CBService, error: Error?) {
        if let characteristics = service.characteristics {
            for characteristic in characteristics {
                if characteristic.properties.contains(.write) {
                    serialCharacteristic = characteristic
                }
                if characteristic.properties.contains(.read) || characteristic.properties.contains(.notify) {
                    peripheral.setNotifyValue(true, for: characteristic)
                }
            }
        }
    }

    func peripheral(_ peripheral: CBPeripheral, didUpdateValueFor characteristic: CBCharacteristic, error: Error?) {
        if let data = characteristic.value, let string = String(data: data, encoding: .utf8) {
            DispatchQueue.main.async {
                self.receivedData = string
                print("Received data: \(string)")
            }
        }
    }

    func sendData(_ data: Data, characteristic: CBCharacteristic) {
        if let peripheral = raspberryPiPeripheral {
            peripheral.writeValue(data, for: characteristic, type: .withResponse)
        }
    }

    func sendText(_ text: String, characteristic: CBCharacteristic) {
        if let data = text.data(using: .utf8) {
            sendData(data, characteristic: characteristic)
        }
    }
}

struct WifiSettingView: View {
    @StateObject private var bluetoothManager = BluetoothManager()

    var body: some View {
        VStack {
            Text("Wifi Settings")
                .font(.largeTitle)
                .padding()

            Button(action: {
                if let characteristic = bluetoothManager.serialCharacteristic {
                    bluetoothManager.sendText("Hello, Raspberry Pi!", characteristic: characteristic)
                }
            }) {
                Text("Send Data")
                    .font(.title)
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(10)
            }
            .disabled(bluetoothManager.serialCharacteristic == nil)

            Text("Received Data:")
                .font(.headline)
                .padding(.top, 20)

            Text(bluetoothManager.receivedData)
                .font(.body)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(10)
                .padding()
        }
        .padding()
    }
}

struct WifiSettingView_Previews: PreviewProvider {
    static var previews: some View {
        WifiSettingView()
    }
}

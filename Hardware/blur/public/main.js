const { app, BrowserWindow } = require('electron');
const path = require('path');
const remote = require('@electron/remote/main');
remote.initialize();

function createWindow() {
    const win = new BrowserWindow({
        width: 800,
        height: 600,
        fullscreen: true,
        frame: false,
        webPreferences: {
            enableRemoteModule: true
        }
    });

    if (process.env.NODE_ENV === 'development') {
        win.loadURL('http://localhost:3000');
    } else {
        win.loadURL(`file://${path.join(__dirname, '../build/index.html')}`);
    }

    remote.enable(win.webContents);
}

app.on('ready', createWindow);

app.on('window-all-closed', function() {
    if (process.platform !== 'darwin') {
        app.quit();
    }
});

app.on('activate', function() {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
});

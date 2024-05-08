from flask import Flask, jsonify
from flask_cors import CORS
import requests
from bs4 import BeautifulSoup

app = Flask(__name__)
CORS(app)  # CORS를 전체 앱에 적용

@app.route('/api/finance')
def get_kospi():
    response = requests.get('https://finance.naver.com/')
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    kospi = soup.select_one('#content > div.article > div.section2 > div.section_stock_market > div.section_stock > div.kospi_area.group_quot').text
    kospi = (list(kospi.split("\n")))[5]
    kosdaq = soup.select_one('#content > div.article > div.section2 > div.section_stock_market > div.section_stock > div.kosdaq_area.group_quot').text
    kosdaq = (list(kosdaq.split("\n")))[5]
    rate = soup.select_one("#content > div.article2 > div.section1 > div.group1 > table > tbody > tr.up.bold > td:nth-child(2)").text
    return jsonify(kospi = kospi, kosdaq = kosdaq, rate = rate)

if __name__ == '__main__':
    app.run(debug=True)

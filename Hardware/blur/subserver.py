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
    kospi = list(kospi.split("\n"))
    kospi_value = kospi[5]
    kospi_change_value = kospi[6]
    kospi_change_rate = kospi[7]
    if kospi[8] == "상승" :
        kospi_type = "▲"
    else :
        kospi_type = "▼"
    kosdaq = soup.select_one('#content > div.article > div.section2 > div.section_stock_market > div.section_stock > div.kosdaq_area.group_quot').text
    kosdaq = list(kosdaq.split("\n"))
    kosdaq_value = kosdaq[5]
    kosdaq_change_value = kosdaq[6]
    kosdaq_change_rate = kosdaq[7]
    if kosdaq[8] == "상승" :
        kosdaq_type = "▲"
    else :
        kosdaq_type = "▼"
    
    rate = soup.select_one("#content > div.article2 > div.section1 > div.group1 > table > tbody > tr.up.bold > td:nth-child(2)").text
    rate_change = soup.select_one("#content > div.article2 > div.section1 > div.group1 > table > tbody > tr.up.bold > td:nth-child(3)").text
    rate_change_value = rate_change.split(" ")[-1]
    if "상승" in rate_change :
        rate_type = "▲"
    else :
        rate_type = "▼"

    kospi_message = "{} {}  {}({})".format(kospi_type, kospi_value, kospi_change_value, kospi_change_rate)
    kosdaq_message = "{} {}  {}({})".format(kosdaq_type, kosdaq_value, kosdaq_change_value, kosdaq_change_rate)
    rate_message = "{} {}  ({})".format(rate_type, rate, rate_change_value)
    
    return jsonify(kospi = kospi_message, kosdaq = kosdaq_message, rate = rate_message)

if __name__ == '__main__':
    app.run(debug=True)

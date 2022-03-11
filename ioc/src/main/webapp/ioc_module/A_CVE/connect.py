from datetime import datetime, time
from bs4 import BeautifulSoup
import time
import openpyxl as openpyxl
import pymysql
import os
import smtplib
from datetime import datetime
from email import encoders
from email.utils import formataddr
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import requests
from googletrans import Translator


def runDBupdate(sql):
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    curq.execute(sql) 
    connq.commit()
    connq.close()
    #print("DEBUG : "+sql)
    
    
def runDBselect(sql):
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    curq.execute(sql) 
    connq.commit()
    connq.close()
    #print("DEBUG : "+sql)

    return curq

def getList(jobno, jobip, jobdate):
    print("################ CVE 데이터 GET ###########################")
    cur = runDBselect("SELECT no, cve, status FROM cve WHERE status = 0 AND ipip = '"+ str(jobip)+"' AND time = '" + str(jobdate)+ "'")
    result = []
    for row in cur:
        if row[0] == "X" and row[1] == "X" and row[2] == "X" and row[3] == "X" and row[4] == "X":

            runDBupdate("UPDATE cve SET status = '3' WHERE ipip = '" + str(
                jobip) + "' AND time = '" + str(jobdate) + "' AND status = '2'")

            return 9

        if(row[0] is None or row[0] == ""):
            return 0
        else:
            result.append(row[1])


    loglog("작업[" + str(jobno) + "] 총 [" + str(len(result)) + "]개 데이터 등록 ")

    filename = excel(result,jobip, jobdate)
 
    loglog( "작업[" + str(jobno) + "] 총 [" + str(len(result)) + "]개 데이터 변환완료 ")

    sendMail(filename, jobno, jobip, jobdate, len(result))
        ######################################################################
    return 1

def sendMail(filename, jobno, jobip, jobdate, num1):
    # 보내는사람

    name = '보안관제'

    cur = runDBselect("SELECT no, email FROM jobq WHERE ipip = '" + str(jobip) + "' AND time = '" + str(jobdate) + "'")

    for a in cur:
        address = a[1]

    cur = runDBselect("SELECT * FROM user WHERE a = '"+a[1]+"'")

    for a in cur:
        realname = a[3]

    cur = runDBselect("SELECT mailcount FROM site_status")

    # 여러 줄 출력
    i = 0
    value = 0
    for row in cur:
        value = row[0]

    runDBupdate("UPDATE site_status SET mailcount = " + str(value + 1))

    from_addr = formataddr(('SOCH', 'bh.lee@s-oil.com'))

    # 받는사람
    to_addr = formataddr((name, address))

    session = None
    try:
        # SMTP 세션 생성
        session = smtplib.SMTP('smtp.gmail.com', 587)
        session.set_debuglevel(True)

        # SMTP 계정 인증 설정
        session.ehlo()
        session.starttls()
        session.login('igloosoil@gmail.com', 'lougwydyuijffjcd')

        # 메일 콘텐츠 설정
        message = MIMEMultipart("mixed")

        # 메일 송/수신 옵션 설정
        message.set_charset('utf-8')
        message['From'] = from_addr
        message['To'] = to_addr
        message['Subject'] = "[보안관제] CVE 정보등록 데이터_작업[" + str(jobno)+"] "+str(num1) + "건"

        # 메일 콘텐츠 - 내용
        body = '''
        <h2>안녕하세요.</h1>
        <h4>SOC Helper입니다. </h4>  
        </br><h4><h4>솔루션 주소 : "http://222.110.22.168:8080/ioc/main.jsp" </h4>  
        <h4> 수행작업 : CVE 기준 CVSS 정보 리포팅 파일 생성</h4>   </br>
            <h4> CVE 기준 검색을 통하여 발표일 CVSS 중요도 출처 및 설명을 xlsx로 출력</h4>
        </br>
        <h4>"\\\\192.168.20.12\\itsoc\\S-OIL보안관제PJT\\14.취약점관리\\CVE 취약점 정리 리스트"에 통합관리 요망</h4>  
        '''
        bodyPart = MIMEText(body, 'html', 'utf-8')
        message.attach(bodyPart)

        # 메일 콘텐츠 - 첨부파일
        attachments = [
            os.path.join(os.getcwd(), filename)
        ]

        for attachment in attachments:
            attach_binary = MIMEBase("application", "octect-stream")
            try:
                binary = open(attachment, "rb").read()  # read file to bytes

                attach_binary.set_payload(binary)
                encoders.encode_base64(attach_binary)  # Content-Transfer-Encoding: base64

                filename = os.path.basename(attachment)
                attach_binary.add_header("Content-Disposition", 'attachment', filename=('utf-8', '', filename))

                message.attach(attach_binary)
            except Exception as e:
                print(e)

        # 메일 발송
        session.sendmail(from_addr, to_addr, message.as_string())

        loglog("작업[" + str(jobno) + "] CVE 결과 메일 발송 완료(수신:" + realname + ")")

    except Exception as e:
        print(e)
    finally:
        if session is not None:
            session.quit()

    #################################################################################################################

def loglog(logText):
    print(logText)
    curA = runDBselect("select MAX(no) from log")

    no = 1
    for rs in curA:
        if rs[0] != None and no > 0:
            no = rs[0]
        else:
            no = 1

    nowTime = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    
    runDBupdate("INSERT INTO LOG (no, text) values ('" + str(no + 1) + "','" + nowTime+" : " + logText + "')")
 

def excel(list, jobip, jobdate):
    filename = ""
    if len(list) >= 1:
        print("DATA IN")
        count = 1
        url = 'https://nvd.nist.gov/vuln/detail/'
        url2 = 'https://translate.google.com/?hl=ko&sl=en&tl=ko&op=translate&text='

        filename = datetime.today().strftime('%Y.%m') + "_CVE_CVSS_List(" + datetime.today().strftime(
            '%y%m%d') + ")_백데이터.xlsx"
        wb = openpyxl.Workbook()
        sheet = wb.active

        sheet['A1'] = '번호'
        sheet['B1'] = '년월'
        sheet['C1'] = '발표일'
        sheet['D1'] = 'CVE'
        sheet['E1'] = 'CVSS Score'
        sheet['F1'] = '중요도'
        sheet['G1'] = '해당 여부'
        sheet['H1'] = '출처(URL)'
        sheet['I1'] = '내용'
        sheet['J1'] = '비고'

        line = 2

        for i in list:
            time.sleep(1)
            sitecountUp(1)
            response = requests.get(url + i)
            if response.status_code == 200:
                if 'Not Found' in response.text:
                    print(str(count) + " " + i + " : " + "Not Found 수동조회 진행")

                    sheet['A' + str(line)] = str(count)
                    sheet['B' + str(line)] = "수동조회 대상"
                    sheet['C' + str(line)] = "수동조회 대상"
                    sheet['D' + str(line)] = i
                    sheet['E' + str(line)] = "수동조회 대상"
                    sheet['F' + str(line)] = "수동조회 대상"
                    sheet['G' + str(line)] = "수동조회 대상"
                    sheet['H' + str(line)] = url + i
                    sheet['I' + str(line)] = "수동조회 대상"
                    sheet['J' + str(line)] = ''
                    setup1(i, "cve", jobip, jobdate)
                    runDBupdate("update cve set a='" + str(
                        count) + "',b='N/A',c='N/A',d='" + i + "',e='N/A',f='N/A',g='O/X',h='" + url + i + "',i='N/A' where cve =  '" + i + "' and ipip = '" + jobip + "' and time = '" + jobdate + "'")

                else:
                    html = response.text
                    soup = BeautifulSoup(html, 'html.parser')

                    if '\"Cvss3NistCalculatorAnchorNA\"' in response.text:
                        cvss = soup.select_one('a[id="Cvss3NistCalculatorAnchorNA"]')
                        cvss_text = cvss.get_text()
                        score_text = "N/A"
                        severity_text = "N/A"

                    if '\"Cvss3NistCalculatorAnchor\"' in response.text:
                        cvss = soup.select_one('a[id="Cvss3NistCalculatorAnchor"]')
                        cvss_text = cvss.get_text()
                        tmp = cvss_text.split(" ")
                        score_text = tmp[0]
                        severity_text = tmp[1]

                    if '\"Cvss3CnaCalculatorAnchor\"' in response.text:
                        cvss = soup.select_one('a[id="Cvss3CnaCalculatorAnchor"]')
                        cvss_text = cvss.get_text()
                        tmp = cvss_text.split(" ")
                        score_text = tmp[0]
                        severity_text = tmp[1]

                    date = soup.select_one('span[data-testid="vuln-published-on"]')
                    cve = soup.select_one('a[data-testid="vuln-cve-dictionary-entry"]')
                    info = soup.select_one('p[data-testid="vuln-description"]')

                    date_text = date.get_text()
                    tmp2 = date_text.split("/")
                    mm_text = tmp2[0]
                    dd_text = tmp2[1]
                    yy_text = tmp2[2]
                    cve_text = cve.get_text()
                    info_text = info.get_text()

                    yymm_text = yy_text + "." + mm_text
                    yymmdd_text = yy_text + "." + mm_text + "." + dd_text

                    infokr_text = trans(info_text)

                    print(str( count) + " " + yymm_text + " " + yymmdd_text + " " + i + " " + score_text + " " + severity_text + " " + "O/X" + url + i + " " + infokr_text)

                    sheet['A' + str(line)] = str(count)
                    sheet['B' + str(line)] = yymm_text
                    sheet['C' + str(line)] = yymmdd_text
                    sheet['D' + str(line)] = i
                    sheet['E' + str(line)] = score_text
                    sheet['F' + str(line)] = severity_text
                    sheet['G' + str(line)] = 'O/X'
                    sheet['H' + str(line)] = url + i
                    sheet['I' + str(line)] = infokr_text
                    sheet['J' + str(line)] = ''

                    infokr_text = infokr_text.replace("\'","\\\'")
                    runDBupdate("update cve set a='"+str(count)+"',b='"+yymm_text+"',c='"+yymmdd_text+"',d='"+i+"',e='"+score_text+"',f='"+severity_text+"',g='O/X',h='"+url + i+"',i='"+infokr_text+"' where cve =  '"+i+"' and ipip = '"+jobip+"' and time = '"+jobdate+"'")

                    setup1(i, "cve", jobip, jobdate)

                count += 1
                line += 1

            else:
                setup1(i, "cve", jobip, jobdate)

        wb.save(filename)
    return filename


def setup1(result, type, jobip, jobdate):
    # setup1(md5, "md5", jobip, jobdate)
    #setup1(md5, "md5", jobip, jobdate)

    # md5 변환 완료 (status 0 > 1)
    runDBupdate("UPDATE cve SET status = '1' WHERE "+type+" = '" + result + "' AND ipip = '" + str(jobip) + "' AND time = '" + str(jobdate) + "'")
    
    

def trans(TEXT):
    trans= Translator()
    result = trans.translate(TEXT,src="en",dest="ko")
    return result.text

def sitecountUp(num):

    cur7 = runDBselect("SELECT count FROM site_status where no = 1")
 

    for a in cur7:
        count = a[0]
        if a[0] is None:
            count = 0

    out = count + num

    runDBupdate("UPDATE site_status SET count =" + str(out) + " where no = 1")

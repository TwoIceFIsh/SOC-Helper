import json
import time
from pprint import pprint

import pymysql
# send_attachment.py
import os
import smtplib
from googletrans import Translator
from datetime import datetime
from email import encoders
from email.utils import formataddr
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import requests


def virusSHA1(sha1List):

    result = []
    for i in sha1List:

        try:
            url = 'https://www.virustotal.com/vtapi/v2/file/report'
            params = {'apikey': 'a78160975ee4a9960bb330b90fb6506ead0797cc5d7f14aff0c4f06d293f5bc7', 'resource': i}
            response = requests.get(url, params=params)

            SHA1 = response.json()
            result.append(SHA1['md5'])
            print("sha1 변환 완료 : "+SHA1['md5'])

        except KeyError:
            print("sha1List KeyError :" + i)
            return 0

        time.sleep(15)

    return result


def virusSHA256(sha256List):
    result = []
    for i in sha256List:

        try:
            url = 'https://www.virustotal.com/vtapi/v2/file/report'
            params = {'apikey': 'a78160975ee4a9960bb330b90fb6506ead0797cc5d7f14aff0c4f06d293f5bc7', 'resource': i}
            response = requests.get(url, params=params)

            SHA256 = response.json()

            result.append(SHA256['md5'])
            print("sha256 변환 완료 : " + SHA256['md5'])

        except KeyError:
            print("sha256List KeyError :" + i)
            return 0

        time.sleep(15)


    return result


def getList() :

    sha256 =[]
    md5 = []
    sha1 = []

    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT md5, sha1, sha256 FROM work_place WHERE (status = '0' AND MD5 != 'X') OR (status = '0' AND SHA1 != 'X') OR (status = '0' AND SHA256 != 'X')"
    cur.execute(sql)

    #값이 있으면 continue 하고 다음줄 읽음
    for row in cur:
        print(row[0]+" "+row[1]+" "+row[2])

        if row[0] != 'X' :
            md5.append(row[0])
            continue

        if row[1] != 'X' :
            sha1.append(row[1])
            continue

        if row[2] != 'X' :
            sha256.append(row[2])
            continue

    md5List = md5
    print("md5List " + str(md5List))

    sql2 = "UPDATE work_place SET status = '1' WHERE (status = '0' AND MD5 != 'X')"
    cur.execute(sql2)
    conn.commit()


    sha256List = []
    sha1List = []

    if len(sha256) > 0:
        out = virusSHA256(sha256)
        if out != 0:
            sha256List = out

    print("sha256List " + str(sha256List))
    sql2 = "UPDATE work_place SET status = '1' WHERE (status = '0' AND SHA256 != 'X')"
    cur.execute(sql2)
    conn.commit()

    if len(sha1) > 0:
        out = virusSHA1(sha1)
        if out != 0:
            sha1List = virusSHA1(sha1)

    print("sha1List " + str(sha1List))
    sql2 = "UPDATE work_place SET status = '1' WHERE (status = '0' AND SHA1 != 'X')"
    cur.execute(sql2)
    conn.commit()

    output = md5List+sha256List+sha1List
    print("output complete")
    # 한 줄 출력
    row = cur.fetchone()
    if row == None:
        print('검색결과없음')

    conn.close()

    # Stauts DB RW
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT count FROM site_status"
    cur.execute(sql)
    # 여러 줄 출력
    i = 0

    for row in cur:
        value = row[0]

    data = value + len(output)
    count2 = len(output)
    sql2 = "UPDATE site_status SET count = " + str(data)
    cur.execute(sql2)
    conn.commit()
    cur.fetchone()
    conn.close()

    yy = datetime.today().strftime('%y')
    mm = datetime.today().strftime('%m')
    dd = datetime.today().strftime('%d')

    md5_text = ""
    filename = "igloo_"+yy+mm+dd
    ioc = ""
    ioc1 = "{\"igloo\":{\"execution\":["
    ioc2 = "[{\"operator\":\"equal\",\"token\":\"processEvent/md5\",\"type\":\"md5\",\"value\":\""
    ioc3 = "[{\"operator\":\"equal\",\"token\":\"processEvent/md5\",\"type\":\"md5\",\"value\":\""
    ioc4 = "],\"name\":\"" + filename +"\",\"category\":\"Custom\",\"platforms\":[]}}"

    ioc = ioc + ioc1
    writetext = ""
    #output은 md5 배열
    for i in range(0, len(output)):

        if len(output) == 1:
            ioc = ioc1 + ioc3 + output[i] + "\"}]" + ioc4
            output[i] = ioc.strip()
            print("output 1 : "+output[i])
            continue

        if i == 0:
            ioc = ioc1

        if i > 0 and i == len(output)-2:
            ioc = ioc + ioc2 + output[i] + "\"}],"

        if i == len(output)-1:
            ioc = ioc + ioc3 + output[i] + "\"}]" + ioc4
            output[i] = ioc.strip()
            writetext = output[i]
            print("output > 1 : "+output[i])

    asdf = []

    asdf.append(count2)
    asdf.append(writetext)

    return asdf


def sendMail(filename, name, address, yy, mm, dd, line):
    # 보내는사람

    list = []
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT mailcount FROM site_status"
    cur.execute(sql)
    # 여러 줄 출력
    i = 0

    for row in cur:
        value = row[0]

    sql2 = "UPDATE site_status SET mailcount = " + str(value + 1)
    cur.execute(sql2)
    conn.commit()
    cur.fetchone()
    conn.close()

    from_addr = formataddr(('업무도우미', 'bh.lee@s-oil.com'))

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
        message['Subject'] = '[보안관제] ' + "HX 정보등록 데이터(MD5, SHA1, SHA256) "+" " + str(line-1) + "건_" +yy+mm+dd

        # 메일 콘텐츠 - 내용
        body = '''
        <h2>안녕하세요.</h1>
        <h4>업무도우미입니다. </h4>  
        </br><h4><h4>솔루션 주소 : "http://222.110.22.168:8080/ioc/main.jsp" </h4>  
        <h4> 수행작업 : EDR HX에 등록할 수 있는 데이터 파일 생성(MD5, SHA1, SHA256)</h4>   </br>
            <h4> </h4>
        </br>
        <h4>HX TOOL에 접속하여 업로드 해주세요(URL : "https://192.168.36.182:8080/login"</h4>  
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

        print('Successfully sent the mail!!!')
    except Exception as e:
        print(e)
    finally:
        if session is not None:
            session.quit()


def writeHX(filename,list):
    count = 1
    f = open(filename, 'w')
    print("writeHX IN" + str(list))
    f.write(list[len(list)-1])
    print("FIle Write : " + str(list[len(list)-1]))
    f.close()

    return list[0]

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

countzero = 0
asdfasdf = []


def virusTotal(sha256List, sha1List, jobip, jobdate):

    searchList = sha256List + sha1List

    result = []
    result2 = []
    result1 = []

    print("########################################## Virus Total 검색 시도 ##############################################")
    for i in sha256List:
        time.sleep(15)
        try:
            url = 'https://www.virustotal.com/vtapi/v2/file/report'
            params = {'apikey': '645c62843256a387939a6ab31b55f4e9a409971cdfe488d78b97881443289e6a', 'resource': i}
            response = requests.get(url, params=params)

            out = response.json()

            if out['md5'] != None:
                result1.append(out['md5'])
                updateVirustotal(out['md5'], i, jobip, jobdate)
                sitecountUp(1)

        except KeyError:
            print(
                "############################### 데이터 정상조회 실패 시에러처리 ######################################################")
            print("sha1List KeyError :" + i)
            updateVirustotal("변환실패", i, jobip, jobdate)
            sitecountUp(1)

    for i in sha1List:
        time.sleep(15)
        try:
            url = 'https://www.virustotal.com/vtapi/v2/file/report'
            params = {'apikey': '645c62843256a387939a6ab31b55f4e9a409971cdfe488d78b97881443289e6a', 'resource': i}
            response = requests.get(url, params=params)

            out = response.json()

            if out['md5'] != None:
                result2.append(out['md5'])
                updateVirustotal(out['md5'], i, jobip, jobdate)
                sitecountUp(1)

        except KeyError:
            print(
                "############################### 데이터 정상조회 실패 시에러처리 ######################################################")
            print("sha1List KeyError :" + i)
            updateVirustotal("변환실패", i, jobip, jobdate)
            sitecountUp(1)

    result.append(result1)
    result.append(result2)

    return result


def updateVirustotal(a, b, jobip, jobdate):

    print("#################################### SHA256 > 에러처리 데이터 작성 ######################################")



    if len(b) == 64:
        types = "sha256"

    if len(b) == 20:
        types = "sha1"

    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql2 = "UPDATE work_place SET md5 = '" + str(a) +"' WHERE " + types + " = '" + str(b) + "' AND ipip ='" + str(
        jobip) + "' AND time = '" + str(jobdate) + "'"
    cur.execute(sql2)
    conn.commit()


    print("################################# 변환실패가 작성된 SHA256 Status를 1로 변경 ###############################")
    sql2 = "UPDATE work_place SET status= 1 WHERE " + types + " = '" + str(b) + "' AND ipip ='" + str(
        jobip) + "' AND time = '" + str(jobdate) + "'"
    cur.execute(sql2)
    conn.commit()
    conn.close()



def loglog(logText):
    connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curA = connA.cursor()
    sqlA = "select MAX(no) from log"
    curA.execute(sqlA)
    connA.close()

    no = 1
    for rs in curA:
        if rs[0] != None and no > 0:
            no = rs[0]
        else:
            no = 1

    nowTime = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curA = connA.cursor()
    sqlA = "INSERT INTO LOG (no, text) values ('" + str(no + 1) + "','" + nowTime+" : " + logText + "')"
    curA.execute(sqlA)
    connA.commit()
    connA.close()

def sitecountUp(num):

    conn7 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur7 = conn7.cursor()

    sql7 = "SELECT count FROM site_status where no = 1"
    cur7.execute(sql7)

    for a in cur7:
        count = a[0]
        if a[0] is None:
            count = 0

    sql4 = "UPDATE site_status SET count =" + str(count + num) + " where no = 1"
    cur7.execute(sql4)
    conn7.commit()
    conn7.close()

def getList(jobno, jobip, jobdate):
    print(
        "##################################### 데이터 추출, HX 파일 작성, 엑셀파일 이름##############################################")

    print("#######데이터 초기화")
    yy = datetime.today().strftime('%y')
    mm = datetime.today().strftime('%m')
    dd = datetime.today().strftime('%d')

    value = 0
    x = 1
    md5 = []
    sha256 = []
    md5 = []
    sha1 = []
    ip1 = []
    url1 = []

    print("######################## 입력된 데이터 md5, sha1, sha256, ip ,url 획득 ##########################################")
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sqlq = "SELECT md5, sha1, sha256, ip, url ,  no FROM work_place WHERE status = '0' AND ipip = '" + str(jobip) + "' AND time = '" + str(jobdate) + "'"
    curq.execute(sqlq)
    curq.close()

    for row in curq:

        if row[0] != 'X':
            md5.append(row[0])

        if row[1] != 'X':
            sha1.append(row[1])

        if row[2] != 'X':
            sha256.append(row[2])

        if row[3] != 'X':
            ip1.append(row[3])

        if row[4] != 'X':
            url1.append(row[4])

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sqlq = "SELECT count(no) FROM work_place WHERE status = '0' AND ipip = '" + str(
        jobip) + "' AND time = '" + str(jobdate) + "'"
    curq.execute(sqlq)
    curq.close()

    for ain in curq:
        total = ain[0]

    # 데이터 처리 수 로그
    logText ="작업번호 ["+str(jobno)+ "] : 총 "+str(total)+"개 데이터 처리(md5: "+str(len(md5))+"/sha1: "+str(len(sha1))+"/sha256: "+str(len(sha256))+"/ip: "+str(len(ip1))+"/url: "+str(len(url1))+")"
    loglog(logText)

    # 데이터 처리 수 추가
    sitecountUp(total)

    # md5 변환 완료 (status 0 > 1)
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    for i in md5:
        sql4 = "UPDATE work_place SET status = '1' WHERE md5 = '" + i + "' AND ipip = '" + str(jobip) + "' AND time = '" + str(jobdate) + "'"
        curq.execute(sql4)
        connq.commit()
    connq.close()

    ###################################################################################################################
    md5List = []
    ipList = []
    url1List = []
    sha256List= []
    sha1List=[]
    # sha1, sha256 변환 시작
    if len(md5)> 0:
        md5List = md5

    if len(ip1) > 0:
        ipList = ip1

    if len(url1) > 0:
        url1List = url1

    if len(sha256) > 0 or len(sha1) > 0:
        out = virusTotal(sha256, sha1, jobip, jobdate)
        if len(out) > 0:
            sha256List = out[0]
            sha1List = out[1]

    output =[]
    output = md5List + sha256List + sha1List
    output2 = ip1 + url1


    md5Text = "md5: [" +str(len(md5List))+"/"+ str(len(md5)) + "]"
    sha1Text = "sha1: [" +str(len(sha1List))+"/"+ str(len(sha1)) + "]"
    sha256Text = "sha256: [" + str(len(sha256List))+"/"+ str(len(sha256))+"]"
    ipText = "ip: [" + str(len(ipList)) + "/" + str(len(ip1)) + "]"
    urlText = "url: [" + str(len(url1List)) + "/" + str(len(url1)) + "]"
    logText = "작업번호 ["+str(jobno)+ "] 총 " +str(len(output+output2))+"/"+ str(total) + "개 데이터 가공완료 ("+md5Text+"/"+sha1Text+"/"+sha256Text+"/"+ipText+"/"+urlText+")"
    loglog(logText)

    ################################################################################################################
    filename = writeExcel(jobip, jobdate, jobno)
    logText = "작업번호 ["+str(jobno)+ "] 총 " + str(len(output)) + "/" + str(total-len(output2)) + "개 데이터 EXCEL 데이터 작성 완료(" + md5Text + "/" + sha1Text + "/" + sha256Text + ")"
    loglog(logText)
    ################################################################################################################

    filename2 = writeHX(output,jobno)
    logText = "작업번호 ["+str(jobno)+ "] 총 " + str(len(output)) + "/" + str(total-len(output2)) + "개 데이터 HX 데이터 작성 완료(" + md5Text + "/" + sha1Text + "/" + sha256Text + ")"
    loglog(logText)

    sendMail(filename, filename2, jobno, jobip, jobdate, len(output), len(output2), total)


    return 1



def sendMail(filename, filename2,jobno, jobip, jobdate, num1, num2, num3):
    print(
        "############################################## 메일 보내기 ##########################################################")

    name = '보안관제'

    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT no, email FROM jobq WHERE ipip = '"+str(jobip)+"' AND time = '"+str(jobdate)+"'"
    cur.execute(sql)
    conn.commit()
    conn.close()

    for a in cur :
        address = a[1]

    if address == "DLZ1160@s-oil.com":
        realname = "보안관제팀"
    if address == "sungwoo.kwon@s-oil.com":
        realname = "부장님"
    if address == "jsh0119@s-oil.com":
        realname = "승환님"
    if address == "kmh0816@s-oil.com":
        realname = "명훈님"
    if address == "bh.lee@s-oil.com":
        realname = "병호님"
    if address == "ksm0117@s-oil.com":
        realname = "성민님"
    if address == "lyj0409@s-oil.com":
        realname = "예지님"
    if address == "khw1205@s-oil.com":
        realname = "형욱님"


    yy = datetime.today().strftime('%y')
    mm = datetime.today().strftime('%m')
    dd = datetime.today().strftime('%d')

    ################ 이메일 카운트 증가 ##################
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT mailcount FROM site_status"
    cur.execute(sql)
    # 여러 줄 출력
    i = 0

    for row in cur:
        value = row[0]

    if value is None:
        value = 0

    sql2 = "UPDATE site_status SET mailcount = " + str(value + 1)
    cur.execute(sql2)
    conn.commit()
    conn.close()
    ###############################################################
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

        total = num1+num2
        ex = num3-num1-num2
        # 메일 송/수신 옵션 설정
        message.set_charset('utf-8')
        message['From'] = from_addr
        message['To'] = to_addr
        message['Subject'] = "[보안관제] HX 정보등록 데이터(MD5, SHA1, SHA256, IP, URL) " + " " + str(total) + "/" + str(
            num3) + "건_" + str(jobdate)

        # 메일 콘텐츠 - 내용
        body = " <h4>안녕하세요업무도우미입니다. </h4>  </br><h4><h4>솔루션 주소 : http://222.110.22.168:8080/ioc/main.jsp </h4> <h4> 수행작업 : HX 파일(MD5, SHA1, SHA256) 및 결과보고서 생성</h4></br> <h4> </h4></br> 요청 건수(" + str(
            num3) + ")</br> 변환 건수(" + str(total) + ") 제외 건수(" + str(ex) + ")</br> 자세한 사항은 첨부파일을 참조해주세요</br> <h4>HX TOOL에 접속하여 업로드 해주세요(URL : \"https://192.168.36.182:8080/login\"</h4>"

        bodyPart = MIMEText(body, 'html', 'utf-8')
        message.attach(bodyPart)

        # 메일 콘텐츠 - 첨부파일
        attachments = [
            os.path.join(os.getcwd(), filename), os.path.join(os.getcwd(), filename2)
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

        logText = "작업번호 ["+str(jobno)+"] 메일 발송 완료(수신:"+realname+")"
        loglog(logText)

    except Exception as e:
        print(e)
    finally:
        if session is not None:
            session.quit()

    #################################################################################################################


############################## HX 데이터 파일 만들기 #################################################################
def writeHX(output, jobno):
    count = 1

    yy = datetime.today().strftime('%y')
    mm = datetime.today().strftime('%m')
    dd = datetime.today().strftime('%d')
    print(
        "###########################################[생성] HX 텍스트 작성 ######################################################")
    tmp = ""
    tmp2 = ""
    filename = "igloo_" + yy+mm+dd
    ioc = ""

    # 1줄 로직
    if (len(output) == 1) and (output[0] != '변환실패'):
        value = output[0]

        ioc1 = "{\"igloo\":{\"execution\":["
        ioc2 = "[{\"operator\":\"equal\",\"token\":\"processEvent/md5\",\"type\":\"md5\",\"value\":\"" + value + "\"}]"
        ioc3 = "],\"presence\":["
        ioc4 = "[{\"operator\":\"equal\",\"token\":\"fileWriteEvent/md5\",\"type\":\"md5\",\"value\":\"" + value + "\"}]"
        ioc5 = "],\"name\":\"" + filename + "\",\"category\":\"Custom\",\"platforms\":[\"win\",\"osx\"]}}"

        ioc = ioc1 + ioc2 + ioc3 + ioc4 + ioc5

        output[0] = ioc.strip()
        print("output 1 : " + output[0])

    # 2줄 이상 로직
    if len(output) >= 2:

        value = output[0]

        count = 0
        count2 = 0
        ioc1 = "{\"igloo\":{\"execution\":["

        for value in output:
            ioc2 = "[{\"operator\":\"equal\",\"token\":\"processEvent/md5\",\"type\":\"md5\",\"value\":\"" + value + "\"}]"
            count += 1

            if value == '변환실패':
                continue

            if len(output) == count:
                tmp = tmp + ioc2
            else:
                tmp = tmp + ioc2 + ","

        ioc2 = tmp

        ioc3 = "],\"presence\":["

        for value in output:
            ioc4 = "[{\"operator\":\"equal\",\"token\":\"fileWriteEvent/md5\",\"type\":\"md5\",\"value\":\"" + value + "\"}]"
            count2 += 1

            if value == '변환실패':
                continue

            if len(output) == count2:
                tmp2 = tmp2 + ioc4
            else:
                tmp2 = tmp2 + ioc4 + ","
        ioc4 = tmp2

        ioc5 = "],\"name\":\"" + filename + "\",\"category\":\"Custom\",\"platforms\":[\"win\",\"osx\"]}}"
        ioc = ioc1 + ioc2 + ioc3 + ioc4 + ioc5

    f = open(filename, 'w')

    print("writeHX IN" + str(list))
    f.write(ioc)
    f.close()

    return filename
        ##################################################################################################################

def writeExcel(jobip,jobdate,jobno):
    ##################################################################################################################

    print("######################################### 변환된 데이터를 excel 작성하기##############################################")
    excelfilename = "HX_DATA(MD5,SHA1,SHA256)_결과파일_" + str(jobno) + ".xlsx"
    wb = openpyxl.Workbook()
    sheet = wb.active

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    ########### 초기 엑셀 작성 세팅
    sql4 = "SELECT no, md5, sha256, sha1, ip, url FROM work_place WHERE status = '1' AND ipip = '" + str(
        jobip) + "' AND time = '" + str(jobdate) + "'"
    curq.execute(sql4)
    connq.close()

    sheet['A1'] = "no"
    sheet['B1'] = "md5"
    sheet['C1'] = "값"
    sheet['D1'] = "유형"
    sheet['E1'] = "비고"
    no = 1
    line = 2

    print("########### 액셀 내용 작성")
    for r in curq:
        print("row after : " + str(r[0]) + " " + r[1] + " " + r[2] + " " + r[3])

        # no
        sheet['A' + str(line)] = no

        # 원본데이터(sha1, sha256)
        if r[2] != 'X':
            sheet['B' + str(line)] = r[1]
            sheet['C' + str(line)] = r[2]
            sheet['D' + str(line)] = "sha256"

        if r[3] != 'X':
            sheet['B' + str(line)] = r[1]
            sheet['C' + str(line)] = r[3]
            sheet['D' + str(line)] = "sha1"

        # md5 데이터 입력 처리
        if r[2] == 'X' and r[3] == 'X':
            sheet['B' + str(line)] = r[1]
            sheet['C' + str(line)] = r[1]
            sheet['D' + str(line)] = "md5"

        if r[4] == 'X':
            sheet['B' + str(line)] = r[4]
            sheet['C' + str(line)] = r[4]
            sheet['D' + str(line)] = "ip"

        if r[5] == 'X':
            sheet['B' + str(line)] = r[5]
            sheet['C' + str(line)] = r[5]
            sheet['D' + str(line)] = "url"

        line += 1
        no += 1
    wb.save(excelfilename)
    return excelfilename


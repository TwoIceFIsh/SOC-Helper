import json
import multiprocessing
import time
from pprint import pprint

import openpyxl as openpyxl
import pymysql
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

countzero = 0

def virusSHA256(sha256List):

    count = 0
    result = []
    SHA256 = []

    print("########################################## Virus Total 검색 시도 ##############################################")
    for i in sha256List:
        time.sleep(15)
        try:
            url = 'https://www.virustotal.com/vtapi/v2/file/report'
            params = {'apikey': '645c62843256a387939a6ab31b55f4e9a409971cdfe488d78b97881443289e6a', 'resource': i}
            response = requests.get(url, params=params)

            SHA256 = response.json()


            if SHA256['md5'] != None:
                print(
                    "################################## 정상 데이터 조회 시 SHA1 스택 #############################################")
                result.append(SHA256['md5'])
                print("sha256 > md5 변환 완료 : "+SHA256['md5'])

                print("#################################### SHA256 > MD5 데이터 작성 ######################################")
                conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
                cur = conn.cursor()
                sql2 = "UPDATE work_place SET md5 = '" + SHA256['md5']+"'" + " WHERE sha256 = '" + i+"'"
                cur.execute(sql2)
                conn.commit()
                #####################################################################################################
                
                print("################################# MD5가 작성된 SHA256의 Status를 1로 변경 ###############################")
                sql2 = "UPDATE work_place SET status=1 WHERE sha256 = '"+i+"'"
                cur.execute(sql2)
                conn.commit()

                cur.fetchone()
                conn.close()
                #######################################################################################################
                
                print("#################################### 통계의 처리수를 증가 ################################################")
                cc = 1
                print("조회")
                conn3 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
                cur3 = conn3.cursor()

                sql3 = "SELECT count FROM site_status where no = 1"
                cur3.execute(sql3)

                for a in cur3:
                    cc = a[0]
                conn3.close()

                print("삽입")
                conn3 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                        charset='utf8')
                cur3 = conn3.cursor()
                sql4 = "UPDATE site_status SET count ='" + str(cc + 1) + "' where no = 1"
                cur3.execute(sql4)
                conn3.commit()
                conn3.close()

                ######################################################################################################
                print(
                    "####################################### 1 END####################################################")

        except KeyError:
            print("############################### 데이터 정상조회 실패 시에러처리 ######################################################")
            print("sha1List KeyError :" + i)

            print("#################################### SHA256 > 에러처리 데이터 작성 ######################################")
            conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            cur = conn.cursor()
            sql2 = "UPDATE work_place SET md5 = '변환실패" + "' WHERE sha256 = '" + i + "'"
            cur.execute(sql2)
            conn.commit()
            cur.fetchone()

            print("################################# 변환실패가 작성된 SHA256 Status를 1로 변경 ###############################")
            sql2 = "UPDATE work_place SET status=1 WHERE sha256 = '" + i + "'"
            cur.execute(sql2)
            conn.commit()

            cur.fetchone()
            conn.close()

            print("#################################### 통계의 처리수를 증가 ################################################")
            cc = 1
            conn3 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            cur3 = conn3.cursor()

            sql3 = "SELECT count FROM site_status where no = 1"
            cur3.execute(sql3)
            conn3.commit()

            for a in cur3:
                cc = a[0]
                print(cc)

            conn3.close()

            conn5 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            cur5 = conn5.cursor()
            sql5 = "UPDATE site_status SET count ='" + str(cc + 1) + "' where no = '1'"
            cur5.execute(sql5)
            conn5.commit()
            conn5.close()

            ####################################################################################################
            print("####################################### 1 END####################################################")
    return result


def virusSHA1(sha1List):
    count = 0
    result = []

    print("########################################## 1 Virus Total 검색 시도 ##############################################")
    for i in sha1List:
        time.sleep(15)

        try:
            url = 'https://www.virustotal.com/vtapi/v2/file/report'
            params = {'apikey': '645c62843256a387939a6ab31b55f4e9a409971cdfe488d78b97881443289e6a', 'resource': i}
            response = requests.get(url, params=params)

            SHA1 = response.json()

            print("################################## 2 정상 데이터 조회 시 SHA1 스택 #############################################")
            if SHA1['md5'] != None:
                result.append(SHA1['md5'])
                print("sha1 < md5 변환 완료 : " + SHA1['md5'])

                print("#################################### SHA1 > MD5 데이터 작성 ######################################")
                conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                       charset='utf8')
                cur = conn.cursor()
                sql2 = "UPDATE work_place SET md5 = '" + SHA1['md5'] + "'" + " WHERE sha1 = '" + i + "'"
                cur.execute(sql2)
                conn.commit()
                #####################################################################################################

                print("################################# 3 MD5가 작성된 SHA1의 Status를 1로 변경 ###############################")
                sql2 = "UPDATE work_place SET status=1 WHERE sha1 = '" + i + "'"
                cur.execute(sql2)
                conn.commit()

                cur.fetchone()
                conn.close()
                #######################################################################################################

                print("#################################### 4 통계의 처리수를 증가 ################################################")
                cc = 1
                conn3 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                        charset='utf8')
                cur3 = conn3.cursor()

                sql3 = "SELECT count FROM site_status where no = 1"
                cur3.execute(sql3)
                conn3.commit()

                for a in cur3:
                    cc = a[0]

                conn3.close

                conn3 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                        charset='utf8')
                sql4 = "UPDATE site_status SET count ='" + str(cc + 1) + "' where no = 1"
                cur3.execute(sql4)
                conn3.commit()
                conn3.close()
                #########################################################################################################
                print(
                    "####################################### 1 END####################################################")

        except KeyError:
            print(
                "############################### 데이터 정상조회 실패 시에러처리 ######################################################")

            print("sha1List KeyError :" + i)

            print("#################################### SHA1 > 에러처리 데이터 작성 ######################################")
            conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            cur = conn.cursor()
            sql2 = "UPDATE work_place SET md5 = '변환실패" + "' WHERE sha1 = '" + i + "'"
            cur.execute(sql2)
            conn.commit()
            cur.fetchone()


            print("################################# 변환실패가 작성된 SHA1의 Status를 1로 변경 ###############################")
            sql2 = "UPDATE work_place SET status=1 WHERE sha1 = '" + i + "'"
            cur.execute(sql2)
            conn.commit()
            cur.fetchone()
            conn.close()

            print("#################################### 통계의 처리수를 증가 ################################################")
            cc = 1
            conn3 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            cur3 = conn3.cursor()

            sql3 = "SELECT count FROM site_status where no = 1"
            cur3.execute(sql3)
            conn3.commit()

            for a in cur3:
                cc = a[0]

            conn3.close()

            conn3 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            cur3 = conn3.cursor()

            sql4 = "UPDATE site_status SET count ='" + str(cc + 1) + "' where no = 1"
            cur3.execute(sql4)

            conn3.commit()
            cur3.fetchone()
            conn3.close()

            print("####################################### 1 END####################################################")
    return result



def getList() :
    print("##################################### 데이터 추출, HX 파일 작성, 엑셀파일 이름##############################################")

    print("#######데이터 초기화")
    yy = datetime.today().strftime('%y')
    mm = datetime.today().strftime('%m')
    dd = datetime.today().strftime('%d')

    value = 0
    x = 1
    sha256 =[]
    md5 = []
    sha1 = []

    print("####################################### 입력된 데이터 md5, sha1, sha256 획득 ##########################################")
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sqlq = "SELECT md5, sha1, sha256, no FROM work_place WHERE (status = '0' AND MD5 != 'X') OR (status = '0' AND SHA1 != 'X') OR (status = '0' AND SHA256 != 'X')"
    curq.execute(sqlq)
    curq.close()

    for row in curq:

        if row[0] != 'X' :
            md5.append(row[0])


        if row[1] != 'X' :
            sha1.append(row[1])


        if row[2] != 'X' :
            sha256.append(row[2])

        if (row[0]!= 'X' and row[1] != 'X') or (row[0] != 'X' and row[2] != 'X'):
            print("###가공된 데이터 : " + row[0] + " " + row[1] + " " + row[2])
            continue
        print("###미가공 데이터 : " + row[0] + " " + row[1] + " " + row[2])

    ###################################################################################################################
    

    print("################################ 처리예정 데이터 갯 수 획득 및 저장 ##############################################")
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sqlq = "SELECT count(no) FROM work_place WHERE status = '0'"
    curq.execute(sqlq)
    curq.close()

    for row in curq:
        countzero = row[0]
        print(countzero)

    print("############################# 처리할 데이터 DB 작성 countzero "+str(countzero)+"#################################")

    connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curA = connA.cursor()
    sqlA = "UPDATE site_status SET c = '"+ str(countzero)+"' WHERE no = '1' "
    curA.execute(sqlA)
    connA.commit()
    connA.close()


    ####################################################################################################################

    print("################################ [상태] MD5 배열 길이를 site_status의 count에 가산 ##############################")
    cc = 1
    conn7 = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur7 = conn7.cursor()

    site_Status_count = 0
    md5_len = len(md5)

    sql7 = "SELECT count FROM site_status where no = 1"
    cur7.execute(sql7)


    for a in cur7:
        site_Status_count = a[0]

    if site_Status_count is None:
        site_Status_count = 0
    num = md5_len + site_Status_count
    sql4 = "UPDATE site_status SET count =" + str(num) + " where no = 1"
    cur7.execute(sql4)
    conn7.commit()
    conn7.close()

    print("################################ [상태] MD5 배열 길이를 site_status의 남은 작업에 차감 ##############################")
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sqlq = "UPDATE site_status SET c = " + str(countzero-md5_len)
    curq.execute(sqlq)
    connq.commit()
    curq.close()

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    for i in md5:
        sql4 = "UPDATE work_place SET status = '1' WHERE md5 = '" + i + "' and sha1 = 'X' and sha256 = 'X'"
        print(sql4)
        curq.execute(sql4)
        connq.commit()
    connq.close()

    ###################################################################################################################


    print("###########################[추출] Virustotal로 SHA1, SHA256 값을 MD5로 반환############################################")
    # MD5 배열
    md5List = md5
    sha256List =[]
    sha1List = []
    
    if len(sha256) > 0:
        out = virusSHA256(sha256)
        if len(out) > 0:
            sha256List = out

    if len(sha1) > 0:
        out = virusSHA1(sha1)
        if len(out) > 0:
            sha1List = out

    ###################### md5 결과에 대하여 status 변경  (sha,md5,sha256 status == 1)
    output = md5List + sha256List + sha1List
    print("output : " + str( output))



    ###################################################################################################################


    print("######################################### 변환된 데이터를 excel 작성하기##############################################")
    excelfilename = "HX_DATA(MD5,SHA1,SHA256)_결과파일_" + yy + mm + dd + ".xlsx"
    wb = openpyxl.Workbook()
    sheet = wb.active

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    ########### 초기 엑셀 작성 세팅
    sql4 = "SELECT no, md5, sha256, sha1 FROM work_place WHERE status = '1'"
    curq.execute(sql4)
    connq.close()

    line = 2
    sheet['A1'] = "no"
    sheet['B1'] = "md5"
    sheet['C1'] = "값"
    sheet['D1'] = "유형"
    sheet['E1'] = "비고"
    no = 1

    print("########### 액셀 내용 작성")
    for r in curq:
        print("row after : " + str(r[0]) + " " + r[1] + " " + r[2] + " " + r[3])

        # no
        sheet['A' + str(line)] = no

        # 변환데이터(md5)
        sheet['B' + str(line)] = r[1]

        # 원본데이터(sha1, sha256)
        if r[2] != 'X':
            sheet['C' + str(line)] = r[2]
            sheet['D' + str(line)] = "sha256"
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "UPDATE work_place SET status = '2' WHERE sha256 = '"+r[2]+"' AND no = '"+ str(r[0]) +"' "
            curq.execute(sql4)
            connq.commit()
            connq.close()

        if r[3] != 'X':
            sheet['C' + str(line)] = r[3]
            sheet['D' + str(line)] = "sha1"
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "UPDATE work_place SET status = '2' WHERE sha1 = '" + r[3] + "' AND no = '"+str(r[0]) +"' "
            curq.execute(sql4)
            connq.commit()
            connq.close()

        # md5 데이터 입력 처리
        if r[2] == 'X' and r[3] == 'X':
            sheet['D' + str(line)] = "md5"
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "UPDATE work_place SET status = '2' WHERE md5 = '" + r[1] + "' AND no = '"+ str(r[0]) +"' "
            curq.execute(sql4)
            connq.commit()
            connq.close()

        line += 1
        no += 1

    wb.save(excelfilename)
    ###################################################################################################################
    print("file writing complete")
    
   ###################################################################################################################

    print("###########################################[생성] HX 텍스트 작성 ######################################################")
    yy = datetime.today().strftime('%Y')
    mm = datetime.today().strftime('%m')
    dd = datetime.today().strftime('%d')
    tmp = ""
    tmp2 = ""
    filename = "igloo_"+yy+mm+dd
    ioc = ""

    #1줄 로직
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

    #2줄 이상 로직
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
        
    ###################################################################################################################

    ###### 추출된 데이터 총 길이, HX 파일 내용, 엑셀파일 이름
    result = [len(output), ioc, excelfilename]
    return result



def sendMail(filename,filename2, name, address, yy, mm, dd, line):
    print("############################################## 메일 보내기 ##########################################################")
    # 보내는사람

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    ########### 초기 엑셀 작성 세팅
    sql4 = "SELECT count(status), no FROM work_place WHERE status = '2'"
    curq.execute(sql4)
    connq.close()

    for r in curq:
        if r[0] >= 1:
            list = []
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
                message['Subject'] = "[보안관제] HX 정보등록 데이터(MD5, SHA1, SHA256) "+" " + str(line[1]) +"/"+str(line[0]) + "건_" +yy+mm+dd


                print( "요청 건수("+ str(line[0]) +") 변환 건수("+ str(line[1])+") 제외 건수"+str(line[0] - line[1])+")</br>")
                # 메일 콘텐츠 - 내용
                body = " <h4>안녕하세요업무도우미입니다. </h4>  </br><h4><h4>솔루션 주소 : http://222.110.22.168:8080/ioc/main.jsp </h4> <h4> 수행작업 : HX 파일(MD5, SHA1, SHA256) 및 결과보고서 생성</h4></br> <h4> </h4></br> 요청 건수("+ str(line[0]) +")</br> 변환 건수("+ str(line[1])+") 제외 건수("+str(line[0] - line[1])+")</br> 자세한 사항은 첨부파일을 참조해주세요</br> <h4>HX TOOL에 접속하여 업로드 해주세요(URL : \"https://192.168.36.182:8080/login\"</h4>"

                bodyPart = MIMEText(body, 'html', 'utf-8')
                message.attach(bodyPart)

                # 메일 콘텐츠 - 첨부파일
                attachments = [
                    os.path.join(os.getcwd(), filename),   os.path.join(os.getcwd(), filename2)
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

            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()

            ########### 초기 엑셀 작성 세팅
            sql4 = "UPDATE work_place SET status = '3' where status = '2'"
            curq.execute(sql4)
            connq.commit()
            connq.close()
    #################################################################################################################

############################## HX 데이터 파일 만들기 #################################################################
def writeHX(filename,list):
    count = 1
    if list[0] != 0:
        f = open(filename, 'w')
        print("writeHX IN" + str(list))
        f.write(list[1])
        f.close()
    return list[0]
##################################################################################################################


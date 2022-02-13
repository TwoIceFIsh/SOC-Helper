import time
from datetime import datetime
from datetime import datetime
import pymysql

import connect

line = 2

yy=datetime.today().strftime('%y')
mm=datetime.today().strftime('%m')
dd=datetime.today().strftime('%d')
count = 1

while 1:
    print("START")
    time.sleep(1)

    print("##################################### 이메일 발송을 위한 명단 획득 #######################################")
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                            charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT address FROM site_status WHERE no = 1"
    curq.execute(sql4)
    connq.close()

    ##### 메일 데이터가 없으면 기본팀메일 발송 #######
    for r in curq:
        if r[0] == None or r[0].strip() == "":
            address = 'dlz1160@s-oil.com'
        address = r[0]
    print("메일 수신대상 : " + address)

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT count(status) FROM work_place WHERE status = '0' AND ip ='X' AND url='X'"
    curq.execute(sql4)
    connq.close()
    print("################################# 처리 전인 데이터가 있는지 확인 ################################################")


    ##############################################################################################################


    for r in curq:
        if r[0] > 0:
            print(
                "################################################## 처리 가능한 데이터가 있음 ##########################################")
            print("###################### log log 처리 가능한 데이터를 얻는다 ########################")
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT address, time  FROM site_status WHERE no = '1'"
            curq.execute(sql4)
            connq.close()

            fromAddress = ""
            fromTime = ""
            fromIp = ""
            fromMail = ""
            fromCount = ""
            fromDateDate = ""

            fromFrom = ""
            fromTo = ""

            for rs in curq:
                fromAddress = rs[0]
                fromTime = rs[1]




            fromTime=fromTime.strip()
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT ip, mail, count, date  FROM log WHERE date = '" + str(fromTime)+"'"
            curq.execute(sql4)
            connq.close()



            for rs in curq:
                fromIp = rs[0]
                fromMail = rs[1]
                fromCount = rs[2]
                fromDateDate = rs[3]


######################################################################
            time.sleep(60)



            ####추출된 데이터 총 길이, HX 파일 내용, 엑셀파일 이름
            list = connect.getList(fromIp, fromMail, fromCount,fromDateDate)
            
            ### 파일내용이 공백이아닌지 확인
            if list[len(list) - 2] != "":
                print("DATA IN")

                ### HX 파일 생성 및 갯수 리턴 ###
                filename = "HX_DATA(MD5,SHA1,SHA256)_" + yy + mm + dd + ".hx"
                count = connect.writeHX(filename, list)

                asdf = []
                asdf.append(list[0])
                asdf.append(count)
                print(str(asdf))
                #######################################################################################################

                print("##################################### 메일 전송 ########################################################")
                name = '보안관제'
                print("count" + str(count))
                connect.sendMail(filename, list[2], name, address, yy, mm, dd, asdf, fromIp, fromMail, fromCount,fromDateDate)
                #######################################################################################################

    print("END")






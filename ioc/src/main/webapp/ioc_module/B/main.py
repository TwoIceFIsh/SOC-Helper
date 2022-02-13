import time
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

    #################################### 작업큐에 대기중인 장업이 있는지 확인 ################################
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT count(*) FROM jobq WHERE status = 0 AND type ='ioc' limit 1"
    curq.execute(sql4)
    yesyes = 0
    for rs in curq:
        if rs[0] > 0:

            print("############################### 작업큐에 작업 내용 불러오기 #############################")
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT * FROM jobq WHERE status = 0 AND type = 'ioc' limit 1"
            curq.execute(sql4)

            jobno = 0
            jobip = ""
            jobdate = ""
            jobstatus = 0
            jobtype = ""

            for i in curq:
                print(i)
                jobno = i[0]
                jobip = i[1]
                jobdate = i[2]
                jobstatus = i[3]
                jobtype = i[4]
                jobmail = i[5]

            connq.close()
            #################################
            time.sleep(60)
            ####추출된 데이터 총 길이, HX 파일 내용, 엑셀파일 이름
            list = connect.getList(jobip, jobdate)

            ### 파일내용이 공백이아닌지 확인
            if list[len(list) - 2] != "":
                print("DATA IN")

                ### HX 파일 생성 및 갯수 리턴 ###
                filename = "HX_DATA(MD5,SHA1,SHA256)_" + yy + mm + dd + ".hx"
                filename2 = list[2]
                count = connect.writeHX(filename, list)

                line = []
                line.append(list[0])
                line.append(count)
                print(str(line))
                #######################################################################################################

                print("##################################### 메일 전송 ########################################################")
                name = '보안관제'
                print("count" + str(count))

                ####filename,filename2, name, address, line , jobip, jobdate
                connect.sendMail(filename,filename2, name, jobmail, line, jobip, jobdate)
                #######################################################################################################

            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "UPDATE jobq SET status ='1' WHERE status = '0' AND ipip = '" + str(jobip) + "' AND time = '" + str(
                jobdate) + "'"
            print(sql4)
            curq.execute(sql4)
            connq.commit()
            connq.close()

    print("END")






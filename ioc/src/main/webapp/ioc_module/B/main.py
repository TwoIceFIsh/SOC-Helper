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

            realname = connect.mailCheck(jobmail)

            logText = "작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 진행 (To : " +str(realname)+ ")"
            print(logText)
            connect.loglog(logText)

            #################################
            time.sleep(60)
            ####추출된 데이터 총 길이, HX 파일 내용, 엑셀파일 이름
            v = connect.getList(jobno, jobip, jobdate)

            if v == 1:
                connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
                curq = connq.cursor()
                sql4 = "UPDATE jobq SET status ='1' WHERE status = '0' AND ipip = '" + str(jobip) + "' AND time = '" + str(
                    jobdate) + "'"
                print(sql4)
                curq.execute(sql4)
                connq.commit()
                connq.close()

    print("END")






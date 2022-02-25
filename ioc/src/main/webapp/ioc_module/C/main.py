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
    #print("START")
    time.sleep(1)
    print('#################################### 작업큐에 대기중인 장업이 있는지 확인 ################################')
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT count(*) FROM code WHERE c = '1' order by no desc limit 1"
    curq.execute(sql4)
    yesyes = 0
    time.sleep(5)
    for rs in curq:
        if rs[0] > 0:
            print("############################### 메일발송 작업 #############################")
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT * FROM code WHERE c = 1 limit 1"
            curq.execute(sql4)

            jobno = 0
            jobip = ""
            jobdate = ""
            jobstatus = 0
            jobtype = ""

            for i in curq:
                print(i)
                jobno = i[0]
                jobmail = i[1]
                jobcode = i[2]
                jobstatus = i[3]
            connq.close()
            connect.sendMail(jobmail, jobcode)


            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                    charset='utf8')
            curq = connq.cursor()
            sql4 = "UPDATE code SET c = 2 WHERE c = 1 and a = '" + str(jobmail)+"'"
            print(sql4)
            curq.execute(sql4)
            connq.commit()
            connq.close()








import time
from datetime import datetime
import pymysql
import connect
import subprocess
import os

line = 2
print('module find activated')
yy=datetime.today().strftime('%y')
mm=datetime.today().strftime('%m')
dd=datetime.today().strftime('%d')
count = 1

while 1:


    ######################################     PROGRAM HEART BEAT        ######################################
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT b FROM programs WHERE a = 'module_find'"
    curq.execute(sql4)
    pno = 0;
    for rs in curq:
        pno = rs[0]
    connq.close()

    pno += 1

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "UPDATE programs SET c = 0, b = '" + str(pno) + "' WHERE a = 'module_find'"
    curq.execute(sql4)
    connq.commit()
    connq.close()

    ############################################################################################################

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT * FROM programs WHERE no = '5'"
    curq.execute(sql4)
    pno = []
    pcount = []
    for rs in curq:
        pno.append(rs[0])
        pcount.append(rs[2])

    time.sleep(5)

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT * FROM programs WHERE no = '5'"
    curq.execute(sql4)
    mno = []
    mcount = []
    for rs in curq:
        mno.append(rs[0])
        mcount.append(rs[2])

    connq.close()

    for a in range(0, len(mno)):
        if pcount[a] == mcount[a]:
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                    charset='utf8')
            curq = connq.cursor()
            sql4 = "UPDATE programs SET c = 3 WHERE no = '5'"
            curq.execute(sql4)
            connq.commit()
            connq.close()



    #print("START")
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT count(*) FROM find WHERE b = '0' order by no desc limit 1"
    curq.execute(sql4)
    yesyes = 0
    for rs in curq:
        if rs[0] > 0:
            print("############################### 메일발송 작업 #############################")
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT * FROM find WHERE b = 0 limit 1"
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
                jobstatus = i[2]

            connq.close()

            # jobmail에 해당하는 정보획득
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT * FROM user WHERE a = '"+str(jobmail)+"'"
            curq.execute(sql4)

            jobno = 0
            jobip = ""
            jobdate = ""
            jobstatus = 0
            jobtype = ""
            jobpw = ""
            for i in curq:
                jobno = i[0]
                jobmail = i[1]
                jobpw = i[2]

            connq.close()

            ############################### MODULE STATUS CHANGE #####################################
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                    charset='utf8')
            curq = connq.cursor()
            sql4 = "UPDATE programs SET c = '1' WHERE a = 'module_find'"
            curq.execute(sql4)
            connq.commit()
            connq.close()

            connect.sendMail(jobmail, jobpw)


            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                    charset='utf8')
            curq = connq.cursor()
            sql4 = "UPDATE find SET b = 1 WHERE b = 0 and a = '" + str(jobmail)+"'"
            print(sql4)
            curq.execute(sql4)
            connq.commit()
            connq.close()








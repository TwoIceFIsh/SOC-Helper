import time
from datetime import datetime
import pymysql
import connect
import subprocess
import os


line = 2
print('module join activated')
yy=datetime.today().strftime('%y')
mm=datetime.today().strftime('%m')
dd=datetime.today().strftime('%d')
count = 1

def heartBeat(mudule_name):
    pno = 0;

    curq = connect.runDBselect("SELECT b FROM programs WHERE a = '"+mudule_name+"'")
    for rs in curq:
        pno = rs[0]

    pno += 1

    connect.runDBupdate("UPDATE programs SET c = 0 , b = '" + str(pno) + "' WHERE a = '"+mudule_name+"'")

    time.sleep(4)


while 1:

    ######################################     PROGRAM HEART BEAT        ######################################
    heartBeat('module_code')

    #print('#################################### 작업큐에 대기중인 장업이 있는지 확인 ################################')

    curq = connect.runDBselect("SELECT count(*) FROM code WHERE c = '1' order by no desc limit 1")

    yesyes = 0

    for rs in curq:
        if rs[0] > 0:
            time.sleep(4)
 
            curq = connect.runDBselect("SELECT * FROM code WHERE c = 1 limit 1")

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


            ############################### MODULE STATUS CHANGE #####################################

            connect.runDBupdate("UPDATE programs SET c = '1' WHERE a = 'module_code'")

            connect.sendMail(jobmail, jobcode)

            connect.runDBupdate("UPDATE code SET c = 2 WHERE c = 1 and a = '" + str(jobmail)+"'")






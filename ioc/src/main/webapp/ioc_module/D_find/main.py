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


def heartBeat(mudule_name):
    pno = 0;
    curq = runDBselect("SELECT b FROM programs WHERE a = '"+mudule_name+"'")
    for rs in curq:
        pno = rs[0]
    pno += 1
    runDBupdate("UPDATE programs SET c = 0 , b = '" + str(pno) + "' WHERE a = '"+mudule_name+"'")
    time.sleep(4)

while 1:


    ######################################     PROGRAM HEART BEAT        ######################################
    heartBeat('module_find')

    ############################################################################################################

    curq = connect.runDBselect("SELECT * FROM programs WHERE no = '5'")

    pno = []
    pcount = []
    for rs in curq:
        pno.append(rs[0])
        pcount.append(rs[2])

    time.sleep(5)

    curq = connect.runDBselect("SELECT * FROM programs WHERE no = '5'"
 
    mno = []
    mcount = []
    for rs in curq:
        mno.append(rs[0])
        mcount.append(rs[2])

 

    for a in range(0, len(mno)):
        if pcount[a] == mcount[a]:
             connect.runDBupdate("UPDATE programs SET c = 3 WHERE no = '5'")
 
    curq = connect.runDBselect("SELECT count(*) FROM find WHERE b = '0' order by no desc limit 1")

    yesyes = 0
    for rs in curq:
        if rs[0] > 0:
            print("############################### 메일발송 작업 #############################")

            curq = connect.runDBselect("SELECT * FROM find WHERE b = 0 limit 1")

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

            # jobmail에 해당하는 정보획득
            curq = connect.runDBselect("SELECT * FROM user WHERE a = '"+str(jobmail)+"'")

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

            ############################### MODULE STATUS CHANGE #####################################

            connect.runDBupdate("UPDATE programs SET c = '1' WHERE a = 'module_find'")


            connect.sendMail(jobmail, jobpw)

            connect.runDBupdate("UPDATE find SET b = 1 WHERE b = 0 and a = '" + str(jobmail)+"'")







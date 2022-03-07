import time
import pymysql
import connect
import subprocess
import os

line = 2

print('module cve activated')

def heartBeat(mudule_name):
    pno = 0;

    curq = connect.runDBselect("SELECT b FROM programs WHERE a = '"+mudule_name+"'")
    for rs in curq:
        pno = rs[0]

    pno += 1

    connect.runDBupdate("UPDATE programs SET c = 0 , b = '" + str(pno) + "' WHERE a = '"+mudule_name+"'")

    time.sleep(4)

while 1:

    heartBeat('module_cve')

    curq = connect.runDBselect("SELECT count(*) FROM jobq WHERE status = 0 AND type ='cve'  limit 1")

    yesyes = 0
    for rs in curq:
        if rs[0] > 0:
            print("############################### 작업큐의 내용 불러오기 #############################")
            curq = connect.runDBselect("SELECT * FROM jobq WHERE status = 0 AND type = 'cve' limit 1")

            jobno = 0
            fromIp = ""
            fromDateDate = ""
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


            print("############################### 작업큐의 작업 갯수 확인 #############################")

            curq = connect.runDBselect("SELECT count(no) FROM cve WHERE status = '0' AND ipip = '" + str(
                jobip) + "' AND time = '" + str(jobdate) + "'")

            for j in curq:
                num = j[0]

            if num > 0:

                ############################### MODULE STATUS CHANGE #####################################
                connect.runDBupdate("UPDATE programs SET c = '1' WHERE a = 'module_cve'")

                #####################################
                realname = connect.mailCheck(jobmail)
                connect.loglog("작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 진행 (To : " + str(
                    realname) + ")")
                time.sleep(30)

                v = connect.getList(jobno, jobip, jobdate)

                if v == 1:
                    connect.runDBupdate("UPDATE jobq SET status ='1' WHERE status = '0' AND ipip = '" + str(
                        jobip) + "' AND time = '" + str(
                        jobdate) + "'")


                    connect.loglog("작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 완료")


                if v == 9:
                    connect.runDBupdate("UPDATE jobq SET status ='9' WHERE status = '0' AND ipip = '" + str(
                        jobip) + "' AND time = '" + str(
                        jobdate) + "'")


                    connect.loglog("작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업실패(한글포함)")

                    ############################### MODULE STATUS CHANGE #####################################

                    connect.runDBupdate("UPDATE programs SET c = '0' WHERE a = 'module_cve'")

            else:

                connect.runDBupdate("UPDATE jobq SET status ='2' WHERE status = '0' AND ipip = '" + str(
                    jobip) + "' AND time = '" + str(
                    jobdate) + "'")

                connect.loglog("작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 실패(파일내용없음)")





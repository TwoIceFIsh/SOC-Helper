import time
import pymysql
import connect
import subprocess
import os
line = 2

print('module cve activated')

while 1:



    ######################################     PROGRAM HEART BEAT        ######################################
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT b FROM programs WHERE a = 'module_cve'"
    curq.execute(sql4)
    pno = 0;
    for rs in curq:
        pno = rs[0]
    connq.close()

    pno += 1

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "UPDATE programs SET c = 0, b = '"+ str(pno) +"' WHERE a = 'module_cve'"
    curq.execute(sql4)
    connq.commit()
    connq.close()

    ############################################################################################################

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT count(*) FROM jobq WHERE status = 0 AND type ='cve'  limit 1"
    curq.execute(sql4)
    yesyes = 0
    for rs in curq:
        if rs[0] > 0:
            print("############################### 작업큐의 내용 불러오기 #############################")
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT * FROM jobq WHERE status = 0 AND type = 'cve' limit 1"
            curq.execute(sql4)
        
            jobno=0
            fromIp=""
            fromDateDate=""
            jobstatus=0
            jobtype=""
        
            for i in curq:
                print(i)
                jobno = i[0]
                jobip = i[1]
                jobdate = i[2]
                jobstatus = i[3]
                jobtype = i[4]
                jobmail = i[5]


            connq.close()

            print("############################### 작업큐의 작업 갯수 확인 #############################")
            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT count(no) FROM cve WHERE status = '0' AND ipip = '" + str(
                    jobip) + "' AND time = '" + str(jobdate) +"'"
            curq.execute(sql4)
            connq.close()
            print(sql4)
            for j in curq:
                num = j[0]

            if num > 0:

                ############################### MODULE STATUS CHANGE #####################################
                connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                        charset='utf8')
                curq = connq.cursor()
                sql4 = "UPDATE programs SET c = '1' WHERE a = 'module_cve'"
                curq.execute(sql4)
                connq.commit()
                connq.close()


                #####################################
                realname = connect.mailCheck(jobmail)
                logText = "작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 진행 (To : " + str(
                    realname) + ")"
                print(logText)
                connect.loglog(logText)
                time.sleep(30)

                v = connect.getList(jobno, jobip, jobdate)

                if v == 1:
                    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                            charset='utf8')
                    curq = connq.cursor()
                    sql4 = "UPDATE jobq SET status ='1' WHERE status = '0' AND ipip = '" + str(
                        jobip) + "' AND time = '" + str(
                        jobdate) + "'"
                    print(sql4)
                    curq.execute(sql4)
                    connq.commit()
                    connq.close()

                    logText = "작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 완료"
                    connect.loglog(logText)
                    print(logText)

                if v == 9:
                    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                            charset='utf8')
                    curq = connq.cursor()
                    sql4 = "UPDATE jobq SET status ='9' WHERE status = '0' AND ipip = '" + str(
                        jobip) + "' AND time = '" + str(
                        jobdate) + "'"
                    print(sql4)
                    curq.execute(sql4)
                    connq.commit()
                    connq.close()

                    logText = "작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업실패(한글포함)"
                    print(logText)
                    connect.loglog(logText)

                    ############################### MODULE STATUS CHANGE #####################################
                    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                            charset='utf8')
                    curq = connq.cursor()
                    sql4 = "UPDATE programs SET c = '0' WHERE a = 'module_cve'"
                    curq.execute(sql4)
                    connq.commit()
                    connq.close()

            else:
                connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                        charset='utf8')
                curq = connq.cursor()
                sql4 = "UPDATE jobq SET status ='2' WHERE status = '0' AND ipip = '" + str(
                    jobip) + "' AND time = '" + str(
                    jobdate) + "'"
                print(sql4)
                curq.execute(sql4)
                connq.commit()
                connq.close()
                logText = "작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 실패(파일내용없음)"
                connect.loglog(logText)
                print(logText)




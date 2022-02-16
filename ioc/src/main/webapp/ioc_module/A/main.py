import time
import pymysql
import connect

line = 2



while 1:
    print("START")
    time.sleep(1)
    print("#################################### 작업큐에 대기중인 장업이 있는지 확인 ################################")
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT count(*) FROM jobq WHERE status = 0 AND type ='cve'  limit 1"
    curq.execute(sql4)
    yesyes = 0
    for rs in curq:
        if rs[0] > 0:
            print("############################### 작업큐에 작업 내용 불러오기 #############################")
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
            realname = connect.mailCheck(jobmail)

            logText = "작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 진행 (To : " + str(
                realname) + ")"
            print(logText)
            connect.loglog(logText)
            #####################################
            time.sleep(60)
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
                print(logText)

            elif v == 0:
                logText = "작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 실패(파일내용없응)"
                print(logText)

        print("END")
import time
from datetime import datetime
import pymysql
import subprocess
import os

line = 2

yy = datetime.today().strftime('%y')
mm = datetime.today().strftime('%m')
dd = datetime.today().strftime('%d')
count = 1
 


while 1:

    ######################################     INI        ######################################
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "UPDATE programs SET b = 0 WHERE b > 1000"
    curq.execute(sql4)
    connq.commit()
    connq.close()


    ######################################     PROGRAM HEART BEAT        ######################################
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT b FROM programs WHERE a = 'module_manager'"
    curq.execute(sql4)
    pno = 0;
    for rs in curq:
        pno = rs[0]
    connq.close()

    pno += 1

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "UPDATE programs SET c = 0, b = '" + str(pno) + "' WHERE a = 'module_manager'"
    curq.execute(sql4)
    connq.commit()
    connq.close()

    ############################################################################################################

    # print("START")
    time.sleep(1)

    ################################## status 0 program heart beat check ################################
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT count(*) FROM programs WHERE c = 0 "
    curq.execute(sql4)
    yesyes = 0
    for rs in curq:
        if rs[0] > 0:

            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT * FROM programs"
            curq.execute(sql4)
            pno = []
            pcount = []
            for rs in curq:
                pno.append(rs[0])
                pcount.append(rs[2])

            time.sleep(10)

            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT * FROM programs"
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
                    sql4 = "UPDATE programs SET c = 3 WHERE no = '" + str(pno[a]) + "'"
                    curq.execute(sql4)
                    connq.commit()
                    connq.close()








    #
    # ############################### MODULE STATUS CHANGE #####################################
    # connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
    #                         charset='utf8')
    # curq = connq.cursor()
    # sql4 = "UPDATE programs SET c = '1' WHERE a = 'module_manager'"
    # curq.execute(sql4)
    # connq.commit()
    # connq.close()








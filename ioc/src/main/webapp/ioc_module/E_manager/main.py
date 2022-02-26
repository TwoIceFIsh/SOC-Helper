import time
from datetime import datetime
import pymysql
import subprocess
import os

line = 2
print('module manager activated')
yy = datetime.today().strftime('%y')
mm = datetime.today().strftime('%m')
dd = datetime.today().strftime('%d')
count = 1

while 1:
    print('Process Heart Beat')
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
            sql4 = "SELECT * FROM programs WHERE c = 0"
            curq.execute(sql4)
            pno = []
            pcount = []
            for rs in curq:
                pno.append(rs[0])
                pcount.append(rs[2])

            time.sleep(6)

            connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
            curq = connq.cursor()
            sql4 = "SELECT * FROM programs WHERE c = 0"
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
                    sql4 = "UPDATE programs SET c = 3 WHERE no = '" + str(pno[a]) + "' AND c = 0"
                    curq.execute(sql4)
                    connq.commit()
                    connq.close()

                    if pno[a] == 1:
                        subprocess.call([str(os.getcwd()) + "\\..\\A_CVE\\recover_A.bat"])
                        print('module restart : \\A_CVE\\main.py ')
                    if pno[a] == 2:
                        subprocess.call([str(os.getcwd()) + "\\..\\B_IOC\\recover_B.bat"])
                        print('module restart : \\B_IOC\\main.py ')
                    if pno[a] == 3:
                        subprocess.call([str(os.getcwd()) + "\\..\\C_join\\recover_C.bat"])
                        print('module restart : \\C_join\\main.py ')
                    if pno[a] == 4:
                        subprocess.call([str(os.getcwd()) + "\\..\\D_find\\recover_D.bat"])
                        print('module restart : \\D_find\\main.py ')

    ############################### MODULE STATUS CHANGE #####################################
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                            charset='utf8')
    curq = connq.cursor()
    sql4 = "UPDATE programs SET c = '0' WHERE a = 'module_manager'"
    curq.execute(sql4)
    connq.commit()
    connq.close()







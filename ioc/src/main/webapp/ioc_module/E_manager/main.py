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

def heartBeat(mudule_name):
    pno = 0;
    curq = runDBselect("SELECT b FROM programs WHERE a = '"+mudule_name+"'")
    for rs in curq:
        pno = rs[0]
    pno += 1
    runDBupdate("UPDATE programs SET c = 0 , b = '" + str(pno) + "' WHERE a = '"+mudule_name+"'")
    time.sleep(4)

def runDBupdate(sql):
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    curq.execute(sql)
    connq.commit()

def runDBselect(sql):
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    curq.execute(sql)
    connq.commit()
    connq.close()

    return curq

while 1:
    print('Process Heart Beat')
    runDBupdate("UPDATE programs SET b = 0 WHERE b > 1000")

    heartBeat('module_manager')

    ################################## status 0 program heart beat check ################################

    curq = runDBselect("SELECT count(*) FROM programs WHERE c != 1 AND  a != 'module_manager'")

    yesyes = 0
    for rs in curq:
        if rs[0] > 0:
            #print(str(sql4) + " :  " + str(rs[0]))
            runDBupdate("UPDATE programs SET c = 1 WHERE a = 'module_manager'")

            curq = runDBselect("SELECT * FROM programs WHERE c != 1 and a != 'module_manager'")

            pno = []
            pcount = []
            for rs in curq:
                pno.append(rs[0])
                pcount.append(rs[2])

            time.sleep(10)

            curq = runDBselect("SELECT * FROM programs WHERE c != 1 and a != 'module_manager'")

            mno = []
            mcount = []
            for rs in curq:
                mno.append(rs[0])
                mcount.append(rs[2])

            a = 0
            if 0 < len(mno):

                if pcount[a] == mcount[a]:

                    runDBupdate("UPDATE programs SET c = 3 WHERE no = '" + str(pno[a]) + "' AND c = 0 AND a != 'module_manager'")

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
                a += 1
    ############################### MODULE STATUS CHANGE #####################################
    runDBupdate("UPDATE programs SET c = '0' WHERE a = 'module_manager'")




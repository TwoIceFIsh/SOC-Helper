import time
from datetime import datetime

import pymysql

import connect

line = 2

yy=datetime.today().strftime('%y')
mm=datetime.today().strftime('%m')
dd=datetime.today().strftime('%d')
count = 0

while 1:
    print("START")
    time.sleep(1)

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                            charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT address FROM site_status WHERE no = 1"
    curq.execute(sql4)
    connq.close()

    for r in curq:
        if r[0] == None or r[0].strip() == "":
            address = 'dlz1160@s-oil.com'
        address = r[0]
    print("메일 수신대상 : " + address)

    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    sql4 = "SELECT count(status) FROM work_place WHERE status = '0'"
    curq.execute(sql4)
    connq.close()

    for r in curq:
        if r[0] > 0:
            time.sleep(60)
            filename = "HX_DATA(IP, URL)_" + yy + mm + dd + ".txt"
            f = open(filename, 'w')

            list = connect.getList()

            if len(list) >= 1:
                time.sleep(60)
                print("DATA IN")

                for k in range(0,len(list)):
                    print("파일작성되는 list["+str(k)+"] : "+list[k])
                    f.write(list[k]+"\n")
                    count += 1

                f.close()



                name='보안관제'

                #connect.sendMail(filename,name,'bh.lee@s-oil.com',yy,mm,dd,count)
                connect.sendMail(filename, name, address, yy, mm, dd, count)
                #connect.sendMail(filename,name,'khw1205@s-oil.com',yy,mm,dd,count)
                #connect.sendMail(filename,name,'lyj0409@s-oil.com',yy,mm,dd,count)
                #connect.sendMail(filename,name,'ksm0117@s-oil.com',yy,mm,dd,count)
                #connect.sendMail(filename, name, 'sungwoo.kwon@s-oil.com', yy, mm, dd, count)

                count = 0
    print("END")
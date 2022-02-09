import time
from datetime import datetime
import connect

line = 2

yy=datetime.today().strftime('%y')
mm=datetime.today().strftime('%m')
dd=datetime.today().strftime('%d')
count = 1

while 1:
    print("START")
    filename = "HX_DATA(IP, URL)_" + yy + mm + dd + ".txt"
    time.sleep(5)
    f = open(filename, 'w')
    list = connect.getList()

    if len(list) >= 1:
        print("DATA IN")

        for k in range(0,len(list)):
            print("파일작성되는 list["+str(k)+"] : "+list[k])
            f.write(list[k]+"\n")
            count += 1

        f.close()

        name='보안관제'
        address='bh.lee@s-oil.com;khw1205@s-oil.com;lyj0409@s-oil.com;ksm0117@s-oil.com'
        connect.sendMail(filename,name,'bh.lee@s-oil.com',yy,mm,dd,count)
        #connect.sendMail(filename, name, 'dlz1160@s-oil.com', yy, mm, dd, count)
        #connect.sendMail(filename,name,'khw1205@s-oil.com',yy,mm,dd,count)
        #connect.sendMail(filename,name,'lyj0409@s-oil.com',yy,mm,dd,count)
        #connect.sendMail(filename,name,'ksm0117@s-oil.com',yy,mm,dd,count)
        #connect.sendMail(filename, name, 'sungwoo.kwon@s-oil.com', yy, mm, dd, count)

        count = 0

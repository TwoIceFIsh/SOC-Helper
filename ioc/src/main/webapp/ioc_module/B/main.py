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

    time.sleep(5)

    list = connect.getList()

    print("List in main value " + str(list))

    if list[len(list)-1] != "":
        print("DATA IN")
        filename = "HX_DATA(MD5,SHA1,SHA256)_" + yy + mm + dd + ".txt"
        count = connect.writeHX(filename, list)

        name='보안관제'
        address='bh.lee@s-oil.com;khw1205@s-oil.com;lyj0409@s-oil.com;ksm0117@s-oil.com'
        #connect.sendMail(filename,name,'bh.lee@s-oil.com',yy,mm,dd,count)
        #connect.sendMail(filename, name, 'dlz1160@s-oil.com', yy, mm, dd, count)
        #connect.sendMail(filename,name,'khw1205@s-oil.com',yy,mm,dd,count)
        #connect.sendMail(filename,name,'lyj0409@s-oil.com',yy,mm,dd,count)
        #connect.sendMail(filename,name,'ksm0117@s-oil.com',yy,mm,dd,count)
        #connect.sendMail(filename, name, 'sungwoo.kwon@s-oil.com', yy, mm, dd, count)



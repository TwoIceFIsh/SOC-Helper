import pymysql
import smtplib
from datetime import datetime
from email.utils import formataddr
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

def runDBupdate(sql):
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    curq.execute(sql) 
    connq.commit()
    connq.close()
    
def runDBselect(sql):
    connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curq = connq.cursor()
    curq.execute(sql) 
    connq.commit()
    connq.close()

    return curq

def loglog(logText):
    print(logText)

    curA = runDBselect("select MAX(no) from log")
    no = 1
    for rs in curA:
        if rs[0] != None and no > 0:
            no = rs[0]
        else:
            no = 1

    nowTime = datetime.today().strftime('%Y-%m-%d %H:%M:%S')

    runDBupdate("INSERT INTO LOG (no, text) values ('" + str(no + 1) + "','" + nowTime+" : " + logText + "')")



def sendMail(jobmail, jobpw):
    print(
        "############################################## 메일 보내기 ##########################################################")
    yy = datetime.today().strftime('%y')
    mm = datetime.today().strftime('%m')
    dd = datetime.today().strftime('%d')

    from_addr = formataddr(('SOCH', 'bh.lee@s-oil.com'))

    # 받는사람
    to_addr = formataddr(('', jobmail))

    session = None
    try:
        # SMTP 세션 생성
        session = smtplib.SMTP('smtp.gmail.com', 587)
        session.set_debuglevel(True)

        # SMTP 계정 인증 설정
        session.ehlo()
        session.starttls()
        session.login('igloosoil@gmail.com', 'lougwydyuijffjcd')

        # 메일 콘텐츠 설정
        message = MIMEMultipart("mixed")


        # 메일 송/수신 옵션 설정
        message.set_charset('utf-8')
        message['From'] = from_addr
        message['To'] = to_addr
        message['Subject'] = "[보안관제] SOC Helper 계정 비밀번호 전달"

        # 메일 콘텐츠 - 내용
        body = " <h4>안녕하세요 SOC Helper 비밀번호 전달드립니다.. </h4>  </br><h4><h4>솔루션 주소 : http://222.110.22.168:8080/ioc/main.jsp </h4> <h4> 비밀번호 : "+str(jobpw)+"</h4>"

        bodyPart = MIMEText(body, 'html', 'utf-8')
        message.attach(bodyPart)

        # 메일 콘텐츠 - 첨부파일

        # 메일 발송
        session.sendmail(from_addr, to_addr, message.as_string())

        loglog("작업["+str(jobno)+"] IOC 결과 메일 발송 완료(수신:"+realname+")")


    except Exception as e:
        print(e)
    finally:
        if session is not None:
            session.quit()

    #################################################################################################################

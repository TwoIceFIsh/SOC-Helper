import pymysql
import os
import smtplib
from datetime import datetime
from email import encoders
from email.utils import formataddr
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

output=[]
def getList(jobip, jobdate) :
    list = []
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT ip, url FROM work_place WHERE (status = '0' AND ip != 'X') OR (status = '0' AND url != 'X') AND ipip = '"+ str(jobip)+"' AND time = '" + str(jobdate)+ "'"
    cur.execute(sql)
    # 여러 줄 출력
    i = 0
    ip = []
    url = []
    for row in cur:

        if row[0] != 'X' :
            ip.append(row[0])


        if row[1] != 'X' :
            url.append(row[1])


        i += 1

    for o in range(0, len(list)):
        print("list "+list[o])

    list = ip+url
    sql2 = "UPDATE work_place SET status = '1' WHERE (status = '0' AND ip != 'X') OR (status = '0' AND url != 'X')AND ipip = '"+ str(jobip)+"' AND time = '" + str(jobdate)+ "'"
    cur.execute(sql2)
    conn.commit()

    # 한 줄 출력
    row = cur.fetchone()
    if row == None:
        print('검색결과없음')

    conn.close()

    ############################# loglog 메시지 입력 ###############################

    connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curA = connA.cursor()
    sqlA = "select MAX(no) from log"
    curA.execute(sqlA)
    connA.close()
    no = 1

    for rs in curA:
        if rs[0] != None:
            no = rs[0]

    no + 1
    ############## fromIp, fromMail, fromCount,fromDateDate #####


    asdfasdf = list

    nowTime = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    text = nowTime + " :  IOC(IP/URL) 데이터" + str(len(asdfasdf)) + "건 처리 진행."
    print("#####################" + text)
    connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curA = connA.cursor()
    sqlA = "INSERT INTO LOG (no, text) values ('" + str(no + 1) + "','" + text + "')"
    curA.execute(sqlA)
    connA.commit()
    connA.close()
    #######################################################################################################

    output = list

    # Stauts DB RW
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT count FROM site_status "
    cur.execute(sql)
    # 여러 줄 출력
    i = 0
    value = 0
    for row in cur:
        value = row[0]

    data = value + len(output)
    sql2 = "UPDATE site_status SET count = " + str(data)
    cur.execute(sql2)
    conn.commit()
    cur.fetchone()
    conn.close()



    ############################# loglog 메시지 입력 ###############################

    connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curA = connA.cursor()
    sqlA = "select MAX(no) from log"
    curA.execute(sqlA)
    connA.close()
    no = 1

    for rs in curA:
        if rs[0] != None:
            no = rs[0]

    no + 1
    ############## fromIp, fromMail, fromCount,fromDateDate #####

    asdfasdf = list
    nowTime = datetime.today().strftime('%Y-%m-%d %H:%M:%S')

    text = nowTime + " :  IOC(IP/URL) 데이터" + str(len(asdfasdf)) + "건 변환 완료."
    print("#####################" + text)
    connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curA = connA.cursor()
    sqlA = "INSERT INTO LOG (no, text) values ('" + str(no + 1) + "','" + text + "')"
    curA.execute(sqlA)
    connA.commit()
    connA.close()
    #######################################################################################################
    
    return output

def sendMail(filename, name, address, line , jobip, jobdate):
    print(
        "############################################## 메일 보내기 ##########################################################")

    yy = datetime.today().strftime('%y')
    mm = datetime.today().strftime('%m')
    dd = datetime.today().strftime('%d')

    ################ 이메일 카운트 증가 ##################
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT mailcount FROM site_status"
    cur.execute(sql)
    # 여러 줄 출력
    i = 0

    for row in cur:
        value = row[0]

    if value is None:
        value = 0

    sql2 = "UPDATE site_status SET mailcount = " + str(value + 1)
    cur.execute(sql2)
    conn.commit()
    conn.close()
    ###############################################################


    # 보내는사람
    from_addr = formataddr(('업무도우미', 'bh.lee@s-oil.com'))

    # 받는사람
    to_addr = formataddr((name, address))

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
        message['Subject'] = '[보안관제] ' + "HX 정보등록 데이터(IP, URL) "+ str(line) + "건_" +yy+mm+dd
        print('[보안관제] ' + "HX 정보등록 데이터(IP, URL) "+" " + str(line) + "건_" +yy+mm+dd)
        # 메일 콘텐츠 - 내용
        body = '''
        <h2>안녕하세요.</h1>
        <h4>업무도우미입니다. </h4>  
        </br><h4><h4>솔루션 주소 : "http://222.110.22.168:8080/ioc/main.jsp" </h4>  
        <h4> 수행작업 : EDR HX에 등록할 수 있는 데이터 파일 생성(IP, URL)</h4>   </br>
            <h4> </h4>
        </br>
        <h4>HX TOOL에 접속하여 업로드 해주세요(URL : "https://192.168.36.182:8080/login"</h4>  
        '''
        bodyPart = MIMEText(body, 'html', 'utf-8')
        message.attach(bodyPart)

        # 메일 콘텐츠 - 첨부파일
        attachments = [
            os.path.join(os.getcwd(), filename)
        ]

        for attachment in attachments:
            attach_binary = MIMEBase("application", "octect-stream")
            try:
                binary = open(attachment, "rb").read()  # read file to bytes

                attach_binary.set_payload(binary)
                encoders.encode_base64(attach_binary)  # Content-Transfer-Encoding: base64

                filename = os.path.basename(attachment)
                attach_binary.add_header("Content-Disposition", 'attachment', filename=('utf-8', '', filename))

                message.attach(attach_binary)
            except Exception as e:
                print(e)

        # 메일 발송
        session.sendmail(from_addr, to_addr, message.as_string())

        print('Successfully sent the mail!!!')

        ############################################## 상태변화 2 > 3 ####################################

        connq = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
        curq = connq.cursor()
        ########### 초기 엑셀 작성 세팅
        sql4 = "UPDATE work_place SET status = '3' where status = '2'AND ipip = '" + str(
            jobip) + "' AND time = '" + str(jobdate) + "' AND sha1 = 'X' AND md5 = 'X' AND sha256 = 'X'"
        curq.execute(sql4)
        connq.commit()
        connq.close()

        ############################# loglog 메시지 입력 ###############################

        connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
        curA = connA.cursor()
        sqlA = "select MAX(no) from log"
        curA.execute(sqlA)
        connA.close()
        no = 1

        for rs in curA:
            if rs[0] != None:
                no = rs[0]

        no + 1
        ##############fromIp, fromMail, fromCount,fromDateDate #####
        nowTime = datetime.today().strftime('%Y-%m-%d %H:%M:%S')

        if address == "DLZ1160@s-oil.com":
            realname = "보안관제팀"
        if address == "sungwoo.kwon@s-oil.com":
            realname = "부장님"
        if address == "jsh0119@s-oil.com":
            realname = "승환님"
        if address == "kmh0816@s-oil.com":
            realname = "명훈님"
        if address == "bh.lee@s-oil.com":
            realname = "병호님"
        if address == "ksm0117@s-oil.com":
            realname = "성민님"
        if address == "lyj0409@s-oil.com":
            realname = "예지님"
        if address == "khw1205@s-oil.com":
            realname = "형욱님"

        text = nowTime + " : IOC(IP/URL) 데이터 결과 " + str(realname) + "에게 발송 완료."

        connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc',
                                charset='utf8')
        curA = connA.cursor()
        sqlA = "INSERT INTO LOG (no, text) values ('" + str(no + 1) + "','" + text + "')"
        curA.execute(sqlA)
        connA.commit()

    except Exception as e:
        print(e)
    finally:
        if session is not None:
            session.quit()



#################################################################################################################
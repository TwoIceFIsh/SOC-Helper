import pymysql
import os
import smtplib
from datetime import datetime, time
from email import encoders
from email.utils import formataddr
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart


########(jobip, jobmail, jobdate)
def getList(jopip, jobdate):
    print("################ CVE 데이터 GET ###########################")
    result = []
    list = []
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT * FROM cve WHERE status = 0 AND ipip = '"+ str(jopip)+"' AND time = '" + str(jobdate)+ "'"
    print(sql)
    cur.execute(sql)
    # 여러 줄 출력
    i = 0

    for row in cur:
        list.append([])
        list[i].append(row[0])
        list[i].append(row[1])
        list[i].append(row[2])
        i += 1

    print(list)

    j = 0

    for j in range(0, len(list)):
        print(list[j][1])
        result.append(list[j][1])

    sql2 = "UPDATE cve SET status = '1' WHERE status = '0'AND ipip = '"+ str(jopip)+"' AND time = '" + str(jobdate)+ "'"
    cur.execute(sql2)
    conn.commit()

    print(result)

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

    asdfasdf = result


    nowTime = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    text = nowTime + " :  CVE 데이터" + str(len(asdfasdf)) + "건 처리 진행."
    print("#####################" + text)
    connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    curA = connA.cursor()
    sqlA = "INSERT INTO LOG (no, text) values ('" + str(no + 1) + "','" + text + "')"
    curA.execute(sqlA)
    connA.commit()
    connA.close()

    ######################################################################

    # Stauts DB RW
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT count FROM site_status"
    cur.execute(sql)
    # 여러 줄 출력
    i = 0
    value=0
    for row in cur:
        value = row[0]

    data = value + len(result)
    sql2 = "UPDATE site_status SET count = " + str(data)
    cur.execute(sql2)
    conn.commit()
    cur.fetchone()
    conn.close()

    return result

def sendMail(filename, name, address, line):
    # 보내는사람

    list = []
    conn = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
    cur = conn.cursor()
    sql = "SELECT mailcount FROM site_status"
    cur.execute(sql)
    # 여러 줄 출력
    i = 0
    value = 0
    for row in cur:
        value = row[0]

    sql2 = "UPDATE site_status SET mailcount = " + str(value + 1)
    cur.execute(sql2)
    conn.commit()
    conn.close()

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
        message['Subject'] = '[보안관제] ' + datetime.today().strftime(
            '%Y.%m') + "_CVE_CVSS_List(" + datetime.today().strftime('%y%m%d') + ")_백데이터 " + str(line-1) + "건"

        # 메일 콘텐츠 - 내용
        body = '''
        <h2>안녕하세요.</h1>
        <h4>업무도우미입니다. </h4>  
        </br><h4><h4>솔루션 주소 : "http://222.110.22.168:8080/ioc/main.jsp" </h4>  
        <h4> 수행작업 : CVE 기준 CVSS 정보 리포팅 파일 생성</h4>   </br>
            <h4> CVE 기준 검색을 통하여 발표일 CVSS 중요도 출처 및 설명을 xlsx로 출력</h4>
        </br>
        <h4>"\\\\192.168.20.12\\itsoc\\S-OIL보안관제PJT\\14.취약점관리\\CVE 취약점 정리 리스트"에 통합관리 요망</h4>  
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

        text = nowTime + " : CVE 데이터 결과 " + str(realname) + "에게 발송 완료."

        connA = pymysql.connect(host='localhost', user='root', password='!Hg1373002934', db='ioc', charset='utf8')
        curA = connA.cursor()
        sqlA = "INSERT INTO LOG (no, text) values ('" + str(no + 1) + "','" + text + "')"
        curA.execute(sqlA)
        connA.commit()
        connA.close()

    except Exception as e:
        print(e)
    finally:
        if session is not None:
            session.quit()

    #################################################################################################################

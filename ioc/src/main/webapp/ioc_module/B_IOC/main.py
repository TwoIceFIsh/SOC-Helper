import time
from datetime import datetime
import pymysql
import connect
import subprocess
import os

line = 2
print('module ioc activated')
yy = datetime.today().strftime('%y')
mm = datetime.today().strftime('%m')
dd = datetime.today().strftime('%d')
count = 1


def heartBeat(mudule_name):
	pno = 0

	curq = connect.runDBselect("SELECT b FROM programs WHERE a = '" + mudule_name + "'")
	for rs in curq:
		pno = rs[0]

	pno += 1

	connect.runDBupdate("UPDATE programs SET c = 0 , b = '" + str(pno) + "' WHERE a = '" + mudule_name + "'")

	time.sleep(4)


while 1:

	######################################	 PROGRAM HEART BEAT		######################################
	heartBeat('module_ioc')

	#################################### 작업큐에 대기중인 장업이 있는지 확인 ################################

	curq = connect.runDBselect("SELECT count(*) FROM jobq WHERE status = 0 AND type ='ioc' limit 1")

	for rs in curq:
		if rs[0] > 0:
			print("############################### 작업큐에 작업 내용 불러오기 #############################")

			jobno = 0
			jobip = ""
			jobdate = ""
			jobstatus = 0
			jobtype = ""

			curq = connect.runDBselect("SELECT * FROM jobq WHERE status = 0 AND type = 'ioc' limit 1")

			for i in curq:
				print(i)
				jobno = i[0]
				jobip = i[1]
				jobdate = i[2]
				jobstatus = i[3]
				jobtype = i[4]
				jobmail = i[5]
				jobfilename = i[6]

			print("############################### 작업큐의 작업 갯수 확인 #############################")
			time.sleep(30)
			curq = connect.runDBselect("SELECT count(no) FROM work_place WHERE status = '0' AND ipip = '" + str(
				jobip) + "' AND time = '" + str(jobdate) + "'")

			for j in curq:
				num = j[0]

			if num > 0:

				connect.runDBupdate("UPDATE programs SET c = '1' WHERE a = 'module_ioc'")
				connect.loglog(
					"작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 진행 (To : " + str(
						realname) + ")")

				####추출된 데이터 총 길이, HX 파일 내용, 엑셀파일 이름
				v = connect.getList(jobno, jobip, jobdate, jobfilename)

				if v == 1:
					onnect.runDBupdate(
						"UPDATE jobq SET status ='1' WHERE status = '0' AND ipip = '" + str(
							jobip) + "' AND time = '" + str(
							jobdate) + "'")

					connect.loglog("작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 완료")

				if v == 9:
					connect.runDBupdate("UPDATE jobq SET status ='9' WHERE status = '0' AND ipip = '" + str(
						jobip) + "' AND time = '" + str(
						jobdate) + "'")

					connect.loglog("작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업실패")




			else:
				connect.runDBupdate("UPDATE jobq SET status ='2' WHERE status = '0' AND ipip = '" + str(
					jobip) + "' AND time = '" + str(
					jobdate) + "'")
				print("작업[" + str(jobno) + "] " + str(jobip) + "님의 " + str(jobtype) + " 작업 실패(파일내용없음)")




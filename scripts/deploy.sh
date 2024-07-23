#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app

echo "> 현재 구동 중인 애플리케이션 PID 확인"
PORT=8080
CURRENT_PID=$(lsof -t -i:$PORT)

echo "현재 구동 중인 애플리케이션 PID: $CURRENT_PID"


if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -9 $CURRENT_PID"
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

# 배포할 새 애플리케이션(.jar 파일) 선택
echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR 파일 이름: $JAR_NAME"

# 새 애플리케이션 실행
echo "> $JAR_NAME 실행"
nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

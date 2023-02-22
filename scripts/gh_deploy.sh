#!/bin/bash

# 프로젝트 이름과 배포 경로를 설정합니다.
project_name="github_action"
deploy_path="/home/ubuntu/$project_name"

# 배포에 사용할 JAR 파일의 경로와 이름을 설정합니다.
jar_path="$deploy_path/calendar/build/libs/calendar-0.0.1-SNAPSHOT.jar"

# 로그 파일 경로를 설정합니다.
deploy_log_path="$deploy_path/deploy.log"
deploy_err_log_path="$deploy_path/deploy_err.log"
application_log_path="$deploy_path/application.log"

# 이전 어플리케이션의 PID를 저장할 변수를 초기화합니다.
current_pid=""

echo "===== 배포 시작 : $(date +%c) =====" >> "$deploy_log_path"

# 현재 동작중인 어플리케이션의 PID를 찾습니다.
current_pid=$(pgrep -f "$jar_path")

if [ -z "$current_pid" ]
then
  # 이전 어플리케이션이 없을 경우, 적절한 로그를 출력합니다.
  echo "> 현재 동작중인 어플리케이션이 없습니다." >> "$deploy_log_path"
else
  # 이전 어플리케이션을 종료합니다.
  echo "> 이전 어플리케이션 (PID: $current_pid)을 종료합니다." >> "$deploy_log_path"
  kill "$current_pid"

  # 이전 어플리케이션의 종료를 기다립니다.
  echo "> 이전 어플리케이션 종료를 기다립니다." >> "$deploy_log_path"
  sleep 10

  # 이전 어플리케이션 종료 여부를 확인합니다.
  current_pid=$(pgrep -f "$jar_path")
  if [ -n "$current_pid" ]
  then
    echo "> 이전 어플리케이션이 종료되지 않았습니다." >> "$deploy_log_path"
    exit 1
  fi
fi

# 새로운 어플리케이션을 배포합니다.
echo "> 새로운 어플리케이션 배포를 시작합니다." >> "$deploy_log_path"
nohup java -jar "$jar_path" >> "$application_log_path" 2>> "$deploy_err_log_path" &

# 어플리케이션 실행을 기다립니다.
echo "> 어플리케이션 실행을 기다립니다." >> "$deploy_log_path"
sleep 10

# 어플리케이션 실행 여부를 확인합니다.
current_pid=$(pgrep -f "$jar_path")
if [ -n "$current_pid" ]
then
  echo "> 어플리케이션이 성공적으로 실행되었습니다. (PID: $current_pid)" >> "$deploy_log_path"
  echo "> 배포가 완료되었습니다. : $(date +%c)" >> "$deploy_log_path"

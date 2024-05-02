#!/bin/bash

# 작업 디렉토리를 /home/ubuntu/app으로 변경
cd /home/ubuntu/app

# 환경변수 DOCKER_APP_NAME을 spring으로 설정
DOCKER_APP_NAME=spring

# 실행중인 blue와 green 확인
EXIST_BLUE=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml ps | grep Up)
EXIST_GREEN=$(sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml ps | grep Up)

# 배포 시작한 날짜와 시간을 기록
echo "배포 시작일자 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

# 둘 다 실행 중이지 않으면 blue를 시작하고 그렇지 않으면 green을 시작
if [ -z "$EXIST_BLUE" ] && [ -z "$EXIST_GREEN" ]; then

  # 로그 파일에 "blue 배포 시작"이라는 내용을 추가
  echo "blue 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  # docker-compose.blue.yml 파일을 사용하여 spring-blue 프로젝트의 컨테이너를 빌드하고 실행
  sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d --build

  # 30초 동안 대기
  sleep 30

  # 사용하지 않는 이미지 삭제
  sudo docker image prune -af

  # 로그 파일에 "blue 배포 완료"이라는 내용을 추가
  echo "blue 배포 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

# green이 실행 중이지 않으면 blue를 시작하고 green이 실행 중이면 blue를 중지하고 green을 시작
elif [ -z "$EXIST_GREEN" ]; then

  # 로그 파일에 "blue 배포 시작"이라는 내용을 추가
  echo "blue 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  # docker-compose.blue.yml 파일을 사용하여 spring-blue 프로젝트의 컨테이너를 빌드하고 실행
  sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d --build

  # 30초 동안 대기
  sleep 30

  # 사용하지 않는 이미지 삭제
  sudo docker image prune -af

  # 로그 파일에 "blue 배포 완료"이라는 내용을 추가
  echo "blue 배포 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

# 그렇지 않으면 blue를 중지하고 green을 시작
else
  # 로그 파일에 "blue 중단 시작"이라는 내용을 추가
  echo "blue 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  # docker-compose.blue.yml 파일을 사용하여 spring-blue 프로젝트의 컨테이너를 중지
  sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml down

  # 로그 파일에 "blue 중단 완료"이라는 내용을 추가
  echo "blue 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  # 로그 파일에 "green 배포 시작"이라는 내용을 추가
  echo "green 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

  # docker-compose.green.yml 파일을 사용하여 spring-green 프로젝트의 컨테이너를 빌드하고 실행
  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d --build

  # 30초 동안 대기
  sleep 30

  # 사용하지 않는 이미지 삭제
  sudo docker image prune -af

  # 로그 파일에 "green 배포 완료"이라는 내용을 추가
  echo "green 배포 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

fi

# 배포 종료 날짜와 시간을 로그 파일에 추가
echo "배포 종료일자 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

# 배포 완료 메시지를 로그 파일에 추가
echo "===================== 배포 완료 =====================" >> /home/ubuntu/deploy.log
echo >> /home/ubuntu/deploy.log

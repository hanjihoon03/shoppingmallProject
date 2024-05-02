#!/bin/bash

# 작업 디렉토리를 /home/ubuntu/app으로 변경
cd /home/ubuntu/app

# 환경변수 DOCKER_APP_NAME을 spring으로 설정
DOCKER_APP_NAME=spring

# 배포 시작일자를 기록
echo "배포 시작일자 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log

# blue 컨테이너가 실행 중인지 확인
EXIST_BLUE=$(sudo docker ps -qf "name=${DOCKER_APP_NAME}-blue")

# green 컨테이너가 실행 중인지 확인
EXIST_GREEN=$(sudo docker ps -qf "name=${DOCKER_APP_NAME}-green")

# blue 컨테이너가 실행 중이지 않으면 blue를 시작하고, 그렇지 않으면 green을 시작
if [ -z "$EXIST_BLUE" ]; then
  # blue 컨테이너가 실행 중이지 않으므로 blue를 시작합니다.
  echo "blue 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
  sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d --build
  # 여기에 필요한 추가 작업을 수행할 수 있습니다.
elif [ -z "$EXIST_GREEN" ]; then
  # green 컨테이너가 실행 중이지 않으므로 green을 시작합니다.
  echo "green 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d --build
  # 여기에 필요한 추가 작업을 수행할 수 있습니다.
else
  # blue와 green 컨테이너가 모두 실행 중이므로 blue를 중지하고 green을 시작합니다.
  echo "blue 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
  sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml down
  echo "blue 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
  echo "green 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d --build
  # 여기에 필요한 추가 작업을 수행할 수 있습니다.
fi

# 배포 종료일자를 기록
echo "배포 종료일자 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /home/ubuntu/deploy.log
echo "===================== 배포 완료 =====================" >> /home/ubuntu/deploy.log
echo >> /home/ubuntu/deploy.log

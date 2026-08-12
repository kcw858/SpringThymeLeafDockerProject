## Docker
- Application 실행환경을 이미지 표준화
- 운영체제 상관없이 동일한 내용을 실행 : 컨테이너

## 명령어
- sudo apt-get install ca-certificates curl gnupg lsb-release  => docker에 사용되는 도구를 가지고 온다
- curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg => 키등록
- echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null	=> 우분투 목록에 저장
- sudo apt update => apt update 실행
- sudo apt install docker-ce docker-ce-cli containerd.io => 필요한 도커 툴 설치
- sudo systemctl status docker => 도커 작동 확인
- sudo curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose  => docker-compose 설치
- sudo docker images -a => 이미지 생성여부 확인
- sudo docker ps -a  => 실행중 여부 확인 
- sudo docker build -t 이미지명 . => docker 이미지 생성
- dockerHub에 저장
  - sudo docker login -u 허브명(본인이름)
  - sudo docker tag my-app 허브명/my-app
  - sudo docker push 허브명/my-app
  - sudo docker pull 허브명/my-app
- sudo docker run --name my-app -it -d -p 8080:8080 이미지명 => 실행 (port는 8080)
- 종료
  - sudo docker ps -a
  - sudo docker stop pid(id)
  - sudo docker rm pid
- sudo docker compose up -d  => compose 실행
- sudo docker compose down	 => compose 끄기

### ※docker 사용 권한 주기
- sudo usermod -aG docker $USE (docker 사용 그룹 추가 / sudo 사용없이 docker에 접근이 가능)
#
# ⚙️ 환경
+ Sts 5.2.0
+ Java 21
+ git Action

# ▶️ 실행 과정
1. self-hosted로 실행
2. 프로젝트를 main브런치로 push
3. gradlew build
4. Doker이미지 생성 (docker build -t mini-app .)
5. 기존의 docker 프로세스가 작동중일 수 있으니 삭제
6. 새로운 docker 실행(컨테이너)
   

# 📂 yml파일
```
name: Deploy Git Action
# 언제부터 배포 시작 = master에서 push할때마다 배포
on:
  push:
    branches:
      - main
# 작업 지시 
jobs:
  deploy:
    runs-on: self-hosted #현재 Ubuntu에 배포 
    #어떤 순서로 진행할지 
    steps:
      # 소스 코드 다운로드 = clone과 동일 
      - uses: actions/checkout@v4
      # JDK 21버전 확인 
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'
      # gradlew 권한 부여
      - run: chmod +x gradlew
      
      #----------------------------------- 빌드 일반 java -jar
      #- run: ./gradlew clean build -x test
      # 실행 => jar파일 생성 => java -jar app.jar
      #- run: |
          #PID=$(lsof -t -i:8080 || true)
          #if [ -n "$PID" ]; then
             #kill -15 "$PID" || true
          #fi
      #- run: nohup java -jar build/libs/SpringThymeLeafDockerProject-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
        #env:
          #RUNNER_TRACKING_ID: ""
          
      #---------------------------------- 빌드 docker
      - run: ./gradlew clean build -x test
      # Doker이미지 생성
      - run: docker build -t mini-app .
      # 기존의 docker 프로세스 삭제
      - run: |
          docker stop mini-app || true
          docker rm mini-app || true
      # 새로운 docker 실행(컨테이너)
      - run: |
          docker run --name mini-app -d -p 8080:8080 mini-app
        env:
          RUNNER_TRACKING_ID: ""
```

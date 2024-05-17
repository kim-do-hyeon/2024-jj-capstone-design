## React를 사용한 거울 인터페이스

이 프로젝트는 리액트와 도커를 사용하여 거울 인터페이스를 만든다. 인터페이스는 웹 애플리케이션을 통해 다양한 기능을 제공한다. 이 프로젝트는 `docker-compose`를 사용하여 서브서버와 리액트 애플리케이션을 함께 빌드하고 실행할 수 있다.

## Docker 설정 및 파일

### Docker 설치

이 프로젝트를 실행하려면 시스템에 Docker가 설치되어 있어야 한다. 공식 [Docker 웹사이트](https://www.docker.com/get-started)에서 Docker를 다운로드하고 설치할 수 있다.

### Docker 파일

- Dockerfile.subserver : 서브서버를 실행하기 위한 파일

- Dockerfile.react : 리액트 애플리케이션을 실행하기 위한 파일

- .dockerignore : Docker가 무시해야 할 파일과 디렉토리를 지정해 놓은 파일

-  Docker Compose 파일 : `docker-compose.yml` 파일은 여러 컨테이너 Docker 애플리케이션을 정의하고 실행하는 데 사용


### 애플리케이션 실행

애플리케이션을 빌드하고 실행하려면 `docker-compose.yml` 파일이 있는 디렉토리(/Hardware)로 이동하여 다음 명령어를 실행한다.

```sh
docker-compose up --build
```

이 명령어는 Docker 이미지를 빌드하고 컨테이너를 시작한다. `--build` 옵션은 컨테이너를 시작하기 전에 이미지를 빌드하도록 강제한다.

이미지를 다시 빌드하지 않고 컨테이너를 시작하려면 다음 명령어를 실행한다.

```sh
docker-compose up
```

### 애플리케이션 중지

실행 중인 컨테이너를 중지하려면 `docker-compose`가 실행 중인 터미널에서 `CTRL+C`를 누르거나 다음 명령어를 실행한다.

```sh
docker-compose down
```

### 애플리케이션 접근

- 리액트 애플리케이션 : `http://localhost:3000`
- 서브서버 API : `http://localhost:5000`

### `up`과 `up --build`의 차이점

- `docker-compose up --build` : 컨테이너를 시작하기 전에 이미지를 빌드한다. Dockerfile 또는 종속성에 변경 사항이 있을 때 이 명령어를 사용한다.
- `docker-compose up` : 이미지를 빌드하지 않고 컨테이너를 시작한다. Dockerfile 또는 종속성에 변경 사항이 없는 경우 이 명령어를 사용한다. -> 이미지를 다시 빌드하지 않기 때문에 실행 시간이 단축될 수 있다.
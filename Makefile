.PHONY: all build docker volume db

DOCKER_TAG=latest
DOCKER_IMAGE:=$(USER)/jimmy-back:$(DOCKER_TAG)
DOCKER_PORT=8181

DB_VOLUME_NAME:=jimmy-db-data
DB_CONTAINER_NAME:=jimmy-db

all: build
clean:
	@./mvnw -V clean
build: target/jimmy-back.jar

target/jimmy-back.jar:
	@./mvnw -V clean package -Dmaven.test.skip=true

docker: target/jimmy-back.jar \
        src/main/docker/Dockerfile \
        src/main/docker/docker-entrypoint.sh \
        src/main/docker/docker-healthcheck.sh
	@docker image build -t $(DOCKER_IMAGE) --build-arg PORT=$(DOCKER_PORT) -f src/main/docker/Dockerfile .

volume:
	@docker volume create $(DB_VOLUME_NAME)
db: volume
	@docker container run --rm -d -ti -p 5432:5432 \
		--env-file .postgres-env \
		--name $(DB_CONTAINER_NAME) \
		-v $(DB_VOLUME_NAME):/var/lib/postgresql/data \
		postgres:10-alpine

.PHONY: all docker volume db

TAG=latest
IMAGE=$(USER)/jimmy-back:$(TAG)
PORT=8181

DB_VOLUME_NAME:=jimmy-db-data
DB_CONTAINER_NAME:=jimmy-db

all: docker

target/jimmy-back.jar:
	@./mvnw -V clean package -Dmaven.test.skip=true

docker: target/jimmy-back.jar
	@docker build -t $(IMAGE) --build-arg PORT=$(PORT) -f src/main/docker/Dockerfile .

volume:
	@docker volume create $(DB_VOLUME_NAME)
db: volume
	@docker container run --rm -d -ti -p 5432:5432 \
		--env-file .postgres-env \
		--name $(DB_CONTAINER_NAME) \
		-v $(DB_VOLUME_NAME):/var/lib/postgresql/data \
		postgres:10.5

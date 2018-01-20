.PHONY: all build docker run

TAG=latest
IMAGE=$(USER)/jimmy-back:$(TAG)

all: run

build:
	@./mvnw -V clean package -Dmaven.test.skip=true

docker: build
	@docker build -t $(IMAGE) -f src/main/docker/Dockerfile .

run: docker
	@docker run --rm -ti -p 8181:8181 --env-file .docker-env $(IMAGE)

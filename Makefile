.PHONY: all build docker run

TAG=latest
IMAGE=$(USER)/jimmy-back:$(TAG)
PORT=8181

all: run

build:
	@./mvnw -V clean package -Dmaven.test.skip=true

docker: build
	@docker build -t $(IMAGE) --build-arg PORT=$(PORT) -f src/main/docker/Dockerfile .

run: docker
	@docker run --rm -ti -p $(PORT):$(PORT) --env-file .docker-env $(IMAGE)

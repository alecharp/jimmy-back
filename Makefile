all: docker

build:
	@./mvnw -V clean package -Dmaven.test.skip=true

docker: build
	@docker build -t ${USER}/jimmy-back -f src/main/docker/Dockerfile .

.PHONY: build docker

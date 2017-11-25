all: build

build:
	@./mvnw -V clean package -Dmaven.test.skip=true

.PHONY: build

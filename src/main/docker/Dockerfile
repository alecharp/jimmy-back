FROM openjdk:8-jre-alpine
MAINTAINER Adrien Lecharpentier <me@alecharp.fr>

RUN adduser -h /var/jimmy -D -u 1000 jimmy jimmy
COPY --chown=jimmy:jimmy src/main/docker/docker-entrypoint.sh /var/jimmy/docker-entrypoint.sh

USER jimmy
ENV SPRING_PROFILES_ACTIVE prod

EXPOSE 8080
ENTRYPOINT ["sh", "/var/jimmy/docker-entrypoint.sh"]

COPY --chown=jimmy:jimmy target/jimmy-back.jar /var/jimmy/jimmy-back.jar

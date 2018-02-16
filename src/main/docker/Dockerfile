FROM openjdk:8-jre-alpine

RUN apk add --update --no-cache curl
RUN adduser -h /var/jimmy -D -u 1000 jimmy jimmy
COPY --chown=jimmy:jimmy src/main/docker/*.sh /var/jimmy/

USER jimmy
ENV SPRING_PROFILES_ACTIVE prod

ARG PORT=8080
EXPOSE ${PORT}

ENV PORT ${PORT}
ENTRYPOINT ["sh", "/var/jimmy/docker-entrypoint.sh"]
HEALTHCHECK --interval=15s --timeout=2s --retries=3 \
  CMD sh /var/jimmy/docker-healthcheck.sh ${PORT}

COPY --chown=jimmy:jimmy target/jimmy-back.jar /var/jimmy/jimmy-back.jar

FROM openjdk:11-jre

RUN apt-get install -y curl
RUN useradd -m -d /var/jimmy --uid 1000 jimmy
COPY --chown=jimmy:jimmy src/main/docker/*.sh /var/jimmy/
RUN chmod u+x /var/jimmy/*.sh

ENV TINI_VERSION v0.18.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /sbin/tini
RUN chmod +x /sbin/tini

USER jimmy
ENV SPRING_PROFILES_ACTIVE production

ARG PORT=8080
EXPOSE ${PORT}

ENV PORT ${PORT}
ENTRYPOINT ["/sbin/tini", "-g", "--"]
CMD ["/var/jimmy/docker-entrypoint.sh"]

HEALTHCHECK --interval=15s --timeout=2s --retries=3 \
  CMD sh /var/jimmy/docker-healthcheck.sh ${PORT}

COPY --chown=jimmy:jimmy target/jimmy-back.jar /var/jimmy/jimmy-back.jar

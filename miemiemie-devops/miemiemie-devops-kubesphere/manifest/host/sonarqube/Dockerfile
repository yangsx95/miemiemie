FROM sonarqube:9.4.0-community
USER root
RUN apk add --no-cache --upgrade 'zlib>=1.2.12-r1';
COPY ./gitlab.cer /usr/lib/jvm/java-11-openjdk/lib/security/
RUN keytool -import -keystore cacerts -file /usr/lib/jvm/java-11-openjdk/lib/security/gitlab.cer -storepass changeit -noprompt
USER sonarqube
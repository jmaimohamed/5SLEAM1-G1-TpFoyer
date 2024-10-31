
FROM openjdk:17-jdk-slim

EXPOSE 8089

# Variables pour Nexus (à adapter selon votre configuration)
ARG NEXUS_URL=http://10.0.2.15:8081
ARG REPO_PATH=repository/maven-releases/tn/esprit/tp-foyer/5.0.0/tp-foyer-5.0.0.jar
ARG NEXUS_USERNAME=admin
ARG NEXUS_PASSWORD=905ece24-6e26-4910-bbd1-cf5932d0ae9e
#thabet f nexus password

# Récupérer le livrable depuis Nexus et le renommer
RUN apt-get update && apt-get install -y curl && \
    curl -o tp-foyer-5.0.0.jar -u $NEXUS_USERNAME:$NEXUS_PASSWORD $NEXUS_URL/$REPO_PATH

# Commande pour lancer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/tp-foyer-5.0.0.jar"]
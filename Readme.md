# PJ SkillTrack

[//]: # (Informationen zum Projekt und zu getroffenen Architektur-/Technologieentscheidungen befinden sich unter `artefakte/*`.)

## Ausführung

Für die Ausführung von PJ SkillTrack gelten folgende Voraussetzungen:

- Java 17+
- PostgreSQL 16 mit angelegter Datenbank "pjskilltrack"
- unten aufgeführte Umgebungsvariablen sind verfügbar bzw. werden über den unten stehenden Java-Befehl eingebunden

1. Die beim Build erstelle JAR-Datei downloaden
2. Ausführen unter dem demo-Profil:

```shell
    export PG_HOST=localhost
    export ...
    java -jar <Pfad zur Jar-Datei> --spring.profiles.active=demo --server.port=<Portnummer(default ist 8080)>
```

### Anmeldung

Folgende Demo-Nutzer stehen bereit (alle Passwort "demo"):

- max.mueller@demo.org
- sara.schmidt@demo.org
- leon.meier@demo.org

## Konfiguration

Für den angegebenen PostgreSQL-Nutzer wird angenommen, dass dieser über die nötigen Lese- und Schreibrechte
für die angelegte "pjskilltrack"-Datenbank verfügt.

| Umgebungsvariable | Beschreibung               | Beispiel  |
|-------------------|----------------------------|-----------|
| PG_HOST           | Host des Datenbank-Servers | localhost |
| PG_PASSWORD       | PostgreSQL-Passwort        | postgres  |
| PG_USER           | PostgreSQL-Username        | postgres  |
| PG_PORT           | Port des DB-Servers        | 5432      |

<a href="https://aimeos.org/">
    <img src="https://www.capgemini.com/fr-fr/wp-content/themes/capgemini-komposite/assets/images/logo.svg" alt="Capgemini logo" title="Capgemini" align="right" height="60" style="border:none" />
</a>

Le projet consiste en la mise en place d'une application de gestion de panier, sur le principe des architectures Microservices. L’objectif est d’aboutir à une solution à l’état de l’art, performante et maintenable.

## Démarrer l'application SpringBoot
```
    mvn clean install spring-boot:run
```

## Démarrer en mode debug
Démarrer l'application avec la commande suivante
```
    mvn clean install spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"
```

Dans votre IDE préféré, créer une configuration de déboggage de type "Remote JVM Debug" avec l'option "Attach to remote JVM"

## URL d'accès
* Accéder à l'IHM HAL Explorer
http://localhost:<port>/

* Accéder à la documentation
http://localhost:<port>/swagger-ui/index.html

* Accéder à la console de la base H2 :
http://localhost:<port>/api/h2-console/



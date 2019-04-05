# TP 3 – Conception et réalistation d'une application répartie de gestion hiérarchique de logs

## Compilation

Dans le dossier _tp3-synthese_, au même niveau que le dossier _anneau_.

### Avec Make (recommandée)

1. `make build`
2. `make rmic`

### Sans Make

1. `javac anneau/tp3/*.java`
2. `rmic anneau.tp3.ServeurCentral; rmic anneau.tp3.ProgrammeSite; rmic anneau.tp3.GestionnaireAnneau`

## Classes

- `java anneau.tp3.ServeurCentral` ne prend pas d'arguments
- `java annneau.tp3.GestionnaireAnneau idSousReseau`
- `java anneau.tp3.ProgrammeSite idSite idSousReseau`

## Utilisation

Voici un scénario possible:

- 1 serveur central
- 3 sous-réseaux
- 3 sites par sous-réseau

Chaque commande doit être exécutée dans un nouveau terminal:

1. `rmiregistry`
2. `java anneau.tp3.ServeurCentral`
3. `java anneau.tp3.GestionnaireAnneau 0`
4. `java anneau.tp3.ProgrammeSite 0 0`
5. `java anneau.tp3.ProgrammeSite 1 0`
6. `java anneau.tp3.ProgrammeSite 2 0`
7. `java anneau.tp3.GestionnaireAnneau 1`
8. `java anneau.tp3.ProgrammeSite 0 1`
9. `java anneau.tp3.ProgrammeSite 1 1`
10. `java anneau.tp3.ProgrammeSite 2 1`
11. `java anneau.tp3.GestionnaireAnneau 2`
12. `java anneau.tp3.ProgrammeSite 0 2`
13. `java anneau.tp3.ProgrammeSite 1 2`
14. `java anneau.tp3.ProgrammeSite 2 2`

## Fonctionnalités

- Prise en charge des pannes
- Écriture sur un fichier `logs.txt` 
- Utilisation de `synchronized`
- Implémentatin de l'algorithme de Chang et Roberts

## Étudiants
- COLAS Adam
- EL MAJDOUBI Soukaina
- HAJJI Wadii
- HUSS Sylvain
# Qualité logicielle - Projet

## Tests JUnit (dossier `Camix_Java-v0.5/`)

Tous les TODOs ont été réalisés et se trouvent dans le sous-dossier `src/test/camix/service/`.
Il y a un fichier de test dédié pour chaque TODO numéroté : `Ex1`, `Ex2`...

## Tests JUnit générés par IA (dossier `Camix_Java-v0.5_IA/`)

Le bonus concernant la **génération de tests automatisée avec l'IA** a été réalisé dans le dossier `Camix_Java-v0.5_IA/`.

Les tests ont été générés via **Codex** (agent de génération de code). Les preuves de génération se trouvent dans :
- `AI_INTEGRATION_REPORT.md` : compte rendu complet avec analyse critique
- `CODEX_TIMELINE_2026-04-09.md` : timeline de la session Codex (75 commandes, 25 patches)
- `rollout-....jsonl` : trace brute de la session

Les tests couvrent les 5 TODOs (un fichier par TODO) avec les techniques vues en cours :
- `CanalChatAjouteClientTest.java` — TODO1 : AAA + Mockito
- `CanalChatAjouteClientNestedGwtTest.java` — TODO2 + TODO3 : GWT via `@Nested` + extension `@SkipOnFailureInEnclosingClass`
- `ServiceChatSupprimeCanalTest.java` — TODO4 : `assertThrows`
- `ClientChatChangeSurnomParameterizedTest.java` — TODO5 : réflexion + `@EnumSource` + `@MethodSource`

Les rapports d'exécution Maven Surefire se trouvent dans `target/surefire-reports/`.

---

## Tests Serveur avec JMeter (dossier `workspace-jmeter/`)

Les plans de test se trouvent dans le sous-dossier `test_plans/` :
- `test_plan-0.jmx` : Test initial réalisé avant l'implémentation des premiers TODOs.
- `test_plan-[1 à 3].jmx` : Plans de test validant les TODOs 1 à 3.
- `test_plan-charge.jmx` : Test de montée en charge du serveur.
- `test_plan-multi_user.jmx` : Test simulant les actions de plusieurs utilisateurs en simultané.

**Rapports d'exécution :**
- Les rapports bruts au format `JTL` se trouvent dans le sous-dossier `results/`.
- Les rapports générés au format `HTML` se trouvent dans le sous-dossier `outputs/`.

---

## Validation Système avec RobotFramework (dossier `Chat_TVS_RF-v0.6.todo/`)

Les 4 Critères d'acceptation de la User Story ont été réalisés et tous les TODOs ont été implémentés.

**Rapports d'exécution :** les résultats de test se trouvent dans le sous-dossier `report/`.

---

## Auteurs

- Thomas BATISTA  
- Salome GAUDUCHAU  
- Clovis SFEIR
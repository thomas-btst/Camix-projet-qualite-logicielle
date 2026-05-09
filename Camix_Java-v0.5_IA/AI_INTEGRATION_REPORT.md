# Compte rendu — intégration de tests générés par IA (Camix_Java v0.5)

## 1) Objectif d’évaluation

Critère évalué : **« Les tests sont issus d'une génération via une IA (avec justificatif de l'intégration de cette IA et du code de test généré). »**

L’idée ici n’est pas seulement de “faire des tests”, mais de montrer :
- **la trace de génération** (outil, contexte, fichiers produits),
- **l’intégration** dans le projet,
- **la vérification** par exécution réelle,
- et une **lecture critique** (par rapport au cours et au contexte technique).

---

## 2) Preuve de génération IA

- **Outil** : Codex (agent de génération de code)
- **Environnement** : Java 25.0.1, Maven 3.9.11, macOS 26.1 (aarch64)
- **Workspace** : `"/Users/clovis/Downloads/Camix_Java-v0.5_IA"`

### Livrables IA conservés
- **Rapport IA** : `AI_TEST_REPORT.md`
- **Traces de session** (si besoin en annexe) : `CODEX_SESSION_2026-04-09_clean.md`, `CODEX_ACTIVITY_LOG_2026-04-09.md`, `CODEX_TIMELINE_2026-04-09.md`, `rollout-....jsonl`

---

## 3) Ce que l’IA a généré (et ce que j’ai intégré)

### 3.1. Configuration JUnit (timeout global)

Fichier : `src/test/resources/junit-platform.properties`

- **Objectif** : faire échouer un test si son exécution dépasse 2 secondes.
- **Valeur** :

```properties
junit.jupiter.execution.timeout.default=2s
```

### 3.2. Tests générés (par TODO)

Les tests ci-dessous ont été générés et intégrés dans `src/test/java/camix/service/` :

| TODO | Cible | Fichier généré |
|---|---|---|
| TODO1 | `CanalChat.ajouteClient` (AAA + Mockito) | `CanalChatAjouteClientTest.java` |
| TODO2 | même scénario en GWT via `@Nested` | `CanalChatAjouteClientNestedGwtTest.java` |
| TODO3 (bonus) | dépendance hiérarchique (skip nested) | `SkipOnFailureInEnclosingClass.java`, `SkipOnFailureInEnclosingClassExtension.java` |
| TODO4 | `ServiceChat.supprimeCanal` : exception sur canal par défaut | `ServiceChatSupprimeCanalTest.java` |
| TODO5 | `ClientChat.changeSurnom` paramétré (`@EnumSource` + `@MethodSource`) | `ClientChatChangeSurnomParameterizedTest.java` |

### 3.3. Modification de production (testabilité)

Fichier : `src/main/java/camix/service/ServiceChat.java`

- **Problème** : un test unitaire ne doit pas déclencher d’I/O réseau (sinon on bascule vers du test d’intégration).
- **Solution IA** : ajout d’un constructeur **package‑private** `ServiceChat(String canalDefaut)` qui initialise l’état sans lancer le serveur, utilisé par le test TODO4.

---

## 4) Exécution et preuves

### 4.1. Commande d’exécution

Depuis la racine du projet :

```bash
cd "/Users/clovis/Downloads/Camix_Java-v0.5_IA"
mvn test
```

### 4.2. Rapports de tests

Les résultats Maven Surefire sont générés dans :
- `target/surefire-reports/`

Exemples de rapports présents :
- `TEST-camix.service.CanalChatAjouteClientTest.xml`
- `TEST-camix.service.CanalChatAjouteClientNestedGwtTest.xml`
- `TEST-camix.service.ServiceChatSupprimeCanalTest.xml`
- `TEST-camix.service.ClientChatChangeSurnomParameterizedTest.xml`

---

## 5) Analyse critique (validation du code généré)

Cette section est volontairement honnête : même si l’IA génère vite, il faut **valider** ce qui est produit (robustesse, couplage, choix de mocks, etc.).

### 5.1. Conformité au support de cours

Les éléments suivants sont conformes aux principes du support :
- **AAA / Four‑Phase Test** (Arrange‑Act‑Assert) utilisé sur TODO1 et TODO4.
- **GWT via `@Nested`** utilisé sur TODO2.
- **Tests paramétrés** (data‑driven) via `@EnumSource` et `@MethodSource` sur TODO5.
- **Substitution** : usage de Mockito (`@ExtendWith(MockitoExtension.class)`, `@Mock`, `when`, `verify`) sur TODO1/TODO2.
- **Extension JUnit** : bonus TODO3 via `ExecutionCondition` + `TestWatcher`.

### 5.2. Points perfectibles / fragilités des tests IA

1) **Mock d’une classe concrète `ClientChat` (extends `Thread`)**
- Les tests TODO1/TODO2 utilisent `@Mock ClientChat`.
- C’est parfois fragile selon l’environnement (Mockito/Byte Buddy, version Java, instrumentation).
- **Bonne pratique** : privilégier le mock d’interfaces ou de contrats minimaux (substitution).

2) **TODO5 utilise reflection pour tester une méthode `private`**
- Choix défendable (le support évoque reflection), mais **brittle**.
- Alternative plus simple/robuste : rendre la méthode package‑private et tester via l’accès packaged.

3) **Vérification `times(2)` / `times(3)` sur `donneId()`**
- Très “boîte blanche” : un refactor interne peut casser le test sans changer le comportement observable.
- Alternative plus robuste : assertions de comportement (taille du canal, présence) + `atLeastOnce()` si besoin.

---

## 6) Conclusion

Au final :
- Les tests ont bien été **générés via IA** (preuve : `AI_TEST_REPORT.md` + fichiers de tests).
- L’intégration est **fonctionnelle** : `mvn test` s’exécute et produit des rapports Surefire.

Mais cette expérience montre aussi une limite importante : **même quand l’IA a beaucoup de contexte (code + PDF + TODOs), elle ne “prévoit” pas la suite des événements**.
Concrètement, elle ne sait pas anticiper à 100% :
- les contraintes de l’environnement (ex. instrumentation Mockito/Byte Buddy selon la version de Java),
- les évolutions futures du code (refactors qui cassent des tests trop “boîte blanche”),
- ou la manière dont le projet va être exécuté sous différentes charges/conditions.

Donc la génération IA est très utile pour accélérer, mais elle doit rester **un point de départ** : une validation (et parfois des ajustements) par un humain reste nécessaire pour obtenir une suite de tests durable.


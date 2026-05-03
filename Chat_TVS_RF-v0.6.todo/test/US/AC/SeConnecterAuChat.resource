#
# Ressource RobotFramework pour le test de l'U.S. Se connecter au chat (Felix/Camix).
#
# Les critères d'acceptation (acceptance criteria) sont fournis sous formes de keywords.
# Les points de ces keywords (également des keywords) sont définis dans les
# ressources propres aux technologies d'implémentation (java ou android)
# (cf. SUT/).
#
# Fournit également les keywords nécessaires à d'autres critères d'acceptation
# dont les pré-conditions (given) dépendent de l'U.S. "Se connecter au chat".
#
# @version 0.6
# @author Matthias Brun
# @author Thomas BATISTA
# @author Salome GAUDUCHAU
# @author Clovis SFEIR
#

*** Settings ***

Resource    ../SUT/SUT.resource

*** Keywords ***

### Critères d'acceptation ###

Afficher vue connexion
    [Tags]    smoke
    When L'utilisateur lance le client chat
    Then Seule la vue connexion est affichée

    [Teardown]    l'utilisateur ferme le client chat

Se connecter au chat
    [Arguments]    ${autres utilisateurs}
    Given Le serveur chat est accessible @${IP_DEFAUT}:${PORT_DEFAUT}
    And Plusieurs ${autres utilisateurs} sont connectés au chat (@${IP_DEFAUT}:${PORT_DEFAUT}, canal par défaut)
    And L'utilisateur lance le client chat
    When L'utilisateur modifie l'adresse ${IP_DEFAUT}
    And L'utilisateur modifie le numéro du ${PORT_DEFAUT}
    And L'utilisateur demande à se connecter
    Then Un message informe l'utilisateur qu'une connexion est initiée à l'adresse ${IP_DEFAUT} et au ${PORT_DEFAUT} mentionnés
    And La vue connexion de l'utilisateur est fermée
    And La vue chat de l'utilisateur est affichée
    And L'utilisateur reçoit un message d'accueil
    And Tous les ${autres utilisateurs} reçoivent un message d'arrivée du nouvel utilisateur

    [Teardown]    Run keywords    l'utilisateur ferme le client chat
    ...           AND             plusieurs ${autres utilisateurs} ferment leur client chat
    ...           AND             le serveur chat est arrêté


Se connecter au chat - IP/port défaut
    [Arguments]    ${autres utilisateurs}
    Given Le serveur chat est accessible @${IP_DEFAUT}:${PORT_DEFAUT}
    And Plusieurs ${autres utilisateurs} sont connectés au chat (@${IP_DEFAUT}:${PORT_DEFAUT}, canal par défaut)
    And L'utilisateur lance le client chat
    When l'utilisateur demande à se connecter
    Then Un message informe l'utilisateur qu'une connexion est initiée à l'adresse ${IP_DEFAUT} et au ${PORT_DEFAUT} mentionnés
    And La vue connexion de l'utilisateur est fermée
    And La vue chat de l'utilisateur est affichée
    And L'utilisateur reçoit un message d'accueil
    And Tous les ${autres utilisateurs} reçoivent un message d'arrivée du nouvel utilisateur

    [Teardown]    Run keywords    l'utilisateur ferme le client chat
    ...           AND             plusieurs ${autres utilisateurs} ferment leur client chat
    ...           AND             le serveur chat est arrêté

Se connecter au chat - connexion impossible
    Given Le serveur chat n'est pas accessible @${IP_DEFAUT}:${PORT_DEFAUT}
    And L'utilisateur lance le client chat
    When L'utilisateur modifie l'adresse ${IP_DEFAUT}
    And L'utilisateur modifie le numéro du ${PORT_DEFAUT}
    And L'utilisateur demande à se connecter
    Then Un message informe l'utilisateur qu'une connexion est initiée à l'adresse ${IP_DEFAUT} et au ${PORT_DEFAUT} mentionnés
    And Après le délai de connexion, un message informe l'utilisateur que la connexion est impossible à l'adresse ${IP_DEFAUT} et au ${PORT_DEFAUT} mentionnés

    [Teardown]    l'utilisateur ferme le client chat


#
# Pré-conditions (given) dépendantes de l'U.S. "Se connecter au chat"
# nécessaires à d'autres critères d'acceptation.
#

# Given
L'${utilisateur} est connecté au chat (@${ip}:${port}, canal par défaut)
    Given L'${utilisateur} lance le client chat
    When L'${utilisateur} modifie l'adresse ${ip}
    And L'${utilisateur} modifie le numéro du ${port}
    And L'${utilisateur} demande à se connecter
    And L'${utilisateur} reçoit un message d'accueil

# Given
Plusieurs ${utilisateurs} sont connectés au chat (@${ip}:${port}, canal par défaut)
    FOR    ${utilisateur}    IN    @{utilisateurs}
        Given L'${utilisateur} est connecté au chat (@${ip}:${port}, canal par défaut)
    END
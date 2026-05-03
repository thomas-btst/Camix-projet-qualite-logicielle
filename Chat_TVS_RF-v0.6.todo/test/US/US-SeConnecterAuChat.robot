#
# Test RobotFramework du l'U.S. Se connecter au chat (Felix/Camix).
#
# @version 0.6
# @author Matthias Brun
# @author Thomas BATISTA
# @author Salome GAUDUCHAU
# @author Clovis SFEIR
#

*** Settings ***

Documentation      User Story : Se connecter au chat
...
...                [En tant qu'] utilisateur du chat
...                [Je dois pouvoir] saisir une adresse IP et un numéro de port
...                [Et] me connecter au chat
...                [Afin d'] entrer dans le chat à l'adresse IP et au port mentionnés
...
...                Business Rules :
...                Le délai de connexion est de 3 secondes (configuration).

Resource    AC/SeConnecterAuChat.resource


*** Variables ***

@{0 autres utilisateurs}    @{EMPTY}
@{2 autres utilisateurs}    premier    deuxième


*** Test Cases ***

AC - Afficher vue connexion
    [Tags]          smoke
    Afficher Vue Connexion

AC - Se connecter au chat
    [Template]    Se Connecter Au Chat
    ${0 autres utilisateurs}
    ${2 autres utilisateurs}

AC - Se connecter au chat - IP/port défaut
    [Template]    Se Connecter Au Chat - IP/port Défaut
    ${0 autres utilisateurs}
    ${2 autres utilisateurs}

AC - Se connecter au chat - connexion impossible
    Se Connecter Au Chat - Connexion Impossible
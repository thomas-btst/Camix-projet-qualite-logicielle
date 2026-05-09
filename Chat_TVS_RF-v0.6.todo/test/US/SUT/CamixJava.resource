#
# Ressource RobotFramework pour la manipulation du serveur chat Camix.
#
# Fournit les keywords pour le lancement et l'arrêt du serveur Camix.
#
# Les keywords sont implémentés avec la bibliothèque Process.
#
# @version 0.6
# @author Matthias Brun
#

*** Settings ***

Library    Process


*** Keywords ***

le serveur chat est accessible @${ip}:${port}
    Start Process    java    -Duser.language\=fr    -Duser.country\=FR    -jar    SUT/Camix_Java-0.6.jar    alias=Camix
    Sleep    1s
    Process Should Be Running    Camix    Le serveur Camix n'est pas accessible.
    Wait Until Keyword Succeeds    5x    1s    Verifier Port Ouvert    ${ip}    ${port}


le serveur chat n'est pas accessible @${ip}:${port}
    ${running}=    Run Keyword And Return Status    Process Should Be Running    Camix
    IF    ${running}
        Terminate Process    Camix
        Sleep    1s
    END
    ${accessible}=    Run Keyword And Return Status    Verifier Port Ouvert    ${ip}    ${port}
    Should Be Equal    ${accessible}    ${FALSE}    Un serveur Camix est encore accessible sur ${ip}:${port}


Verifier Port Ouvert
    [Arguments]    ${ip}    ${port}
    ${result}=    Run Process    python3    -c    import socket; s=socket.socket(); s.settimeout(1); s.connect(('${ip}', int('${port}'))); s.close()
    Should Be Equal As Integers    ${result.rc}    0    Le serveur n'est pas accessible sur ${ip}:${port}


Le serveur chat est arrêté
    ${OS} =    Evaluate    platform.system()    platform
    IF    '${OS}' == 'Windows'
        ${Camix PID} =    Get Process Id    Camix
        Run Process    taskkill    /F    /PID    ${Camix PID}
        # Run Process    taskkill /F /PID ${Camix PID}    shell=true
        # Run Process    Stop-Process    -Force    -ID    ${Camix PID}
        # Run Process    Stop-Process -Force -ID ${Camix PID}    shell=true
    ELSE
        Terminate Process    Camix
        # Terminate Process    Camix    kill=true
        # ${Camix PID} =    Get Process Id    Camix
        # Run Process    kill    ${Camix PID}
        # Run Process    kill ${Camix PID}    shell=true
    END
    Sleep    1s
    Process Should Be Stopped    Camix    Le serveur Camix n'est pas arrêté.


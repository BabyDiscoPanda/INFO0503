d'abord lancer le serveur :
    sur le dossier TP05 lancer cette commande (port utilisé 8000)
        php -S 127.0.0.1:8000

Ouvrir un navigateur en tapant l'adresse :
    localhost:8000
    (ou 127.0.0.1:8000)

en dessous du gros texte ce trouve un formulaire ( si vous ne le trouvez pas cliquer sur commande )

Si vous séléctionnez :
    tonne : 90
    pays : FRANCE
    CO2 : 30
    Type de bois : Bouleau
    Version : 1 ( cette case du formulaire n'est pas modifiable c'est juste pour informer l'utilisateur la version du logiciel si vous changez cette valeur cela ne va pas changer la valeur de version dans le json )

Votre sorti : 
    {"tonne":90,"pays":"FRANCE","cO2":30,"typeBois":"BOUL","version":1}

Si vous séléctionnez :
    tonne : 30
    pays : GERMANY
    CO2 : 200
    Type de bois : Chêne
    Version : 3 ( pour changer la version il faut aller enlever la balise disable mais vous n'êtes pas obliger car cela ne va pas changer la version du logiciel)

Votre sorti : 
    {"tonne":30,"pays":" GERMANY","cO2":200,"typeBois":"CHEN","version":1}

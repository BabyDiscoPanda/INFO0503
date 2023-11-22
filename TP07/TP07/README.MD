1) Tout d'abord compiler tous les .java (vous pouvez le faire à la barbare en faisant cette commande : )
    javac *.java

2) Ensuite lancer cette commande : 
    java GenerationClesRSA [fichier qui va contenir la clef privée] [fichier qui va contenir la clef publique]

3) Lancer d'abord le Serveur :
    java ServeurDechiffrement [fichier contenant la clef privé]

4) Lancer ensuite le Client :
    java ClientChiffrement [fichier contenant la clef publique]

5) Dans le terminal de Client vous devez selectionner :
    1 : Un pays
    2 : Un type de bois
    3 : Une tonne minimale (supérieur à 0)
    4 : Une consommation en CO2 (supérieur à 0)
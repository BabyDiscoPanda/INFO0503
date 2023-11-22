# Assurez vous d'avoir les droit d'écriture !
# Création d'un dossier src
mkdir src
# Compilation des codes
javac ServeurCompte.java -d src
javac ClientCompte.java -d src
# exec du serveur dans un terminal différent
gnome-terminal --window-with-profile="Serveur de Compte" -e "java ./src/ServeurCompte"
java ./src/ClientCompte
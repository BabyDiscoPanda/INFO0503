<?php
header("Content-type: application/json");

if(isset($_POST['login']) && isset($_POST['motDePasse'])) {
    if(($_POST['login'] == "toto") && ($_POST['motDePasse'] == "titi"))
        $tableau = [ "code" => "OK", "message" => "login correct" ];
    else{
        sleep(3);
        $tableau = [ "code" => "erreur", "message" => "login ou mot de passe incorrect" ];
    }
    }
        
else
    $tableau = [ "code" => "erreur", "message" => "données manquantes." ];

echo json_encode($tableau);
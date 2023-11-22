<?php
    include("Commande.php");
    $tonne = $_POST['tonne'];
    $pays = $_POST['pays'];
    $typeBoisTMP = $_POST['typeBois'];
    $cO2 = $_POST['cO2'];

    switch ($typeBoisTMP) {
        case 'BOUL':
            $typeBois = TypeBois::BOUL;
            break;
        case 'SAPI':
            $typeBois = TypeBois::SAPI;
            break;
        case 'CHEN':
            $typeBois = TypeBois::CHEN;
        break;
    }
    $monBois = new Bois($tonne,$pays,$cO2,$typeBois);
    $json = json_encode($monBois);
?>

<!DOCTYPE HTML>
<html>
    <head>
        <title> MON SUPER BOIS !</title>
    </head>
 <body>
 <p> VOILA VOTRE COMMANDE : <?php echo $json; ?></p><br>
 <p> 
<?php 
    if(isset($_POST['meuble'])){
        echo "JOLI MEUBLE !";
    } else{
        echo "WHERE IS MA MEUBLE ?";
    }

    echo "       ". $_POST['meuble1'];
?> </p><br>
 <a href="./"> Nouvelle commande</a>
    </body>
</html>
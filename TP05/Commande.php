<?php

include_once('TypeBois.php');

class Bois implements JsonSerializable
{
    public static int $version = 1;
    private int $tonne;
    private string $pays;
    private int $CO2;
    private TypeBois $typeBois;


    public function __construct(int $tonne,string $pays,int $CO2,TypeBois $typeBois) {
        $this->tonne = $tonne;
        $this->pays = $pays;
        $this->CO2 = $CO2;
        $this->typeBois = $typeBois;
    }

    public static function fromJson(string $encode_json) {
        $json = json_decode($encode_json,true);
        return new Bois($json['tonne'],$json['pays'],$json['cO2'],$json['typeBois']);
    }

    public function jsonSerialize() {
        return [
            'tonne' => $this->tonne,
            'pays' => $this->pays,
            'cO2' => $this->CO2,
            'typeBois' => $this->typeBois,
            'version' => Bois::$version
        ];
    }

    public function __toString()
    {
        return $this->tonne.    " "    .$this->pays." ".        $this->typeBois->value ." ".       $this->CO2;
    }

}

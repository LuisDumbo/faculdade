<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
require_once 'model/item.php';


$item = new item();

$redord = $item->read();
$date = json_decode(file_get_contents("php://input"));

$data_arr = array();





foreach ($redord as $record) {


  $dataCli = $record['data'];
  $hora = $record['hora'];


  if ($dataCli == $date->data) {

    $timeCli = date('H:i', strtotime($hora));
    $timeServer = date('H:i', strtotime($date->hora));

    ///se o tempo for manor do que o marcado
    if ($timeCli < $timeServer) {
      /*
      header('Content-type: application/json');
      echo json_encode("proximo", 200); */
      //ler("proximo");

      //noticio jornal
      
      $response = file_get_contents('https://servicodados.ibge.gov.br/api/v3/noticias/?de='.$dataCli.'');
      $response = json_decode($response);
      $veetor=$response->items;
      $val=rand(0,20);

      ///tempo
      $response = file_get_contents('https://api.hgbrasil.com/weather?woeid=1261906');
      $response = json_decode($response);
      $temp=$response->results;


      header('Content-type: application/json');
      echo json_encode(array("Noticia"=>$veetor[$val]->titulo,"tempo" => $temp->description) ,200);
      break;

    } 

  } else {
    header('Content-type: application/json');
    echo json_encode("sem reunião", 200);
    break;
  }

 
}
/*
  header('Content-type: application/json');
echo json_encode($date, 200); */
function ler($var)
{
  header('Content-type: application/json');

  echo json_encode($var, 200);
}

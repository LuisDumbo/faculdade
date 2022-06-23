<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
require_once 'model/item.php';


$item = new item();
$data = json_decode(file_get_contents("php://input"));

if (!empty($data->data) && !empty($data->hora)) {
    
    $dataCli =  $data->data;
    $hora = $data->hora;
    $return = $item->add($dataCli, $hora);
    http_response_code(201);
    echo var_dump($return); //json_encode(array("menssagem" => $hora));


    /*
    if ($reutn) {
        http_response_code(201);
        echo json_encode(array("menssagem" => "item criado com sucess0"));
    } else {
        http_response_code(503);
        echo json_encode(array("menssagem" => "erro ao criar"));
    } */
} else {
    http_response_code(400);
    echo json_encode(array("menssagem" => "valores errado"));
}

<?php
require_once(__DIR__ . '/../config/conection.php');

class  item extends conection
{


    public  function  add($data, $hora)
    {

        // $conn= new conecntion

        try {
            $conn = $this->connect();

            $users = $conn->prepare("insert into item (data, hora) values (:data, :hora)");
            $retun = $users->execute(array(
                ':data' => $data,
                ':hora' => $hora,
            ));

            /* if ($retun) {
                return true;
                # code...
            } */

            return  $retun;


            /// $users->execute($nome, $regiao, $valor);

            //$users->execute([$nome,$regiao,$valor]);


            return  $msg = "Cadastrado com sucesso!";
        } catch (PDOException $exception) {
            return $exception;
        }
    }

    public function read()
    {

        try {
            $conn = $this->connect();

            $users = $conn->query('select * from item');


            $list = $users->fetchAll(PDO::FETCH_ASSOC);


            return  $list;



            /// $users->execute($nome, $regiao, $valor);

            //$users->execute([$nome,$regiao,$valor]);


           // return  $msg = "Cadastrado com sucesso!";
        } catch (PDOException $exception) {
            return $exception;
        }
    }
}

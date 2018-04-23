<?php
	include 'config.php';

	function RandomString($length = 10) {

	    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	    $charactersLength = strlen($characters);
	    $randomString = '';
	    for ($i = 0; $i < $length; $i++) {
	        $randomString .= $characters[rand(0, $charactersLength - 1)];
	    }
	    return $randomString;
	}

	function status(){

		global $conn, $category_id, $notes, $date_begin, $date_end, $date_created, $id_beacon, $rand_id;

		$text = "Anda telah mengajukan Izin dengan keterangan $category_id - $notes untuk tanggal $date_begin sampai $date_end pada $date_created";

		$sql = "INSERT INTO `status`(`id`, `id_beacon`, `title`, `message`, `is_approved`, `date_modified`, `status_id`) VALUES (null, '$id_beacon', 'Permit', '$text', '0', '$date_created', '$rand_id')";
		$result = $conn->query($sql);

		return $result;

	}
	
	if ( !empty($_POST['date_begin']) && !empty($_POST['date_end']) && !empty($_POST['id_beacon']) && !empty($_POST['category_id']) ){

		$id_beacon = $_POST['id_beacon'];
		$category_id = $_POST['category_id'];
		$date_begin = $_POST['date_begin'];
		$date_end = $_POST['date_end'];
		if( !empty($_POST['notes'])){
			$notes = $_POST['notes'];
		} else {
			$notes = "";
		}
		
		date_default_timezone_set("Asia/Jakarta");
		$date_created = date("Y-m-d H:i:s");
		$rand_id = RandomString();
		
		$sql = "insert into employee_permit values(null, '$id_beacon', 'Permit', '$category_id', '$date_begin', '$date_end', '$notes', '0', '$date_created', '$date_created', '$rand_id')";
		$result1 = $conn->query($sql);
		$result2 = status();
		
		if($result1 && $result2){
			$json['status'] = true;
			$json['message'] = "data berhasil disimpan";
		} else {
			$json['status'] = false;
			$json['message'] = "data gagal disimpan";
		}
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($json);
?>
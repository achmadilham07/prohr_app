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

	function member(){

		global $conn, $title, $notes, $date_event, $partner, $date_created, $rand_id;

		$list_id_beacon = explode(",", $partner);
				
		$key = true;
		foreach ($list_id_beacon as $id) {
			$sql = "INSERT INTO `schedule` (`id`, `id_beacon`, `title`, `notes`, `level`, `date_event`, `partner`, `id_event`, `date_created`, `date_modified`) VALUES (NULL, '$id', '$title', '$notes', 'Member', '$date_event', '$partner', '$rand_id', '$date_created', '$date_created');";
			$result = $conn->query($sql);
			
			if($result && $key){
				$key = true;
			} else {
				$key = false;
			}
		}

		return $key;
	}
	
	if ( !empty($_POST['title']) && !empty($_POST['notes']) && !empty($_POST['date_event']) && !empty($_POST['id_beacon']) && !empty($_POST['partner']) ){

		$title = $_POST['title'];
		$notes = $_POST['notes'];
		$date_event = $_POST['date_event'];
		$id_beacon = $_POST['id_beacon'];
		$partner = $_POST['partner'];
		
		date_default_timezone_set("Asia/Jakarta");
		$date_created = date("Y-m-d H:i:s");
		$rand_id = RandomString();
		
		$sql = "INSERT INTO `schedule` (`id`, `id_beacon`, `title`, `notes`, `level`, `date_event`, `partner`, `id_event`, `date_created`, `date_modified`) VALUES (NULL, '$id_beacon', '$title', '$notes', 'Leader', '$date_event', '$partner', '$rand_id', '$date_created', '$date_created');";
		$result1 = $conn->query($sql);

		if ($partner == $id_beacon) {
			$result2 = $result1;
		} else {
			$result2 = member();	
		}	
	
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
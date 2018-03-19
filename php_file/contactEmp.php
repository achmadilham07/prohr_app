<?php
	include 'config.php';
	
	if ( $_POST['uid'] && $_POST['id_beacon'] ){
		$uid = $_POST['uid'];
		$id_beacon = $_POST['id_beacon'];
		
		$sql = "SELECT * FROM users_compa WHERE uid = '$uid' and id_beacon != '$id_beacon' ORDER by fullname ASC";
		$result = $conn->query($sql);
		
		if($result){
			$json['status'] = true;
			$json['message'] = "Berhasil";
			while($row = $result ->fetch_assoc()){
				$contact[] = $row;
			}
			$json['presence'] = $contact;
		} else {
			$json['status'] = false;
			$json['message'] = "data tidak bisa di umpan";
		}
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($contact, JSON_PRETTY_PRINT);
?>
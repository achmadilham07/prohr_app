<?php
	include 'config.php';
	
	
	$sql = "SELECT * FROM company ORDER by name_company ASC";
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
	
	echo json_encode($contact, JSON_PRETTY_PRINT);
?>
<?php
	include 'config.php';
	
	if ( !empty($_POST['id_beacon']) && !empty($_POST['year']) && !empty($_POST['month']) ){
		$id_beacon = $_POST['id_beacon'];
		$year = $_POST['year'];
		$month = $_POST['month'];
		
		$sql = "SELECT * FROM absensi WHERE id_beacon = '$id_beacon' and year = '$year' and month = '$month' ORDER by date DESC";
		$result = $conn->query($sql);
		
		if($result){
			$json['status'] = true;
			$json['message'] = "Berhasil";
			while($row = $result ->fetch_assoc()){
				$presence[] = $row;
			}
			$json['presence'] = $presence;
		} else {
			$json['status'] = false;
			$json['message'] = "data tidak bisa di umpan";
		}
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($presence, JSON_PRETTY_PRINT);
?>
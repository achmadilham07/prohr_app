<?php
	include 'config.php';
	
	if ( !empty($_POST['id_beacon']) ){
		$id_beacon = $_POST['id_beacon'];
		
		$sql = "SELECT * FROM schedule WHERE id_beacon = '$id_beacon' ORDER by date_event DESC";
		$result = $conn->query($sql);
		
		if($result){
			$json['status'] = true;
			$json['message'] = "data berhasil di select";
			while($row = $result ->fetch_assoc()){
				$schedule[] = $row;
			}
			$json['presence'] = $presence;
		} else {
			$json['status'] = false;
			$json['message'] = "data tidak berhasil di select";
		}
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($schedule, JSON_PRETTY_PRINT);
?>
<?php
	include 'config.php';
	
	if ( !empty($_POST['id_beacon']) && !empty($_POST['id_event']) ){
		$id_beacon = $_POST['id_beacon'];
		$id_event = $_POST['id_event'];

		$sql = "DELETE FROM `schedule` WHERE `id_event` = '$id_event' AND `id_beacon` = '$id_beacon' AND `level` = 'Leader'";
		$result = $conn->query($sql);
		
		if($result){
			$json['status'] = true;
			$json['message'] = "data berhasil di delete";
			// while($row = $result ->fetch_assoc()){
			// 	$presence[] = $row;
			// }
			// $json['presence'] = $presence;
		} else {
			$json['status'] = false;
			$json['message'] = "data tidak berhasil di delete";
		}
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($json, JSON_PRETTY_PRINT);
?>
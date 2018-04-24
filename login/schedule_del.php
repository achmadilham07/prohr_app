<?php
	include 'config.php';
	
	if ( !empty($_POST['id_beacon']) && !empty($_POST['id_event']) ){
		$id_beacon = $_POST['id_beacon'];
		$id_event = $_POST['id_event'];

		$sql1 = "SELECT * FROM `schedule` WHERE `id_event` = '$id_event' AND `id_beacon` = '$id_beacon' AND `level` = 'Leader'";
		$result1 = $conn->query($sql1);

		if ($result1->num_rows) {
			$sql2 = "DELETE FROM `schedule` WHERE `id_event` = '$id_event' ";
			$result2 = $conn->query($sql2);
			
			if($result2){
				$json['status'] = true;
				$json['message'] = "data berhasil di delete";
			} else {
				$json['status'] = false;
				$json['message'] = "data tidak berhasil di delete";
			}
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
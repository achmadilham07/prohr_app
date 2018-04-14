<?php
	include 'config.php';
	
	if ( !empty($_POST['id_beacon']) ){
		$id_beacon = $_POST['id_beacon'];
		
		$sql = "SELECT * FROM status WHERE id_beacon = '$id_beacon' ORDER BY date_modified DESC";

		$result = $conn->query($sql);

		if($result){
			$json['status'] = true;
			$json['message'] = "data berhasil disimpan";
			
			while($row = $result ->fetch_assoc()){
				$recently[] = $row;
			}

			$json['data'] = $recently;
		} else {
			$json['status'] = false;
			$json['message'] = "data gagal disimpan";
		}
		
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($json, JSON_PRETTY_PRINT);
?>
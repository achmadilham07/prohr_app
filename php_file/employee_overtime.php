<?php
	include 'config.php';
	
	if ( !empty($_POST['time_begin']) && !empty($_POST['time_end']) && !empty($_POST['id_beacon']) && !empty($_POST['date']) ){
		$id_beacon = $_POST['id_beacon'];
		$date = $_POST['date'];
		$time_begin = $_POST['time_begin'];
		$time_end = $_POST['time_end'];
		if( !empty($_POST['notes'])){
			$notes = $_POST['notes'];
		} else {
			$notes = "";
		}
		$date_created = date("Y-m-d");
		
		$sql = "insert into employee_overtime values(null, '$id_beacon', '$date', '$time_begin', '$time_end', '$notes', '0', '$date_created', '$date_created')";
		$result = $conn->query($sql);
		
		if($result){
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
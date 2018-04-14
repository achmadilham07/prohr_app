<?php
	include 'config.php';
	
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
		
		$date_created = date("Y-m-d h:i:s");
		
		$sql = "insert into employee_permit values(null, '$id_beacon', 'permit', '$category_id', '$date_begin', '$date_end', '$notes', '0', '$date_created', '$date_created')";
		$result = $conn->query($sql);
		
		if($result){
			$json['status'] = true;
			$json['message'] = "data berhasil disimpan";
		} else {
			$json['status'] = false;
			$json['message'] = $sql;//"data gagal disimpan";
		}
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($json);
?>
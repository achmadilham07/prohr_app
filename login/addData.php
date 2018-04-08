<?php
	include 'config.php';
	
	if ( !empty($_POST['name']) && !empty($_POST['age']) && !empty($_POST['email']) && !empty($_POST['password']) ){
		$name = $_POST['name'];
		$age = $_POST['age'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		$sql = "insert into data values(null, '$name', '$age', '$email', '$password')";
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
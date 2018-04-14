<?php
	include 'config.php';
	
	if ( !empty($_POST['id']) && !empty($_POST['entry']) ){
		$entry = $_POST['entry'];
		$id = $_POST['id'];
		
		switch ($entry) {
			case 'Permit':
				$sql = "DELETE FROM `employee_permit` WHERE `id` = '$id'";
				break;
			case 'Leave':
				$sql = "DELETE FROM `employee_leave` WHERE `id` = '$id'";
				break;
			case 'Overtime':
				$sql = "DELETE FROM `employee_overtime` WHERE `id` = '$id'";
				break;
			default:
				# code...
				break;
		}

		$result = $conn->query($sql);
		
		if($result){
			$json['status'] = true;
			$json['message'] = "data berhasil dihapus";
		} else {
			$json['status'] = false;
			$json['message'] = "data gagal dihapus";
		}
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($json);
?>
<?php
	include 'config.php';

	function compare_date_modified($a, $b){
		return strnatcmp( $b['date_created'], $a['date_created'] );
	}
	
	if ( !empty($_POST['id_beacon']) ){
		$id_beacon = $_POST['id_beacon'];
		$sql = "(SELECT id, entry, category_id, notes, date_created  FROM employee_permit WHERE id_beacon = 'BC9801' AND is_approved = '0' ORDER BY date_modified DESC) UNION (SELECT id, entry, category_id, notes, date_created  FROM employee_overtime WHERE id_beacon = 'BC9801' AND is_approved = '0' ORDER BY date_modified DESC) UNION (SELECT id, entry, category_id, notes, date_created  FROM employee_leave WHERE id_beacon = 'BC9801' AND is_approved = '0' ORDER BY date_modified DESC)";
		// $sql = array(
		// 	"SELECT * FROM employee_permit WHERE id_beacon = '$id_beacon' AND is_approved = '0' ORDER BY date_modified DESC",
		// 	"SELECT * FROM employee_leave WHERE id_beacon = '$id_beacon' AND is_approved = '0' ORDER BY date_modified DESC",
		// 	"SELECT * FROM employee_overtime WHERE id_beacon = '$id_beacon' AND is_approved = '0' ORDER BY date_modified DESC"
		// );

		// $check = true;
		// foreach($sql as $value){
		// 	$result = $conn->query($value);
			
		// 	if ($check and $result){
		// 		$check = true;
		// 		$resultArr[] = $result;
		// 	} else{
		// 		$check = false;
		// 	}
		// }

		$result = $conn->query($sql);

		if($result){
			$json['status'] = true;
			$json['message'] = "data berhasil disimpan";
			// echo $result -> num_rows;
			if ($result -> num_rows) {
				while($row = $result ->fetch_assoc()){
					$not_approved[] = $row;
				}

				usort($not_approved, 'compare_date_modified');
				$json['not_approved'] = $not_approved;
			} else {
				$not_approved = null;
				$json['not_approved'] = $not_approved;
			}
			
			
			// $cek_num_rows = 0;
			// foreach ($resultArr as $value) {
			// 	$num_rows = $value -> num_rows ;

			// 	if ($num_rows != 0){
			// 		while($row = $value ->fetch_assoc()){
			// 			$entry[] = $row;
			// 		}
			// 	}
			// 	$cek_num_rows = $cek_num_rows + $num_rows ;
			// }

			// if($cek_num_rows != 0){
			// 	usort($entry, 'compare_date_modified');
			// 	$json['data'] = $entry;
			// } else {
			// 	$json['data'] = null;
			// }

			
		} else {
			$json['status'] = false;
			$json['message'] = "data gagal disimpan";
		}
		
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($not_approved, JSON_PRETTY_PRINT);
?>
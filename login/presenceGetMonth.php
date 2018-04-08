<?php
	include 'config.php';
	
	if ( !empty($_POST['id_beacon']) && !empty($_POST['year']) ){
		$id_beacon = $_POST['id_beacon'];
		$year = $_POST['year'];
		$sql = "select DISTINCT month from absensi where id_beacon = '$id_beacon' and year = '$year' order by month desc";
		$result = $conn->query($sql);
		while($row = $result ->fetch_assoc()){
			$json[] = $row;
		}
	}
	
	echo json_encode($json, JSON_PRETTY_PRINT);
?>
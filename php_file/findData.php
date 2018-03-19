<?php
	include 'config.php';
	
	function check($uid, $conn){
		$cek = false;
		$sql = "select uid from company";
		$hasil = mysqli_query($conn, $sql);
		$i=0;
		while($fetch = mysqli_fetch_array($hasil)){
			$list_uid[$i] = $fetch['uid'];
			// echo $list_uid[$i] , "\n";
			if($uid == $list_uid[$i]){
				// echo $list_uid[$i] , " ini yang dipake\n";
				$cek = true; break;
			} else {
				$cek = false;
			}
			$i++;
		}
		
		return $cek;
	}
	
	if ( !empty($_POST['email']) && !empty($_POST['password']) ){
		$username = $_POST['email'];
		$password = $_POST['password'];
		
		list($uname, $uid) = explode("@", $username);
		
		if (check($uid, $conn) == true){
			$sql = "select * from users_compa where uname = '$uname' and uid = '$uid'";
		} else {
			$sql = "select * from users_compa where email = '$username'";
		}
		
		/* $sql = "select * from users_compa where email = '$username'"; */
		$fetch = mysqli_fetch_array($conn->query($sql));
		$numrows = $conn->query($sql)->num_rows;
		
		if ( $numrows ){
			if ( $fetch['password'] == $password ){
				$json['status'] = true;
				$json['message'] = "Berhasil Login";
				
				$json['users']['fullname'] = $fetch['fullname'];
				$json['users']['id_beacon'] = $fetch['id_beacon'];
				$json['users']['uname'] = $fetch['uname'];
				$json['users']['uid'] = $fetch['uid'];
				$json['users']['address'] = $fetch['address'];
				$json['users']['email'] = $fetch['email'];
				$json['users']['phoneNum'] = $fetch['phoneNum'];
				$json['users']['position'] = $fetch['position'];
				$json['users']['division'] = $fetch['division'];
				$json['users']['password'] = $fetch['password'];
				$birthday = "${fetch['birthday_place']}, ${fetch['birthday_date']}";
				$json['users']['birthday'] = $birthday;
				$json['users']['city'] = $fetch['city'];
				$json['users']['state'] = $fetch['state'];
				$json['users']['country'] = $fetch['country'];
				
				/* 
				$user['full_name'] = $fetch['full_name'];
				$user['alamat'] = $fetch['alamat'];
				$user['email'] = $fetch['email'];
				$user['telepon'] = $fetch['telepon'];
				$user['jabatan'] = $fetch['jabatan'];
				$user['divisi'] = $fetch['divisi'];
				$user['password'] = $fetch['password'];
				
				$json['users'] = json_encode($user);
				 */
			} else {
				$json['status'] = false;
				$json['message'] = "Password salah";
			}
		} else {
			$json['status'] = false;
			$json['message'] = "Email anda salah";
		}
	} else {
		$json['status'] = false;
		$json['message'] = "parameter tidak ada";
	}
	
	echo json_encode($json, JSON_PRETTY_PRINT);
?>
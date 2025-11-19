<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db", 3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$fname   = $_POST['fname'];
$lname   = $_POST['lname'];
$email   = $_POST['email'];
$phone   = $_POST['phone'];
$pass    = $_POST['password'];

$username = $fname . " " . $lname;
$role     = "User";
$status   = "Active";

$sqlCheck = "SELECT * FROM user_table WHERE email='$email'";
$resultCheck = $conn->query($sqlCheck);

$response = [];

if ($resultCheck->num_rows > 0) {
    $response["success"] = false;
    $response["message"] = "Account already exists";
} else {
    $sqlInsert = "INSERT INTO user_table(role,name,email,password,phone,Status,	
RegistrationDate
)VALUES ('$role','$username','$email','$pass','$phone','$status',now())";
    if ($conn->query($sqlInsert) === TRUE) {
        $userid = $conn->insert_id;

        $response["success"] = true;
        $response["message"] = "Registration successful";
        $response["user"] = [
            "userid"   => $userid,
            "username" => $username,
            "email"    => $email,
            "role"     => $role,
            "phone"    => $phone,
            "status"   => $status
        ];
    } else {
        $response["success"] = false;
        $response["message"] = "Registration failed: " . $conn->error;
    }
}

header('Content-Type: application/json');
echo json_encode($response);
$conn->close();
?>

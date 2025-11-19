<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db", 3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$email = $_POST['email'];
$password = $_POST['password'];
$role = $_POST['role'];

$response = [];

$sql = "SELECT * FROM user_table WHERE email='$email' AND password='$password'And Role='$role' LIMIT 1";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();

    $response["success"] = true;
    $response["message"] = "Login successful";
    $response["user"] = [
        "userid"   => $row["UserID"],
        "name" => $row["Name"],
        "email"    => $row["Email"],
        "role"     => $row["Role"],
        "phone"    => $row["Phone"],
        "status"   => $row["Status"]
    ];
} else {
    $response["success"] = false;
    $response["message"] = "Invalid email or password";
}

header('Content-Type: application/json');
echo json_encode($response);
$conn->close();
?>

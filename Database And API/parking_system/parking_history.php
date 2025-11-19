<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$action = $_POST['action'];
switch ($action){
    case 'User_History':

        $UserId =(int) $_POST['user_id'];
        $sql = "SELECT * from booking_history where UserId= $UserId";
        $result = $conn->query($sql);

        $slots = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $slots[] = $row;
            }
        }
        header('Content-Type: application/json');
        echo json_encode($slots);
        break;
    case 'Manager_History':
        $sql = "SELECT * from booking_history";
        $result = $conn->query($sql);

        $slots = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $slots[] = $row;
            }
        }
        header('Content-Type: application/json');
        echo json_encode($slots);
        break;
}
$conn->close();
?>
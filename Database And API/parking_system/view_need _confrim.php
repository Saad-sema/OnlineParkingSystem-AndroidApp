<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
// Sample query to fetch all slots
$sql = "SELECT booking_table.* ,payment_table.Status FROM booking_table ,payment_table 
                WHERE StartTime BETWEEN DATE_SUB(NOW(), INTERVAL 5 MINUTE) 
                                  AND DATE_ADD(NOw(), INTERVAL 15 MINUTE) 
                  AND booking_table.P_status = 'Pending' and booking_table.BookingID=payment_table.BookingID";
$result = $conn->query($sql);

$slots = [];
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $slots[] = $row;
    }
}
header('Content-Type: application/json');
echo json_encode($slots);
$conn->close();
?>
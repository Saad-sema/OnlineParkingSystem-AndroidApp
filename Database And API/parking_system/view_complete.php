<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
// Sample query to fetch all slots
$sql = "SELECT booking_table.* ,payment_table.Status FROM booking_table,payment_table WHERE booking_table.P_Status= 'Confirmed' and NOW() BETWEEN EndTime-INTERVAL 5 MINUTE AND EndTime+INTERVAL 10 MINUTE And booking_table.BookingID=payment_table.BookingID";
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
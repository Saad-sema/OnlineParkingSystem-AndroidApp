<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
// Sample query to fetch all slots
$sql = "SELECT b.*, p.ExtraCharge,payment_table.Status
FROM booking_table b
,parking_slot p,payment_table WHERE P_status = 'Confirmed'and b.EndTime + INTERVAL 10 MINUTE <= NOW() And b.SlotId = p.SlotId and b.BookingId=Payment_table.BookingID";
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
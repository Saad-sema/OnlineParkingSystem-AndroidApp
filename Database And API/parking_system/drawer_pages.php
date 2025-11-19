<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$UserId =(int)$_POST['userId'];
$sql = "SELECT booking_table.*, payment_table.Status,payment_table.ExtraCharge as PayExtraAmount,parking_slot.ExtraCharge FROM booking_table ,payment_table,parking_slot where booking_table.UserID = $UserId AND booking_table.bookingid = payment_table.bookingid and booking_table.SlotID=parking_slot.SlotID";
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
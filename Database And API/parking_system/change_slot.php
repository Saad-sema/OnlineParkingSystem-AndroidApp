<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$slotId = (int)$_POST['slotId'];
$bookingId = (int)$_POST['bookingId'];
$newEndTime = $_POST['newEndTime'];
$extraCharge = (int)$_POST['extraCharge'];
$slotName=$_POST['slotName'];
        $updateQuery = "UPDATE booking_table SET endtime = '$newEndTime',SlotId=$slotId,SlotName='$slotName' WHERE bookingid = $bookingId";
        $updateQuery2 = "UPDATE payment_table SET ExtraCharge = $extraCharge,Status = 'Pending' WHERE bookingid = $bookingId";

        if ($conn->query($updateQuery) === TRUE && $conn->query($updateQuery2) === TRUE) {
            echo json_encode([
                "status" => "updated",
                "bookingId" => $bookingId,
                "newEndTime" => $newEndTime
            ]);
        } else {
            echo json_encode([
                "status" => "error",
                "message" => "Failed to update booking."
            ]);
}
 $conn->close();
 ?>
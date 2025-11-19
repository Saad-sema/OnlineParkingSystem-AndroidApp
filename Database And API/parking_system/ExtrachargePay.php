<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$bookingId = (int)$_POST['bookingId'];  
$extraCharge = (int)$_POST['extraCharge'];
$updateQuery = "UPDATE payment_table SET Amount = Amount + $extraCharge,Status = 'Success' WHERE bookingid = $bookingId";
$updateQuery2="UPDATE payment_table SET ExtraCharge = null WHERE bookingid = $bookingId";
if ($conn->query($updateQuery) === TRUE &&  $conn->query($updateQuery2) === TRUE) {
    echo json_encode([
        "status" => "updated",
        "bookingId" => $bookingId,
        "extraCharge" => $extraCharge
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Failed to update booking."
    ]);
}
$conn->close();
?>
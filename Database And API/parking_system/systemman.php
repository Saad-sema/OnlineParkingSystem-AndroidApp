<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
  
  $sql = "SELECT b.username, b.slotname, b.VehicleNo, b.starttime, b.endtime, b.PhoneNo, p.Status 
                FROM booking_table b
                INNER JOIN payment_table p ON b.PaymentId = p.PaymentID";
        $result = $conn->query($sql);

        $viewParking = [];
        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $viewParking[] = $row;
            }
        }
        header('Content-Type: application/json');
        echo json_encode($viewParking);

$conn->close(); // Always close connection at the end
?>

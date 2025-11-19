<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$entry =$_POST['entry'];
$exit  = $_POST['exit'];

$sql ="SELECT ps.* 
        FROM parking_slot ps
        WHERE ps.slotId NOT IN (
            SELECT b.slotId 
            FROM booking_table b
            WHERE (b.starttime < '$exit') 
              AND (b.endtime > '$entry')
        )";
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
<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
header('Content-Type: application/json');
$action =$_POST['action'];
switch($action){
    case 'fetchEarnings':
        $sql = "SELECT sum(Amount)As result  FROM `payment_table`";
        $result = $conn->query($sql);
        $Earnings = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $Earnings[] = $row;
            }
        }
        echo json_encode($Earnings);
        break;
    case 'fetchSlots':
         $sql = "SELECT COUNT(slotid)As result from parking_slot";
        $result = $conn->query($sql);
        $slots = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $slots[] = $row;
            }
        }
        echo json_encode($slots);
        break;
     case 'fetchCompleteBookings':
         $sql = "SELECT COUNT(b_history_id)As result from booking_history";
        $result = $conn->query($sql);
        $cmpBookings = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $cmpBookings[] = $row;
            }
        }
        echo json_encode($cmpBookings);
        break;
    case 'fetchUsers':
        $sql="SELECT COUNT(userid)As result from user_table where STATUS='Active'";
        $result = $conn->query($sql);
        $totalUser = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $totalUser[] = $row;
            }
        }
        echo json_encode($totalUser);
        break;
    case 'fetchBookings':
        $sql="SELECT COUNT(bookingid)As result from booking_table";
        $result = $conn->query($sql);
        $totalBookings = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $totalBookings[] = $row;
            }
        }
        echo json_encode($totalBookings);
        break;
        default:
             echo json_encode(["success" => false, "message" => "Invalid action"]);  
}
$conn->close();
?>

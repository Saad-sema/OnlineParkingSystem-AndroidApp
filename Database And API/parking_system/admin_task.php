<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
header('Content-Type: application/json');
$action = $_POST['action'];

switch($action){
    case 'fetchSlot':
        $sql = "SELECT * FROM Parking_Slot";
        $result = $conn->query($sql);
        $slots = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $slots[] = $row;
            }
        }
        echo json_encode($slots);
        break;
    case 'addSlot':
        $slotName = $_POST['slotName'];
        $slotLocation = $_POST['slotLocation'];
        $rate =(int) $_POST['rate'];
        $extraCharge=(int)$_POST['extraCharge'];
        $inserQuery="INSERT INTO parking_slot (Location, SlotNumber, RatePerHour, ExtraCharge) VALUES ( '$slotLocation', '$slotName', $rate, $extraCharge)";
        if ($conn->query($inserQuery) === TRUE) {
            echo json_encode([
            "success" => true,
            "message" => "Insert Successfully"
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Error" . $conn->error
            ]);
        }
        break;
    case 'changeCharges':
        $slotId=(int)$_POST['slotId'];
        $newRate=(int)$_POST['newRate'];
        if($slotId==0){
            $updateCharges="UPDATE parking_slot SET RatePerHour=$newRate";
        }else{
            $updateCharges="UPDATE parking_slot SET RatePerHour=$newRate WHERE SlotID=$slotId";
        }
        if ($conn->query($updateCharges) === TRUE) {
            echo json_encode([
            "success" => true,
            "message" => "Update Successfully"
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Error" . $conn->error
            ]);
        }
        break;
    case 'changeExtraCharges':
        $slotId=(int)$_POST['slotId'];
        $newExtraCharge=(int)$_POST['newExtraCharge'];
        if($slotId==0){
            $updateCharges="UPDATE parking_slot SET ExtraCharge=$newExtraCharge";
        }else{
            $updateCharges="UPDATE parking_slot SET ExtraCharge=$newExtraCharge WHERE SlotID=$slotId";
        }
        if ($conn->query($updateCharges) === TRUE) {
            echo json_encode([
            "success" => true,
            "message" => "Update Successfully"
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Error" . $conn->error
            ]);
        }
        break;
    case 'removeSlot':
        $slotId=(int)$_POST['slotId'];
        $removeSlot="DELETE FROM parking_slot WHERE SlotID=$slotId";
        if ($conn->query($removeSlot) === TRUE) {
            echo json_encode([
            "success" => true,
            "message" => "remove Successfully"
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Error" . $conn->error
            ]);
        }
        break;
    case 'addManager':
        $managerName = $_POST['managerName'];
        $email = $_POST['email'];
        $password =$_POST['password'];
        $phoneNo=$_POST['phoneNo'];
        $insertManager="INSERT INTO user_table ( Role, Name,Email, Password, Phone, Status, RegistrationDate) VALUES ('Manager', '$managerName', '$email', '$password', '$phoneNo', 'Active', NOW())";
        if ($conn->query($insertManager) === TRUE) {
            echo json_encode([
            "success" => true,
            "message" => "Insert Successfully"
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Error" . $conn->error
            ]);
        }
        break;
    case 'fetchUsers':
        $sql = "SELECT * FROM user_table";
        $result = $conn->query($sql);
        $users = [];
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $users[] = $row;
            }
        }
        echo json_encode($users);
        break;
    case 'changeUserStatus':
        $userId=(int)$_POST['userId'];
        $status=$_POST['status'];
        $changeStatus="UPDATE user_table SET status = '$status' WHERE userId=$userId";
        if ($conn->query($changeStatus) === TRUE) {
            echo json_encode([
            "success" => true,
            "message" => "Change Successfully"
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Error" . $conn->error
            ]);
        }
        break;
    case 'fetchBooking':
          $sql = "SELECT b.*, p.Status 
                FROM booking_table b
                INNER JOIN payment_table p ON b.PaymentId = p.PaymentID";
        $result = $conn->query($sql);

        $viewBooking = [];
        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $viewBooking[] = $row;
            }
        }
        echo json_encode($viewBooking);
        break;
    case 'removeBooking':
        $bookingID = (int)$_POST['bookingId'];
        $sql = " DELETE FROM booking_table WHERE BookingID = $bookingID";
        if ($conn->query($sql) === TRUE) {
            echo json_encode([
                "success" => true,
                "message" => "Booking Deleted."
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Delete failed: " . $conn->error
            ]);
        }
        break;
    case 'fetchHistory':
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
    case 'removeHistory':
        $historyID = (int)$_POST['historyId'];
        $sql = " DELETE FROM booking_history WHERE b_history_id = $historyID";
        if ($conn->query($sql) === TRUE) {
            echo json_encode([
                "success" => true,
                "message" => "Booking Deleted."
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Delete failed: " . $conn->error
            ]);
        }
        break;
    case 'fetchPayment':
         $sql = "SELECT * from Payment_table";
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
    case 'removePayment':
        $paymentId = (int)$_POST['paymentId'];
        $sql = " DELETE FROM payment_table WHERE paymentid = $paymentId";
        if ($conn->query($sql) === TRUE) {
            echo json_encode([
                "success" => true,
                "message" => "Booking Deleted."
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Delete failed: " . $conn->error
            ]);
        }
        break;
    default:
             echo json_encode(["success" => false, "message" => "Invalid action"]);  
}
$conn->close();
?>
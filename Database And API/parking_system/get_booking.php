<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$action = $_POST['action'];
switch ($action) {
    case 'check_availability':
        $slotID = (int)$_POST['slot_id'];
        $entryTime = $_POST['entry_time'];   
        $exitTime = $_POST['exit_time'];

        $sql = "SELECT * FROM booking_table 
                WHERE SlotID = $slotID
                AND (
                    (StartTime <= '$entryTime' AND EndTime >= '$exitTime') 
                    OR (StartTime BETWEEN '$entryTime' AND '$exitTime') 
                    OR (EndTime BETWEEN '$entryTime' AND '$exitTime')
                )";

        $result = $conn->query($sql);

        //$response = [];
        $response["availability"] = ($result->num_rows > 0) ? false : true;

        echo json_encode($response);
        break;
    case 'book_slot':
        $userID = (int)$_POST['user_id'];
        $slotID = (int)$_POST['slot_id'];    
        $entryTime = $_POST['entry_time'];
        $exitTime = $_POST['exit_time'];
        $vehicleNumber = $_POST['vehicle_number'];
        $amount = floatval($_POST['amount']);
        $method = $_POST['method'];
        $userName = $_POST['user_name'];
        $slotName = $_POST['slot_name'];
        $verOTP = trim($_POST['verOTP']);
        $phoneNo = $_POST['phoneNo'];

    // Insert payment first
            $insertPayment = "INSERT INTO payment_table (UserID,Amount,paymenttime,paymentmethod,status) 
                            VALUES ($userID, $amount, NOW(), '$method', 'success')";

            if ($conn->query($insertPayment) === TRUE) {
                $paymentID = $conn->insert_id;  // Get generated Payment ID

                // Insert booking record linked with payment
                $sqlBooking = "INSERT INTO booking_table 
                            (UserID, SlotID, vehicleNo, StartTime, EndTime, createdat, paymentID,UserName,SlotName,OTP,PhoneNo)
                            VALUES ($userID, $slotID, '$vehicleNumber', '$entryTime', '$exitTime', NOW(), $paymentID,'$userName','$slotName',$verOTP,'+91$phoneNo')";

                if ($conn->query($sqlBooking) === TRUE) {
                    $bookingID = $conn->insert_id;

                    // Optionally update payment record if you have BookingID column in payment_table
                    $updatePayment = "UPDATE payment_table SET BookingID = $bookingID WHERE paymentID = $paymentID";
                    $conn->query($updatePayment);

                    echo json_encode([
                        "success" => true,
                        "message" => "Booking confirmed.",
                        "booking_id" => $bookingID,
                        "payment_id" => $paymentID
                    ]);
                } else {
                    echo json_encode([
                        "success" => false,
                        "message" => "Booking failed: " . $conn->error
                    ]);
                }
            } else {
                echo json_encode([
                    "success" => false,
                    "message" => "Payment failed: " . $conn->error
                ]);
            }
            break;


    default:
        echo json_encode(["success" => false, "message" => "Invalid action"]);

}
$conn->close();
?>

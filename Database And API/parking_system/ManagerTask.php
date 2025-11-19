<?php
$conn = new mysqli("127.0.0.1", "root", "", "parking_db",3307);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$action = $_POST['action'];
switch ($action){
    case 'give_Confirmation':
        $bookingID = (int)$_POST['booking_id'];
        $sql = " UPDATE booking_table SET Status = 'Confirmed' WHERE BookingID = $bookingID";
        if ($conn->query($sql) === TRUE) {
            echo json_encode([
                "success" => true,
                "message" => "Booking confirmed."
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Booking failed: " . $conn->error
            ]);
        }
        break;
    case 'give_Cencelation':
        $bookingID = (int)$_POST['booking_id'];
        $sql = " DELETE FROM booking_table WHERE BookingID = $bookingID";
        if ($conn->query($sql) === TRUE) {
            echo json_encode([
                "success" => true,
                "message" => "Booking confirmed."
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Booking failed: " . $conn->error
            ]);
        }
        break;
   case 'update_Extend':
    header('Content-Type: application/json');
    error_reporting(E_ALL);
    ini_set('display_errors', 1);

    // Get POST data and sanitize
    $slotId = (int)$_POST['slotId'];
    $bookingId = (int)$_POST['bookingId'];
    $newEndTime = $_POST['newEndTime'];
    $extraCharge = (int)$_POST['extraCharge'];


    // Step 1: Check for conflicting bookings in the same slot
    $checkQuery = "SELECT * FROM booking_table WHERE starttime BETWEEN NOW() AND '$newEndTime' AND slotid = $slotId";
    $checkResult = $conn->query($checkQuery);

    if ($checkResult->num_rows <= 1) {
        // ✅ No conflict — update the same booking
        $updateQuery = "UPDATE booking_table SET endtime = '$newEndTime' WHERE bookingid = $bookingId";
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
    } else {
        // ❌ Conflict — look for other available slots
        $availableQuery = "SELECT * FROM parking_slot WHERE slotid NOT IN (
            SELECT slotid FROM booking_table WHERE starttime BETWEEN NOW() AND '$newEndTime')";
        $availableResult = $conn->query($availableQuery);

        if ($availableResult->num_rows > 0) {
            $slots = [];
            while ($row = $availableResult->fetch_assoc()) {
                $slots[] = $row;
            }
            echo json_encode([
                "status" => "available",
                "slots" => $slots
            ]);
        } else {
            echo json_encode([
                "status" => "conflict",
                "message" => "Slot already booked and no other slots available."
            ]);
        }
    }
    break;

    case 'give_complete':
        $bookingID = (int)$_POST['booking_id'];
       
        $searchBooking = "SELECT booking_table.*, payment_table.PaymentMethod,payment_table.amount   from booking_table,payment_table WHERE booking_table.BookingID = $bookingID and booking_table.BookingId=payment_table.BookingId";

        $result = $conn->query($searchBooking);
          if ($result->num_rows > 0) {
            $bookings = [];
            while ($row = $result->fetch_assoc()) {
                $bookings[] = $row;
            }
        } 
            if (!empty($bookings)) {
            
                    $booking = $bookings[0];
                   
                    $userID   = (int)$booking['UserID'];
                    $bookingIDIn   = (int)$booking['BookingID'];
                    $paymenyId   = (int)$booking['PaymentId'];
                    $userName   = $booking['UserName'];
                    $phoneNo   = $booking['PhoneNo'];
                    $vehicleNumber   = $booking['VehicleNo'];
                    $entryTime   = $booking['StartTime'];
                    $exitTime   = $booking['EndTime'];
                    $slotName   = $booking['SlotName'];
                    $method   = $booking['PaymentMethod'];
                    $amount   = $booking['amount'];
                    
                    $insertHistory="INSERT INTO booking_history (userId, bookingId, paymentId,User_Name, Phone_No, Vechicle_No, StartTime, EndTime, SlotName, Payment_Method, Payment_Amount) VALUES ( $userID, $bookingIDIn,$paymenyId, '$userName', '$phoneNo','$vehicleNumber', '$entryTime', '$exitTime', '$slotName', '$method', $amount)";

                    if ($conn->query($insertHistory) === TRUE) {
                       // echo "Inserted successfully!";
                    } else {
                       // echo "Error: " . $conn->error;
                    }
                }
                $DeleteBooking = "DELETE FROM booking_table WHERE BookingID = $bookingIDIn";
        if ($conn->query($DeleteBooking) === TRUE) {
            echo json_encode([
                "success" => true,
                "message" => "Booking Complete."
            ]);
        }else{
            echo json_encode([
                "success" => false,
                "message" => "Booking Complete Failed: " . $conn->error
            ]);
        }
        
       
        break;
    
     default:
             echo json_encode(["success" => false, "message" => "Invalid action"]);             
       }
$conn->close();
?>
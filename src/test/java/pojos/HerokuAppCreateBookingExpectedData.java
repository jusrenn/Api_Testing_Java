package pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HerokuAppCreateBookingExpectedData {
    private int bookingid;
    private HerokuAppCreateBookingBody booking;
}

package thirdparty.seatbooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeatReservationServiceImpl implements SeatReservationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void reserveSeat(long accountId, int totalSeatsToAllocate) {
        // Real implementation omitted, assume working code will make the seat reservation.
        logger.info("Seats reserved successfully");
    }

}

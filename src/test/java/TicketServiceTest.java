import jakarta.validation.ConstraintViolation;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.util.Util;

import java.util.Set;
import java.util.stream.Collectors;

public class TicketServiceTest {

    public uk.gov.dwp.uc.pairtest.TicketService ticketService;
    public SeatReservationService seatReservationService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void initializeClass()
    {
        ticketService = new TicketServiceImpl();
        seatReservationService = new SeatReservationServiceImpl();
    }

    @Test
    public void purchaseTicketsTest()
    {
        Long accountId = 0L;
        TicketTypeRequest ticketTypeRequestA = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest ticketTypeRequestB = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        logger.info("Purchase of tickets for account {}", accountId);

        try {
            ticketService.purchaseTickets(accountId, ticketTypeRequestA, ticketTypeRequestB);
        }
        catch(InvalidPurchaseException exception)
        {
            logger.error("Exception thrown: {}", exception.getMessage());
        }
        logger.info("=================================");



        logger.info("Purchase of tickets for account {}", accountId);
        accountId = 1L;
        try {
            ticketService.purchaseTickets(accountId, ticketTypeRequestA, ticketTypeRequestB);
        }
        catch(InvalidPurchaseException exception)
        {
            logger.error("Exception thrown: {}", exception.getMessage());
        }

    }


    @Test
    @Ignore
    public void getTicketPriceByTicketTypeTest()
    {
        Long accountId = 1L;
        TicketTypeRequest ticketTypeRequestA = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest ticketTypeRequestB = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
        TicketTypeRequest ticketTypeRequestC = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2);
        Double ticketPrice = Util.getTicketPriceByTicketType(ticketTypeRequestA);
        logger.info("Ticket price for {} ticket is {}", ticketTypeRequestA.getTicketType().name(), ticketPrice);
        Double total = Util.getTotalTicketPrices(ticketTypeRequestA, ticketTypeRequestB, ticketTypeRequestC);

    }


    @Test
    @Ignore
    public void getReservationSeatsTest()
    {
        Long accountId = 1L;
        TicketTypeRequest ticketTypeRequestA = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest ticketTypeRequestB = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
        TicketTypeRequest ticketTypeRequestC = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2);
        Integer totalTickets = Util.getReservationSeats(ticketTypeRequestA, ticketTypeRequestB, ticketTypeRequestC);
        seatReservationService.reserveSeat(accountId, totalTickets);

        logger.info("Total ticket counts - {}", totalTickets);

    }


    @Test
    @Ignore
    public void validateTicketRequestTest()
    {
        TicketTypeRequest ticketTypeRequestA = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest ticketTypeRequestB = new TicketTypeRequest(null, 0);
        TicketTypeRequest ticketTypeRequestC = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 0);
        Set<Set<ConstraintViolation<TicketTypeRequest>>> constraintViolations = Util.validateTicketRequest(ticketTypeRequestA, ticketTypeRequestB, ticketTypeRequestC);


        String allErrors = constraintViolations.stream().map(constraintViolation -> {
            String t = constraintViolation.stream().map(violation -> violation.getMessage())
                    .collect(Collectors.joining (", "));
            return t;
        }).collect(Collectors.joining("\n"));

        logger.error("Validation error: {}", allErrors);
    }
}

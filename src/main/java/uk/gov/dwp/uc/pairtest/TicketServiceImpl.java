package uk.gov.dwp.uc.pairtest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.util.Util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */


    public SeatReservationService seatReservationService = new SeatReservationServiceImpl();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {



        if(accountId<1)
        {
            throw new InvalidPurchaseException("Minimum value of account identifier is 1");
        }



        Set<Set<ConstraintViolation<TicketTypeRequest>>> violationsSet = Util.validateTicketRequest(ticketTypeRequests);
        String errorMessages = violationsSet.stream().map(violations -> {
            String t = violations.stream().map(violation -> violation.getMessage())
                    .collect(Collectors.joining (", "));
            return t;
        }).collect(Collectors.joining("\n"));


        if(!errorMessages.isEmpty())
        {
            throw new InvalidPurchaseException(errorMessages);
        }

        Double totalTicketPrice = Util.getTotalTicketPrices(ticketTypeRequests);

        Integer totalSeatCount = Util.getReservationSeats(ticketTypeRequests);

        seatReservationService.reserveSeat(accountId, totalSeatCount);


    }



}

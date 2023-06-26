package uk.gov.dwp.uc.pairtest.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Util {


    public static Double getTicketPriceByTicketType(TicketTypeRequest ticketTypeRequest)
    {
        return switch (ticketTypeRequest.getTicketType())
        {
            case ADULT -> 20.00;
            case CHILD -> 10.00;
            case INFANT -> 0.00;
        };
    }


    public static Double getTotalTicketPrices(TicketTypeRequest... ticketTypeRequests)
    {
        Double totalTicketPrice = 0.00;
        Double total = Arrays.stream(ticketTypeRequests).mapToDouble(o -> {
            return (getTicketPriceByTicketType(o) * o.getNoOfTickets());
        }).sum();
        return total;
    }

    public static Integer getReservationSeats(TicketTypeRequest... ticketTypeRequests)
    {
        Integer totalTickets = Arrays.stream(ticketTypeRequests).mapToInt(o -> {
            return switch (o.getTicketType())
            {
                case ADULT -> (o.getNoOfTickets());
                case CHILD -> (o.getNoOfTickets());
                default -> (0);
            };

        }).sum();
        return totalTickets;
    }


    public static Set<Set<ConstraintViolation<TicketTypeRequest>>> validateTicketRequest(TicketTypeRequest... ticketTypeRequests)
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<TicketTypeRequest>> violations = validator.validate(user);
        Set<Set<ConstraintViolation<TicketTypeRequest>>> violations = Arrays.stream(ticketTypeRequests).map(ticketTypeRequest -> {
            return (validator.validate(ticketTypeRequest));
        }).collect(Collectors.toSet());

        return violations;
    }


}

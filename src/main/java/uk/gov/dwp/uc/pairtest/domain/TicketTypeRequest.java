package uk.gov.dwp.uc.pairtest.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Immutable Object
 */

public class TicketTypeRequest {

    @Min(value = 1, message = "Minimum number of tickets that can be purchased on a ticket request is one (1)")
    @Max(value = 5, message = "Maximum number of tickets that can be purchased on a ticket request is five (5)")
    @NotNull(message = "Number of tickets must be specified")
    private int noOfTickets;

    @NotNull(message = "Ticket type must be specified")
    private Type type;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {
        ADULT, CHILD , INFANT
    }

}

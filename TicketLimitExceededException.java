public class TicketLimitExceededException extends Exception {

    public TicketLimitExceededException() {
        super();
    }

    public TicketLimitExceededException(String message) {
        super(message);
    }
}
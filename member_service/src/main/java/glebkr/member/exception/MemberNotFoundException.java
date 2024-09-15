package glebkr.member.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Integer id) {
        super("Member with id " + id + " not found");
    }
}

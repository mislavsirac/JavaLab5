package hr.java.vjezbe.iznimke;

/**
 * Iznimka koja se baci kada se pronađe više najmlađih studenata, tj. kada nije moguće odrediti jednog najmljađeg studenata.
 */

public class PostojiViseNajmladjihStudenataException extends RuntimeException{

    public  PostojiViseNajmladjihStudenataException (String message){
        super(message);
    }

    public  PostojiViseNajmladjihStudenataException (String message, Throwable cause){
        super(message, cause);
    }

    public  PostojiViseNajmladjihStudenataException (Throwable cause){
        super(cause);
    }
}

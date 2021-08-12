package crazy8;


/**
 * ***************************************************************
 * Class Link, the base class for a link list of playing cards May be placed in
 * a file named Link.java
 *
 *****************************************************************
 */
public class Link {

    protected Link next;

    public Link getNext() {
        return next;
    }

    public void setNext(Link newnext) {
        next = newnext;
    }

}  // end class Link

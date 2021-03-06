package org.jboss.narayana.quickstarts.nonTransactionalResource.bookService;

import org.jboss.narayana.compensations.api.Compensatable;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author paul.robinson@redhat.com 02/08/2013
 */
public class BookService {

    @Inject
    PackageDispatcher packageDispatcher;

    @Inject
    InvoicePrinter invoicePrinter;

    private static AtomicInteger orderId = new AtomicInteger(0);

    @Compensatable
    public void buyBook(String item, String address) {

        packageDispatcher.dispatch(item, address);
        invoicePrinter.print(orderId.getAndIncrement(), "Invoice body would go here, blah blah blah");

        //other activities, such as updating inventory and charging the customer


        /**
         * This is here in order to eliminate participant completion race condition.
         * See these blog posts:
         *      http://jbossts.blogspot.co.uk/2013/01/ws-ba-participant-completion-race.html
         *      http://jbossts.blogspot.co.uk/2013/01/ws-ba-participant-completion-race_30.html
         * And JIRA:
         *      https://issues.jboss.org/browse/JBTM-1718
         */
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

}

package com.smalaca.jpa;

import com.smalaca.jpa.domain.Invoice;
import com.smalaca.jpa.domain.InvoiceItem;
import com.smalaca.jpa.dto.IdWithStatus;
import com.smalaca.jpa.utils.DbUtils;

import java.util.function.Function;
import java.util.stream.Collectors;

public class NativeQueries {
    public static void main(String[] args) {
        DbUtils.init();
        DbUtils.populateDb();
        DbUtils.nextContext();
        DbUtils.runInEntityManagerContext(context -> {
            var nativeQuery = context.createNativeQuery("select * from invoice", Invoice.class);
            System.out.println(nativeQuery.getResultList());

            var nativeQuery2 = context.createNativeQuery("select id, status from invoice", Invoice.class);
            System.out.println(nativeQuery2.getResultList());


            var nativeQuery3 = context.createNativeQuery("select id, status from invoice");
            var nativeQuery3Result = nativeQuery3.getResultList();
            var nativeQuery3ResultTransformed = nativeQuery3Result.stream()
                    .map((Function<Object[], IdWithStatus>) row -> new IdWithStatus((byte[])(row[0]), (String)(row[1])))
                    .collect(Collectors.toList());
            System.out.println(nativeQuery3ResultTransformed);

            var nativeQuery3a = context.createNativeQuery("select id, status from invoice", "idWithStatus");
            System.out.println(nativeQuery3a.getResultList());


            var nativeQuery4 = context.createNativeQuery("select * from invoiceitem where amount > :amount", InvoiceItem.class);
            nativeQuery4.setParameter("amount", 3);
            System.out.println(nativeQuery4.getResultList());

            var nativeQuery5 = context.createNativeQuery("select * from invoiceitem where amount <= ?1", InvoiceItem.class);
            nativeQuery5.setParameter(1, 3);
            System.out.println(nativeQuery5.getResultList());

            var nativeQuery6 = context.createNativeQuery("select s.id as seller_id, b.id as buyer_id from invoice i, seller s, buyer b where i.seller_id = s.id and i.buyer_id = b.id",
                    "sellerAndBuyerId");
            System.out.println(nativeQuery6.getResultList());

            var namedNativeQuery = context.createNamedQuery("Invoice.findBySellerLoginNative", Invoice.class);
            namedNativeQuery.setParameter("login", "natasha");
            System.out.println(namedNativeQuery.getResultList());
        });

        DbUtils.close();
    }
}

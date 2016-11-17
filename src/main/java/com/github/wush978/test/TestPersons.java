package com.github.wush978.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wush.wu on 2016/11/15.
 */
public class TestPersons {

    public static List<PersonOuterClass.Person> getTestPersons() {
        List<PersonOuterClass.Person> result = new ArrayList();
        result.add(
            PersonOuterClass.Person.newBuilder()
                .setId("A123456789")
                .setAge(11)
                .setSex("female")
                .setContact(PersonOuterClass.Person.Contact.newBuilder()
                        .setEmailAddress("email@example.com")
                )
                .build()
        );

        result.add(result.get(0).toBuilder().setId("M123456789").build());
        result.add(result.get(0).toBuilder().setAge(12).build());
        result.add(result.get(0).toBuilder().setSex("male").build());
        result.add(result.get(0).toBuilder().setContact(
                result.get(0).getContact().toBuilder().setPostalCode("123")
        ).build());
        result.add(result.get(0).toBuilder().setContact(
                result.get(0).getContact().toBuilder().setAddress("example address")
        ).build());
        return result;
    }
}

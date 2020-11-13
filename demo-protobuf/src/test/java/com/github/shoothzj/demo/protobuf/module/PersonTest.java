package com.github.shoothzj.demo.protobuf.module;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class PersonTest {

    @Test
    public void testPersonBuild() {
        AddressBookProtos.Person john = AddressBookProtos.Person.newBuilder()
                        .setId(1234)
                        .setName("John Doe")
                        .setEmail("jdoe@example.com")
                        .addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder()
                                .setNumber("555-4321").setType(AddressBookProtos.Person.PhoneType.HOME))
                        .build();
        System.out.println(john);
    }

}

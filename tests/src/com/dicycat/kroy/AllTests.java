package com.dicycat.kroy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        FireStationTest.class,
        FireTruckTest.class,
        FortressTest.class,
        GooseTest.class,
        PipeTest.class
})

public class AllTests {
}
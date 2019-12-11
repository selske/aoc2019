package be.selske.aoc2019.days;

import be.selske.aoc2019.days.intcomputer.IntComputer;

import static java.util.stream.Collectors.joining;

public class DayJoey {

    public static void main(String[] args) {

        long[] memory = IntComputer.parseInput("104,65,20101,55,1,0,204,0,109,45,003,5,1001,5,119,4,204,-41,01001,0,-19,0,004,0,00101,100,0,0,009,0,20102,23,11,-244,004,2,01001,0,-169,0,109,-242,204,-4,20101,-5,14,-3,204,-3,104,117,21002,35,54,0,204,0,01001,0,-31,0,22101,100,-4,7,004,11,004,2,22101,-22,-1,5,204,5,00102,33,176,176,1105,75,176,1102,34463338,34463338,63,1007,63,34463338,63,1005,63,53,1101,0,3,1000,109,988,209,12,9,1000,209,6,209,3,203,0,1008,1000,1,63,1005,63,65,1008,1000,2,63,1005,63,904,1008,1000,0,63,1005,63,58,4,25,104,0,99,4,0,104,0,99,4,17,104,0,99,0,0,1101,29,0,1010,1102,1,1,1021,1101,0,36,1002,1101,573,0,1026,1101,0,33,1012,1102,1,25,1004,1102,1,38,1000,3");

        IntComputer intComputer = new IntComputer(memory);
        intComputer.run(1L);
        String message = intComputer.getOutput().stream()
                .map(l -> (char) l.intValue() + "")
                .collect(joining());

        System.out.println("message = " + message);
    }

}

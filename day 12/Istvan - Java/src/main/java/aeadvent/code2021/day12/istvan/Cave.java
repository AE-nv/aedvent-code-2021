package aeadvent.code2021.day12.istvan;

public record Cave(String name) {

    public static Cave fromString(String input) {
        return new Cave(input);
    }

    public boolean isBig() {
        var firstChar = name.charAt(0);
        return firstChar >= 'A' && firstChar <= 'Z';
    }

    public boolean isStart() {
        return name.equals("start");
    }

    public boolean isEnd() {
        return name.equals("end");
    }
}

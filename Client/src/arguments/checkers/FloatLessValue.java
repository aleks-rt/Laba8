package arguments.checkers;

import arguments.exceptions.CheckerException;

public class FloatLessValue implements Checker{
    private Float value;

    public FloatLessValue(Float value) {
        this.value = value;
    }

    @Override
    public void check(String argument) throws CheckerException {
        if(Float.parseFloat(argument) > value) {
            throw new CheckerException("Число больше максимального порога!");
        }
    }
}

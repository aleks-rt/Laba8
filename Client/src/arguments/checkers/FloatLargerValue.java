package arguments.checkers;

import arguments.exceptions.CheckerException;

public class FloatLargerValue implements Checker {

    private Float value;

    public FloatLargerValue(Float value) {
        this.value = value;
    }

    @Override
    public void check(String argument) throws CheckerException {
        if(Float.parseFloat(argument) < value) {
            throw new CheckerException("Число меньше минамального порога!");
        }
    }
}

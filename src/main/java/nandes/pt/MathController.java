package nandes.pt;

import nandes.pt.exceptions.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value = "/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return covertToDouble(numberOne) + covertToDouble(numberTwo);
    }

    @GetMapping(value = "/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return covertToDouble(numberOne) - covertToDouble(numberTwo);
    }

    @GetMapping(value = "/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return covertToDouble(numberOne) * covertToDouble(numberTwo);
    }

    @GetMapping(value = "/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return covertToDouble(numberOne) / covertToDouble(numberTwo);
    }

    @GetMapping(value = "/mean/{numberOne}/{numberTwo}")
    public Double mean(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return (covertToDouble(numberOne) + covertToDouble(numberTwo)) / 2;
    }

    @GetMapping(value = "/squareRoot/{number}")
    public Double squareRoot(@PathVariable(value = "number") String number) throws Exception {

        if (!isNumeric(number)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return Math.sqrt(covertToDouble(number));
    }

    public static Double covertToDouble(String strNumber) {
        if (strNumber == null) return 0d;
        String number = strNumber.replaceAll(",", ".");
        if (isNumeric(number)) return Double.parseDouble(number);
        return 1.0d;
    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
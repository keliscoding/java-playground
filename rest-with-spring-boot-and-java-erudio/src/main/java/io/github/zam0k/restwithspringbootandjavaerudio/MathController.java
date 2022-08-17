package io.github.zam0k.restwithspringbootandjavaerudio;

import io.github.zam0k.restwithspringbootandjavaerudio.exceptions.UnsupportedMathOperationException;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {
    private static final String template = "Hello, %s!";
    private static final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    @GetMapping
    public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/sub/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double subtraction(@PathVariable("numberOne") String numberOne,
                              @PathVariable("numberTwo") String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/multi/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double multiply(@PathVariable("numberOne") String numberOne,
                              @PathVariable("numberTwo") String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/div/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double divide(@PathVariable("numberOne") String numberOne,
                              @PathVariable("numberTwo") String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    @RequestMapping(value = "/average/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double arithmeticAverage(@PathVariable("numberOne") String numberOne,
                              @PathVariable("numberTwo") String numberTwo) {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return (convertToDouble(numberOne) + convertToDouble(numberTwo))/2;
    }

    @RequestMapping(value = "/sqr/{number}", method = RequestMethod.GET)
    public Double squareRoot(@PathVariable("number") String number) {
        if(!isNumeric(number))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        Double dbNumber = convertToDouble(number);
        return Math.sqrt(dbNumber);
    }

    private Double convertToDouble(String strNumber) {
        if (strNumber == null) return 0D;

        String number = strNumber.replaceAll(",", ".");
        if(isNumeric(number)) return Double.parseDouble(number);
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if (strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

}

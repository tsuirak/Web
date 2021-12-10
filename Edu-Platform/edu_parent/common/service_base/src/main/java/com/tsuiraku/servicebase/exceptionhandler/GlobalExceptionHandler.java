package com.tsuiraku.servicebase.exceptionhandler;
import com.tsuiraku.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().msg("执行了全局异常处理");
    }

    // 特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().msg("执行了ArithmeticException异常处理");
    }

    // 自定义异常
    @ExceptionHandler(MyException.class)
    @ResponseBody //为了返回数据
    public R error(MyException e) {
        log.error(e.getMessage());
        e.printStackTrace();

        return R.error().code(e.getCode()).msg(e.getMsg());
    }

}

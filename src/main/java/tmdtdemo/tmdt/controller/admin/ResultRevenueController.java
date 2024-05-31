package tmdtdemo.tmdt.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.HTTP;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.service.DailyRevenueService;
import tmdtdemo.tmdt.service.ResultRevenueService;
import tmdtdemo.tmdt.utils.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/result")
public class ResultRevenueController {
    private final ResultRevenueService resultRevenueService;
    private final DailyRevenueService dailyRevenueService;
    @PostMapping("/caculate")
    public ResponseEntity<BaseResponse> caculate(@RequestParam int day,@RequestParam int month, @RequestParam int year, HttpServletRequest request){
        return ResponseEntity.ok(BaseResponse.builder()
                .code(HttpStatus.OK.toString())
                .message(resultRevenueService.caculateResultFor(
                        day,month,year,
                        request.getHeader("x-admin-username")
                )).build());
    }
    @PostMapping("/daily")
    public ResponseEntity<BaseResponse> caculateDaily(@RequestParam int month, @RequestParam int year,
                                                       HttpServletRequest request){
        return ResponseEntity.ok(BaseResponse.builder()
                .code(HttpStatus.OK.toString())
                .message(dailyRevenueService.caculateDailyRevenueForMonth(month,year,
                        request.getHeader("x-admin-username")
                )).build());
    }
}

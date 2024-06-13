package tmdtdemo.tmdt.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tmdtdemo.tmdt.dto.response.DashboardResponseForAllMonth;
import tmdtdemo.tmdt.dto.response.DashboardResponseForDay;
import tmdtdemo.tmdt.dto.response.DashboardResponseForMonth;
import tmdtdemo.tmdt.service.DashboardService;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    @PostMapping("/forMonth")
    public ResponseEntity<DashboardResponseForMonth> forMonth(
            @RequestParam int month,
            @RequestParam int year
    ){
        return ResponseEntity.ok(dashboardService.getReportByMonth(month,year));
    }

    @PostMapping("/forDay")
    public ResponseEntity<DashboardResponseForDay> forDay
            (
                    @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                    @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end
            ){
        return ResponseEntity.ok(dashboardService.getReportByDay(start,end));
    }
    @PostMapping("/forAllMonth")
    public ResponseEntity<DashboardResponseForAllMonth> forAllMonth(){
        return ResponseEntity.ok(dashboardService.getReportAllMont());
    }
}

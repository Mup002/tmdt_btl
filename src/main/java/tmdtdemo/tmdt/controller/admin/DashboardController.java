package tmdtdemo.tmdt.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tmdtdemo.tmdt.dto.response.DashboardResponseForMonth;
import tmdtdemo.tmdt.service.DashboardService;

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
}

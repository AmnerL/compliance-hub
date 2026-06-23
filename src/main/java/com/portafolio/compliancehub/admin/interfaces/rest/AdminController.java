package com.portafolio.compliancehub.admin.interfaces.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portafolio.compliancehub.admin.domain.service.AdminLogService;
import com.portafolio.compliancehub.admin.domain.service.AdminMetricsService;
import com.portafolio.compliancehub.admin.domain.service.AdminUserService;
import com.portafolio.compliancehub.admin.interfaces.rest.resources.ChangeRoleRequest;
import com.portafolio.compliancehub.admin.interfaces.rest.resources.ChangeStatusRequest;
import com.portafolio.compliancehub.admin.interfaces.rest.resources.LogEntryResponse;
import com.portafolio.compliancehub.admin.interfaces.rest.resources.MetricResponse;
import com.portafolio.compliancehub.admin.interfaces.rest.resources.UserAdminResponse;
import com.portafolio.compliancehub.admin.interfaces.rest.transform.LogEntryResponseFromLogEntryAssembler;
import com.portafolio.compliancehub.admin.interfaces.rest.transform.UserAdminResponseFromAggregateAssembler;
import com.portafolio.compliancehub.auth.domain.model.valueobjects.RoleType;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminUserService adminUserService;
    private final AdminMetricsService adminMetricsService;
    private final AdminLogService adminLogService;

    public AdminController(
            AdminUserService adminUserService,
            AdminMetricsService adminMetricsService,
            AdminLogService adminLogService) {
        this.adminUserService = adminUserService;
        this.adminMetricsService = adminMetricsService;
        this.adminLogService = adminLogService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserAdminResponse>> getUsers() {
        var users = adminUserService.listUsers();
        var responses = users.stream()
                .map(UserAdminResponseFromAggregateAssembler::fromAggregate)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<UserAdminResponse> changeRole(
            @PathVariable Long id,
            @RequestBody ChangeRoleRequest request) {
        RoleType newRole = RoleType.valueOf(request.role().toUpperCase());
        var updatedUser = adminUserService.changeUserRole(id, newRole);
        return ResponseEntity.ok(UserAdminResponseFromAggregateAssembler.fromAggregate(updatedUser));
    }

    @PatchMapping("/users/{id}/status")
    public ResponseEntity<UserAdminResponse> changeStatus(
            @PathVariable Long id,
            @RequestBody ChangeStatusRequest request) {
        if (request.active() == null) {
            return ResponseEntity.badRequest().build();
        }
        var updatedUser = adminUserService.changeUserStatus(id, request.active());
        return ResponseEntity.ok(UserAdminResponseFromAggregateAssembler.fromAggregate(updatedUser));
    }

    @GetMapping("/metrics")
    public ResponseEntity<MetricResponse> getMetrics() {
        long regulations = adminMetricsService.getRecentRegulationsCount();
        long checklists = adminMetricsService.getRecentChecklistsCount();
        long consultations = adminMetricsService.getRecentAIConsultationsCount();

        var response = new MetricResponse(regulations, checklists, consultations);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<LogEntryResponse>> getLogs(
            @RequestParam(required = false) String method,
            @RequestParam(required = false) String username) {
        var logs = adminLogService.getLogs(method, username);
        var responses = logs.stream()
                .map(LogEntryResponseFromLogEntryAssembler::toResourceFromValueObject)
                .toList();
        return ResponseEntity.ok(responses);
    }
}

package com.portafolio.compliancehub.notification.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.portafolio.compliancehub.auth.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.portafolio.compliancehub.notification.domain.model.aggregates.Notification;
import com.portafolio.compliancehub.notification.domain.model.commands.MarkAsReadCommand;
import com.portafolio.compliancehub.notification.domain.model.commands.SubscribeCommand;
import com.portafolio.compliancehub.notification.domain.model.commands.UnsubscribeCommand;
import com.portafolio.compliancehub.notification.domain.model.queries.ListNotificationsQuery;
import com.portafolio.compliancehub.notification.domain.service.NotificationCommandService;
import com.portafolio.compliancehub.notification.domain.service.NotificationQueryService;
import com.portafolio.compliancehub.notification.domain.service.SubscriptionCommandService;
import com.portafolio.compliancehub.notification.interfaces.rest.resources.NotificationResponseResource;
import com.portafolio.compliancehub.notification.interfaces.rest.resources.SubscribeResource;
import com.portafolio.compliancehub.notification.interfaces.rest.resources.UnsubscribeResource;
import com.portafolio.compliancehub.notification.interfaces.rest.transform.NotificationResponseFromAggregateAssembler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;
    private final SubscriptionCommandService subscriptionCommandService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal UserDetailsImpl user,
            @Valid @RequestBody SubscribeResource resource) {
        SubscribeCommand cmd = new SubscribeCommand(user.getUserId(), resource.category());
        subscriptionCommandService.handle(cmd);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal UserDetailsImpl user,
            @Valid @RequestBody UnsubscribeResource resource) {
        UnsubscribeCommand cmd = new UnsubscribeCommand(user.getUserId(), resource.category());
        subscriptionCommandService.handle(cmd);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<NotificationResponseResource>> list(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam(required = false, defaultValue = "false") boolean unreadOnly,
            @PageableDefault(size = 20) Pageable pageable) {
        ListNotificationsQuery query = new ListNotificationsQuery(user.getUserId(), unreadOnly, pageable);
        Page<Notification> page = notificationQueryService.handle(query);
        return ResponseEntity.ok(page.map(NotificationResponseFromAggregateAssembler::toResource));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationResponseResource> markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl user) {
        MarkAsReadCommand cmd = new MarkAsReadCommand(id, user.getUserId());
        Notification notif = notificationCommandService.handle(cmd);
        return ResponseEntity.ok(NotificationResponseFromAggregateAssembler.toResource(notif));
    }

}

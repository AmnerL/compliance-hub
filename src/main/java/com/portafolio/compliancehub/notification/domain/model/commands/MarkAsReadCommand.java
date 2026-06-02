package com.portafolio.compliancehub.notification.domain.model.commands;

public record MarkAsReadCommand(Long notificationId, Long userId) {

}

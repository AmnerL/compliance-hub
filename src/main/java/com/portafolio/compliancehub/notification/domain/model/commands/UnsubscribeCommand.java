package com.portafolio.compliancehub.notification.domain.model.commands;

import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;

public record UnsubscribeCommand(Long userId, Category category) {

}

package com.spareparts.modules.reporting.service;

import com.spareparts.modules.reporting.dto.AlertResponse;

import java.util.List;

public interface AlertService {
    List<AlertResponse> getAlerts();
}
